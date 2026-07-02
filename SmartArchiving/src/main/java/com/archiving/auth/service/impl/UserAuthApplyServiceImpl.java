package com.archiving.auth.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.auth.dao.mapper.UserAuthApplyMapper;
import com.archiving.auth.dto.UserAuthApplySubmitDto;
import com.archiving.auth.service.UserAuthApplyService;
import com.archiving.user.dto.UserInfoDto;
import com.archiving.util.UtilClass;

@Service
public class UserAuthApplyServiceImpl implements UserAuthApplyService {

	private final UserAuthApplyMapper mapper;
	private final UtilClass utilClass = new UtilClass();

	public UserAuthApplyServiceImpl(UserAuthApplyMapper mapper) {
		this.mapper = mapper;
	}

	/* 사용자 권한신청 화면 초기 데이터 구성(기존 신청/반려 여부 및 IP/만료일 검증 포함) */
	@Override
	@SuppressWarnings("unchecked")
	public String getApplyInfo(String userCd, HttpServletRequest request) throws Exception {
		String ip = resolveClientIp(request);

		Calendar cal = Calendar.getInstance();
		String expire_date = Integer.toString(cal.get(Calendar.YEAR) + 1) + "0131";
		String now_req_date = utilClass.getDate(0, "");

		UserAuthApplyMapper.LatestReqRow latest = mapper.selectLatestPendingReq(userCd);
		String approval_req_id = latest == null ? "" : utilClass.nvl_trim(latest.getApprovalReqId());
		String req_date = latest == null ? "" : utilClass.nvl_trim(latest.getReqDate());
		String ip_address = latest == null ? "" : utilClass.nvl_trim(latest.getIpAddress());
		String ip_address_user = latest == null ? "" : utilClass.nvl_trim(latest.getIpAddressUser());
		String expire_date_req = latest == null ? "" : utilClass.nvl_trim(latest.getExpireDate());

		List<UserInfoDto> user = mapper.selectUserList(userCd);

		//만료일 조회
		String finalExpireDate = expire_date;
		if(expire_date_req != null && !expire_date_req.isEmpty()) {
			finalExpireDate = expire_date_req;
		} else {
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		    Date dbExpire = f.parse(user.get(0).getExpireDate());
		    Date today = f.parse(now_req_date);

		    if (dbExpire.compareTo(today) > 0) {
		        finalExpireDate = user.get(0).getExpireDate();
		    }
		}

		// check approval line state; if rejected then clear
		if (!approval_req_id.isEmpty()) {
			List<String> approvalYns = mapper.selectApprovalYnByReq(approval_req_id, userCd);
			int cnt = approvalYns == null ? 0 : approvalYns.size();
			boolean rejected = false;
			if (approvalYns != null) {
				for (String yn : approvalYns) {
					if ("N".equals(yn)) rejected = true;
				}
			}
			if (cnt < 1 || rejected) approval_req_id = "";
		}

		if ("".equals(utilClass.nvl_trim(ip_address_user))) ip_address_user = ip;

		int diff_day = 1;
		if (!expire_date_req.isEmpty()) {
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			Date expire = f.parse(expire_date_req);
			Date base = f.parse(utilClass.getDate(0, ""));
			diff_day = expire.compareTo(base);
		}

		/* diff_day 
		 * 양수 (> 0) 만료일 > 오늘 → 아직 유효
		 * 0만료일 == 오늘 → 당일
		 * 음수 (< 0)만료일 < 오늘 → 이미 만료됨
		*/
		JSONArray rows = null;

		// if new or invalid existing: create new id for today
		if (approval_req_id.isEmpty()
			|| (!ip.equals(utilClass.nvl_trim(ip_address_user)) && !ip.equals(utilClass.nvl_trim(ip_address)))
			|| diff_day < 0) {

			Integer cnt2 = mapper.countReqByDate(now_req_date);
			if (cnt2 == null || cnt2 < 1) {
				String seq = mapper.selectNextSeqByDate(now_req_date);
				approval_req_id = now_req_date + seq;
			} else {
				String maxSeq = mapper.selectMaxSeqByDate(now_req_date);
				int next = Integer.parseInt(maxSeq) + 1;
				approval_req_id = now_req_date + String.format("%03d", next);
			}
			// legacy behavior: req_date is NOT overwritten here (often remains blank)
		} else {
			rows = new JSONArray();
			List<UserAuthApplyMapper.ApprovalLineStateRow> lineStates = mapper.selectApprovalLineState(userCd, approval_req_id);
			if (lineStates != null) {
				for (UserAuthApplyMapper.ApprovalLineStateRow s : lineStates) {
					JSONObject datas = new JSONObject();
					datas.put("approval_date", utilClass.nvl_trim(s.getApprovalDate()));
					datas.put("approval_yn", utilClass.nvl_trim(s.getApprovalYn()));
					datas.put("line_approval_date", utilClass.nvl_trim(s.getLineApprovalDate()));
					datas.put("approval_line_user_id", utilClass.nvl_trim(s.getApprovalLineUserId()));
					datas.put("approval_req_reason", utilClass.nvl_trim(s.getApprovalReqReason()));
					datas.put("ip_address", utilClass.nvl_trim(s.getIpAddress()));
					datas.put("auth", utilClass.nvl_trim(s.getAuth()));
					datas.put("com_cd", utilClass.nvl_trim(s.getComCd()));
					rows.add(datas);
				}
			}
		}

		UserAuthApplyMapper.InsaRow insa = mapper.selectInsaByEno(userCd);

		JSONObject jsonobj = new JSONObject();

		if (insa != null) {
			jsonobj.put("eno", insa.getEno());
			jsonobj.put("name", insa.getName());
			jsonobj.put("brc", insa.getBrc());
			jsonobj.put("brnm", insa.getBrnm());
			jsonobj.put("oft_c", insa.getOftC());
			jsonobj.put("oft", insa.getOft());
		}

		if (rows != null) {
			jsonobj.put("rows", rows);
		}
		if (insa != null) {
			jsonobj.put("approval_req_id", approval_req_id);
			jsonobj.put("req_date", req_date);
//			jsonobj.put("expire_date", expire_date);
			jsonobj.put("expire_date", finalExpireDate);
			jsonobj.put("ip", ip);
			jsonobj.put("result", "OK");
		} else {
			jsonobj.put("result", "NotFound");
		}

		return jsonobj.toString();
	}

