package com.archiving.auth.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.archiving.auth.dao.mapper.LoginMapper;
import com.archiving.auth.dao.mapper.LoginMapper.InsaLoginRow;
import com.archiving.auth.dao.mapper.LoginMapper.UserLoginRow;
import com.archiving.auth.dto.LoginResult;
import com.archiving.auth.security.LoginUser;
import com.archiving.auth.service.LoginService;
import com.archiving.auth.service.MenuAuthService;
import com.archiving.util.DateUtil;
import com.archiving.util.StringUtil;
import com.archiving.util.UtilClass;

/**
 * (인사: 결재+만료 선처리 → 만료 30일 전 → IP → 퇴직 → 지점/90일/세션, 비인사+SSO는 NotInsaMaster)
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private static final String GROUP_ADMIN_100 = "100";
    private static final String GROUP_ADMIN_101 = "101";

    private final LoginMapper loginMapper;
    private final MenuAuthService menuAuthService;
    private final UtilClass utilClass = new UtilClass();

    public LoginServiceImpl(LoginMapper loginMapper, MenuAuthService menuAuthService) {
        this.loginMapper = loginMapper;
        this.menuAuthService = menuAuthService;
    }

    @Override
    public LoginResult authenticate(String userCd, String rawPassword, String clientIp, boolean ssoYn) {
        String ip = StringUtil.nvlTrim(clientIp);
        String sUsercd = StringUtil.nvlTrim(userCd);
        String sPwd = rawPassword == null ? "" : rawPassword.trim();

        InsaLoginRow insa = loginMapper.selectInsaByEno(sUsercd);
        boolean hasInsa = insa != null;

        UserLoginRow user = loginMapper.selectUserByUserCd(sUsercd);
        boolean hasUser = user != null;

        String approvalLineUserId = StringUtils.defaultString(
                loginMapper.selectPendingApprovalLineUserId(sUsercd));

        long diffDays = 0;
        if (hasUser && StringUtil.isNotBlank(user.getLoginDate())) {
            try {
                diffDays = computeDiffDaysFromLoginDate(StringUtil.nvlTrim(user.getLoginDate()));
            } catch (ParseException ignored) {
                diffDays = 0;
            }
        }

        Map<String, Object> sessionAttrs = LoginResult.sessionMap();
        boolean expireWarn = false;
        String expireDate = null;
        Long remainDays = null;

        if (hasInsa) {
            LoginResult approvalResult = checkInsaApprovalBeforeLogin(
                    hasUser, user, insa, approvalLineUserId, sessionAttrs);
            if (approvalResult != null) {
                return approvalResult;
            }
        }

        if (hasUser && StringUtil.isNotBlank(user.getExpireDate())) {
            String groupId = user.getUserGrpId();
            if (!GROUP_ADMIN_100.equals(groupId) && !GROUP_ADMIN_101.equals(groupId)) {
                expireDate = StringUtil.nvlTrim(user.getExpireDate());
                remainDays = computeRemainingDays(expireDate);
                if (remainDays != null) {
                    if (remainDays <= 0) {
                        return failure("exportFailed",
                                "사용기한 만료일이 지났습니다. 다시 권한신청을 해주세요.",
                                "/userAuthApplyLogin", sessionAttrs);
                    }
                    if (remainDays >= 1 && remainDays <= 30) {
                        expireWarn = true;
                        sessionAttrs.put("authExpireWarn", "Y");
                        sessionAttrs.put("authExpireDate", expireDate);
                    }
                }
            }
        }

        if (hasUser && StringUtil.isNotBlank(user.getIpAddress())) {
            String groupId = user.getUserGrpId();
            if (!ip.equals(StringUtil.nvlTrim(user.getIpAddress()))
                    && !GROUP_ADMIN_100.equals(groupId)
                    && !GROUP_ADMIN_101.equals(groupId)) {
                return failure("ipFailed",
                        "등록된 IP가 다릅니다.",
                        "/userAuthApplyLogin", sessionAttrs);
            }
        }

        if (hasInsa) {
            return authenticateInsaUser(
                    sUsercd, sPwd, insa, user, hasUser, approvalLineUserId,
                    diffDays, sessionAttrs, expireWarn, expireDate, remainDays, ssoYn);
        }

        if (ssoYn) {
            return failure("NotInsaMaster", "인사정보가 존재하지 않습니다.", null, sessionAttrs);
        }

        return authenticateNonInsaUser(sUsercd, sPwd, user, hasUser, sessionAttrs,
                expireWarn, expireDate, remainDays);
    }

    private LoginResult checkInsaApprovalBeforeLogin(
            boolean hasUser,
            UserLoginRow user,
            InsaLoginRow insa,
            String approvalLineUserId,
            Map<String, Object> sessionAttrs) {
        try {
            if (hasUser && StringUtil.isNotBlank(user.getExpireDate()) && !approvalLineUserId.isEmpty()) {
                SimpleDateFormat expireDateFormat = new SimpleDateFormat("yyyyMMdd");
                Date expire = expireDateFormat.parse(StringUtil.nvlTrim(user.getExpireDate()));
                Date base = expireDateFormat.parse(DateUtil.todayYmd());
                if (expire.compareTo(base) < 0) {
                    sessionAttrs.put("approval_line_user_id", approvalLineUserId);
                    sessionAttrs.put("username", insa.getName());
                    sessionAttrs.put("brnm", insa.getBrnm());
                    return failure("apply", "결재건이 존재합니다.", "/userApproveProcNonLogin", sessionAttrs);
                }
            }
            if (!approvalLineUserId.isEmpty()) {
                sessionAttrs.put("approval_line_user_id", approvalLineUserId);
                sessionAttrs.put("username", insa.getName());
                sessionAttrs.put("brnm", insa.getBrnm());
                return failure("apply", "결재건이 존재합니다.", "/userApproveProcNonLogin", sessionAttrs);
            }
        } catch (ParseException e) {
            log.error("[LOGIN] expireDate parse failed. expireDate={}, today={}",
                    user == null ? null : user.getExpireDate(), DateUtil.todayYmd(), e);
            return failure("ERROR", "로그인 처리 중 오류가 발생했습니다.", null, sessionAttrs);
        }
        return null;
    }

    private LoginResult authenticateInsaUser(
            String sUsercd,
            String sPwd,
            InsaLoginRow insa,
            UserLoginRow user,
            boolean hasUser,
            String approvalLineUserId,
            long diffDays,
            Map<String, Object> sessionAttrs,
            boolean expireWarn,
            String expireDate,
            Long remainDays,
            boolean ssoYn) {

        if (StringUtil.isNotBlank(insa.getRtrDt())) {
            return failure("retired", "재직 상태가 아닙니다. 사용이 불가능 합니다.", null, sessionAttrs);
        }

        if (!hasUser) {
            if (!approvalLineUserId.isEmpty()) {
                sessionAttrs.put("approval_line_user_id", approvalLineUserId);
                sessionAttrs.put("username", insa.getName());
                sessionAttrs.put("brnm", insa.getBrnm());
                return failure("apply", "결재건이 존재합니다.", "/userApproveProcNonLogin", sessionAttrs);
            }
            return failure("authFailed", "권한 신청이 필요합니다.", "/userAuthApplyLogin", sessionAttrs);
        }

        if (!ssoYn) {
            LoginResult passwordResult = verifyPassword(sPwd, user, sessionAttrs);
            if (passwordResult != null) {
                return passwordResult;
            }
        }

        String groupId = user.getUserGrpId();

        if (!StringUtil.nvlTrim(insa.getBrc()).equals(StringUtil.nvlTrim(user.getBrc()))) {
            if (GROUP_ADMIN_100.equals(groupId) || GROUP_ADMIN_101.equals(groupId)) {
                loginMapper.updateBrc(insa.getBrc(), insa.getBrnm(), user.getUserCd());
                user.setBrc(insa.getBrc());
                user.setBrnm(insa.getBrnm());
            } else {
                return failure("brcFailed",
                        "등록된 사무소가 다릅니다.",
                        "/userAuthApplyLogin", sessionAttrs);
            }
        }

        if (diffDays > 90 && !GROUP_ADMIN_100.equals(groupId) && !GROUP_ADMIN_101.equals(groupId)) {
            return failure("dayFailed",
                    "장기 미사용자 입니다. 다시 권한신청을 해주세요.",
                    "/userAuthApplyLogin", sessionAttrs);
        }

        loginMapper.updateLoginDate(sUsercd, DateUtil.todayYmd());
        populateSuccessSession(sUsercd, user, insa, groupId, sessionAttrs);

        LoginUser loginUser = new LoginUser(user, true);
        return LoginResult.success(loginUser, sessionAttrs, expireWarn, expireDate, remainDays);
    }

    private LoginResult authenticateNonInsaUser(
            String sUsercd,
            String sPwd,
            UserLoginRow user,
            boolean hasUser,
            Map<String, Object> sessionAttrs,
            boolean expireWarn,
            String expireDate,
            Long remainDays) {

        if (!hasUser) {
            return failure("NotFound", "존재하지 않는 사용자입니다.", null, sessionAttrs);
        }

        if (!"Y".equalsIgnoreCase(user.getUseYn())) {
            return failure("useFailed", "사용 중지된 계정입니다.", null, sessionAttrs);
        }

        LoginResult passwordResult = verifyPassword(sPwd, user, sessionAttrs);
        if (passwordResult != null) {
            return passwordResult;
        }

        loginMapper.updateLoginDate(sUsercd, DateUtil.todayYmd());
        populateSuccessSession(sUsercd, user, null, user.getUserGrpId(), sessionAttrs);

        LoginUser loginUser = new LoginUser(user, false);
        return LoginResult.success(loginUser, sessionAttrs, expireWarn, expireDate, remainDays);
    }

    private void populateSuccessSession(
            String sUsercd,
            UserLoginRow user,
            InsaLoginRow insa,
            String groupId,
            Map<String, Object> sessionAttrs) {

        if ("admin".equals(sUsercd)) {
            sessionAttrs.put("usercd", sUsercd);
        } else {
            sessionAttrs.put("usercd", user.getUserCd());
        }
        sessionAttrs.put("userid", user.getUserId());
        sessionAttrs.put("groupid", user.getUserGrpId());
        sessionAttrs.put("username", user.getUserNm());
        sessionAttrs.put("picture", StringUtils.defaultIfBlank(user.getPicture(), " "));
        sessionAttrs.put("approwait", user.getApprowaitcnt());

        if (insa != null && (GROUP_ADMIN_100.equals(groupId) || GROUP_ADMIN_101.equals(groupId))) {
            sessionAttrs.put("brc", insa.getBrc());
            sessionAttrs.put("brnm", insa.getBrnm());
        } else {
            sessionAttrs.put("brc", user.getBrc());
            sessionAttrs.put("brnm", user.getBrnm());
        }
    }

    @Override
    public void applySessionAttributes(HttpSession session, Map<String, Object> attributes) {
        if (session == null || attributes == null) {
            return;
        }
        attributes.forEach(session::setAttribute);
        if (attributes.containsKey("usercd")) {
            menuAuthService.clearAllowedMenuPathsCache(session);
        }
    }

    @Override
    public void clearLoginSession(HttpSession session) {
        if (session == null) {
            return;
        }
        session.removeAttribute("sso_id");
        menuAuthService.clearAllowedMenuPathsCache(session);
    }

    /* 레거시 GetLogin AJAX 응답 포맷(JSON 문자열) */
    @Override
    @SuppressWarnings("unchecked")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(60 * 30);

        String username = StringUtil.nvlTrim(request.getParameter("username"));
        String password = request.getParameter("password") == null ? "" : request.getParameter("password");
        boolean ssoYn = session.getAttribute("sso_id") != null;

        JSONObject json = new JSONObject();
        LoginResult result = authenticate(username, password, resolveClientIp(request), ssoYn);
        json.put("result", result.getResultCode());

        Map<String, Object> attrs = result.getSessionAttributes();
        applySessionAttributes(session, attrs);

        if (result.isSuccess()) {
            applySecurityAuthentication(result);

            JSONArray data = new JSONArray();
            JSONObject row = new JSONObject();
            putIfPresent(row, "userid", attrs.get("userid"));
            putIfPresent(row, "usercd", attrs.get("usercd"));
            putIfPresent(row, "username", attrs.get("username"));
            putIfPresent(row, "approwait", attrs.get("approwait"));
            putIfPresent(row, "groupid", attrs.get("groupid"));
            putIfPresent(row, "picture", attrs.get("picture"));
            data.add(row);
            json.put("data", data);

            if (result.isExpireWarn()) {
                json.put("expireWarn", true);
                json.put("expireDate", result.getExpireDate());
                json.put("remainDays", result.getRemainDays());
            }
        }

        return json.toString();
    }

    /* 레거시 GetLogin POST 로그아웃 응답 포맷(JSON 문자열) */
    @Override
    @SuppressWarnings("unchecked")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(60 * 30);
        clearLoginSession(session);
        SecurityContextHolder.clearContext();

        JSONObject json = new JSONObject();
        json.put("result", "success");
        return json.toString();
    }

    @SuppressWarnings("unchecked")
    private void putIfPresent(JSONObject json, String key, Object value) {
        if (value != null) {
            json.put(key, value);
        }
    }

    private void applySecurityAuthentication(LoginResult result) {
        LoginUser loginUser = result.getLoginUser();
        if (loginUser == null) {
            return;
        }
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        authentication.setDetails(result);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private LoginResult verifyPassword(String rawPassword, UserLoginRow user, Map<String, Object> sessionAttrs) {
        String pwd = utilClass.encryptSHA256(rawPassword);
        if (!StringUtils.equalsIgnoreCase(pwd, user.getPassword())) {
            return failure("invalid", "아이디 또는 비밀번호가 올바르지 않습니다.", null, sessionAttrs);
        }
        return null;
    }

    private LoginResult failure(
            String resultCode,
            String message,
            String redirectPath,
            Map<String, Object> sessionAttrs) {
        return LoginResult.failure(resultCode, message, redirectPath, sessionAttrs);
    }

    private Long computeRemainingDays(String expireDateYyyymmdd) {
        try {
            if (StringUtil.isBlank(expireDateYyyymmdd)) {
                return null;
            }
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            fmt.setLenient(false);
            Date expire = fmt.parse(expireDateYyyymmdd.trim());
            Date today = fmt.parse(DateUtil.todayYmd());
            long diffMs = expire.getTime() - today.getTime();
            return diffMs / (24L * 60L * 60L * 1000L);
        } catch (Exception e) {
            return null;
        }
    }

    private long computeDiffDaysFromLoginDate(String loginDateYyyymmdd) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date loginDate = fmt.parse(loginDateYyyymmdd);

        Date now = Calendar.getInstance().getTime();
        Date today = fmt.parse(fmt.format(now));

        long diffSec = (today.getTime() - loginDate.getTime()) / 1000;
        return diffSec / (24 * 60 * 60);
    }

    private String resolveClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getHeader("Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }
}
