package com.archiving.auth.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.archiving.auth.exception.LoginFailedException;
import com.archiving.auth.service.LoginService;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginFailureHandler.class);

    private final LoginService loginService;

    public LoginFailureHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        if (exception instanceof LoginFailedException) {
            LoginFailedException lfe = (LoginFailedException) exception;
            if ("ERROR".equals(lfe.getResultCode())) {
                log.error("[LOGIN] business ERROR result. message={}", lfe.getMessage(), lfe);
            } else {
                log.warn("[LOGIN] auth failed. code={}, message={}", lfe.getResultCode(), lfe.getMessage());
            }
            handleBusinessFailure(request, response, lfe);
            return;
        }

        log.error("[LOGIN] unexpected AuthenticationException type={}, message={}",
                exception.getClass().getName(), exception.getMessage(), exception);

        String result = resolveResultCode(exception);
        redirect(request, response, result, resolveMessage(result), null);
    }

    private void handleBusinessFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            LoginFailedException exception) throws IOException {

        HttpSession session = request.getSession(true);
        loginService.applySessionAttributes(session, exception.getSessionAttributes());

        redirect(request, response,
                exception.getResultCode(),
                exception.getMessage(),
                exception.getRedirectPath());
    }

    private void redirect(
            HttpServletRequest request,
            HttpServletResponse response,
            String result,
            String message,
            String redirectPath) throws IOException {

        HttpSession session = request.getSession(true);
        session.setAttribute("loginResultCode", result);
        session.setAttribute("loginMessage", message);

        String contextPath = request.getContextPath();
        String target = redirectPath != null ? redirectPath : "/login";
        response.sendRedirect(contextPath + target);
    }

    private String resolveResultCode(AuthenticationException exception) {
        if (exception instanceof UsernameNotFoundException) {
            return "NotFound";
        }
        if (exception instanceof DisabledException) {
            return "useFailed";
        }
        if (exception instanceof LockedException) {
            return "locked";
        }
        if (exception instanceof BadCredentialsException) {
            return "invalid";
        }
        return "ERROR";
    }

    private String resolveMessage(String result) {
        switch (result) {
            case "NotFound":
                return "존재하지 않는 사용자입니다.";
            case "useFailed":
                return "사용 중지된 계정입니다.";
            case "locked":
                return "잠긴 계정입니다.";
            case "invalid":
                return "아이디 또는 비밀번호가 올바르지 않습니다.";
            case "exportFailed":
                return "사용기한 만료일이 지났습니다. 다시 권한신청을 해주세요.";
            case "ipFailed":
                return "등록된 IP가 다릅니다.";
            case "apply":
                return "결재건이 존재합니다.";
            case "retired":
                return "재직 상태가 아닙니다. 사용이 불가능 합니다.";
            case "brcFailed":
                return "등록된 사무소가 다릅니다.";
            case "dayFailed":
                return "장기 미사용자 입니다. 다시 권한신청을 해주세요.";
            case "authFailed":
                return "권한 신청이 필요합니다.";
            case "NotInsaMaster":
                return "인사정보가 존재하지 않습니다.";
            default:
                return "로그인 처리 중 오류가 발생했습니다.";
        }
    }
}