	/* 사용자 권한 신청 제출(결재요청/결재라인 insert) */
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public String submit(UserAuthApplySubmitDto dto) throws Exception {
		JSONObject jsonobj = new JSONObject();

		String approvalReqId = utilClass.nvl_trim(dto.getApproval_req_id());
		String max = utilClass.nvl_trim(mapper.selectMaxApprovalReqId());
		if (!max.isEmpty() && max.equals(approvalReqId)) {
			long reqSeq = Long.parseLong(approvalReqId);
			approvalReqId = String.valueOf(reqSeq + 1);
		}

		String programNm = mapper.selectMenuNmByPageName(dto.getPage_name());

		UserAuthApplyMapper.ApprovalReqInsertRow req = new UserAuthApplyMapper.ApprovalReqInsertRow();
		req.setUserCd(utilClass.nvl_trim(dto.getUser_cd()));
		req.setApprovalDivCd(utilClass.nvl_trim(dto.getApply_div_cd()));
		req.setApprovalReqId(approvalReqId);
		req.setName(utilClass.nvl_trim(dto.getUser_nm()));
		req.setApprovalReqReason(utilClass.nvl_trim(dto.getApproval_req_reason()));
		req.setReqDate(utilClass.getDate(0, ""));
		req.setExpireDate(utilClass.nvl_trim(dto.getExpire_date()));
		req.setBrc(utilClass.nvl_trim(dto.getBrc()));
		req.setBrnm(utilClass.nvl_trim(dto.getBrmm()));
		req.setOftC(utilClass.nvl_trim(dto.getOft_c()));
		req.setOft(utilClass.nvl_trim(dto.getOft()));
		req.setAuth("");
		req.setComCd(utilClass.nvl_trim(dto.getCom()));
		req.setIpAddress(utilClass.nvl_trim(dto.getIp()));
		req.setProgramId(utilClass.nvl_trim(dto.getPage_name()));
		req.setProgramNm(programNm == null ? "" : programNm);

		mapper.insertApprovalReq(req);

		String firstNm = safeSplitName(dto.getFirst_user_nm());
		String secondNm = safeSplitName(dto.getSecond_user_nm());

		UserAuthApplyMapper.ApprovalLineInsertRow line1 = new UserAuthApplyMapper.ApprovalLineInsertRow();
		line1.setUserCd(req.getUserCd());
		line1.setName(req.getName());
		line1.setApprovalDivCd(req.getApprovalDivCd());
		line1.setApprovalReqId(req.getApprovalReqId());
		line1.setApprovalLineIndex("1");
		line1.setApprovalLineUserId(utilClass.nvl_trim(dto.getFirst_user_id()));
		line1.setApprovalLineUserNm(firstNm);
		line1.setBeforeApplyYn("Y");
		mapper.insertApprovalLine(line1);

		UserAuthApplyMapper.ApprovalLineInsertRow line2 = new UserAuthApplyMapper.ApprovalLineInsertRow();
		line2.setUserCd(req.getUserCd());
		line2.setName(req.getName());
		line2.setApprovalDivCd(req.getApprovalDivCd());
		line2.setApprovalReqId(req.getApprovalReqId());
		line2.setApprovalLineIndex("2");
		line2.setApprovalLineUserId(utilClass.nvl_trim(dto.getSecond_user_id()));
		line2.setApprovalLineUserNm(secondNm);
		line2.setBeforeApplyYn("N");
		mapper.insertApprovalLine(line2);

		UserAuthApplyMapper.ApprovalLineInsertRow line3 = new UserAuthApplyMapper.ApprovalLineInsertRow();
		line3.setUserCd(req.getUserCd());
		line3.setName(req.getName());
		line3.setApprovalDivCd(req.getApprovalDivCd());
		line3.setApprovalReqId(req.getApprovalReqId());
		line3.setApprovalLineIndex("3");
		line3.setApprovalLineUserId("관리자");
		line3.setApprovalLineUserNm("관리자");
		line3.setBeforeApplyYn("N");
		mapper.insertApprovalLine(line3);

		jsonobj.put("result", "OK");
		return jsonobj.toString();
	}

	/* "이름/사번" 형태 등에서 이름 부분만 안전하게 추출 */
	private String safeSplitName(String s) {
		if (s == null) return "";
		int idx = s.indexOf("/");
		return idx > 0 ? s.substring(0, idx) : s;
	}

	/* 프록시 헤더 포함 클라이언트 IP 추출 */
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
