package com.archiving.approve.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.approve.dao.mapper.ApproveQueryMapper.UserPopRow;
import com.archiving.approve.dto.ApproveProcListParamDto;
import com.archiving.approve.dto.ApproveProcRowDto;
import com.archiving.approve.dto.ApproveStatRowDto;
import com.archiving.approve.dto.ApproveStatSearchParamDto;
import com.archiving.approve.dto.DownloadReqListParamDto;
import com.archiving.approve.dto.DownloadReqRowDto;
import com.archiving.approve.dto.MaskHistoryListParamDto;
import com.archiving.approve.dto.MaskHistoryRowDto;
import com.archiving.approve.dto.UserAuthApplyDetailParamDto;
import com.archiving.approve.dto.UserAuthApplyDetailRowDto;
import com.archiving.approve.service.ApproveQueryService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class ApproveQueryController {
	private final ApproveQueryService approveQueryService;
	private final JsonParamBinder jsonParamBinder;

	public ApproveQueryController(ApproveQueryService approveQueryService, JsonParamBinder jsonParamBinder) {
		this.approveQueryService = approveQueryService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 결재 현황 조회 API(조건검색/페이징) */
	@SuppressWarnings("unchecked")
	@GetMapping("/GetUserApproveStatSearchList")
	public String getUserApproveStatSearchList(@RequestParam(value = "param", required = false) String param) throws Exception {
		ApproveStatSearchParamDto p = jsonParamBinder.bind(param, ApproveStatSearchParamDto.class);
		if (p == null) p = new ApproveStatSearchParamDto();

		List<ApproveStatRowDto> list = approveQueryService.statList(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;

		for (ApproveStatRowDto r : list) {
			JSONObject d = new JSONObject();
			d.put("USER_CD", r.getUserCd());
			d.put("BRNM", r.getBrnm());
			d.put("NAME", r.getName());
			d.put("APPROVAL_DIV_CD", r.getApprovalDivCd());
			d.put("APPROVAL_REQ_ID", r.getApprovalReqId());
			d.put("REQ_DATE", r.getReqDate());
			d.put("APPROVAL_REQ_REASON", r.getApprovalReqReason());
			d.put("FIRST_APPROVAL_DATE", r.getFirstApprovalDate());
			d.put("FIRST_APPROVAL_LINE_USER_NM", r.getFirstApprovalLineUserNm());
			d.put("FIRST_APPROVAL_YN", r.getFirstApprovalYn());
			d.put("FIRST_APPROVAL_YN_NM", r.getFirstApprovalYnNm());
			d.put("FIRST_APPROVAL_REJECT_DOCU", r.getFirstApprovalRejectDocu());
			d.put("SECOND_APPROVAL_DATE", r.getSecondApprovalDate());
			d.put("SECOND_APPROVAL_LINE_USER_NM", r.getSecondApprovalLineUserNm());
			d.put("SECOND_APPROVAL_YN", r.getSecondApprovalYn());
			d.put("SECOND_APPROVAL_YN_NM", r.getSecondApprovalYnNm());
			d.put("SECOND_APPROVAL_REJECT_DOCU", r.getSecondApprovalRejectDocu());
			d.put("THIRD_APPROVAL_DATE", r.getThirdApprovalDate());
			d.put("THIRD_APPROVAL_LINE_USER_NM", r.getThirdApprovalLineUserNm());
			d.put("THIRD_APPROVAL_YN", r.getThirdApprovalYn());
			d.put("THIRD_APPROVAL_YN_NM", r.getThirdApprovalYnNm());
			d.put("THIRD_APPROVAL_REJECT_DOCU", r.getThirdApprovalRejectDocu());
			d.put("BULK_CNT", r.getBulkCnt());
			arr.add(d);
			nCount++;
		}

		json.put("rows", arr);
		pagination(json, nCount, p.get__rows(), p.get__page());
		return json.toString();
	}

	/* 결재 현황 조회 API(POST 호환: ArchiveGrid/DataTable 호출 유지) */
	@PostMapping("/GetUserApproveStatSearchList")
	public String getUserApproveStatSearchListPost(@RequestParam(value = "param", required = false) String param) throws Exception {
		return getUserApproveStatSearchList(param);
	}

	/* 결재 처리(관리자) 목록 조회 API */
	@SuppressWarnings("unchecked")
	@GetMapping("/GetUserApproveProcAdminList")
	public String getUserApproveProcAdminList(@RequestParam(value = "param", required = false) String param) throws Exception {
		ApproveProcListParamDto p = jsonParamBinder.bind(param, ApproveProcListParamDto.class);
		if (p == null) p = new ApproveProcListParamDto();

		List<ApproveProcRowDto> list = approveQueryService.procAdminList(p);
		return procListToJson(list, p.get__rows(), p.get__page()).toString();
	}

	/* 결재 처리(관리자) 목록 조회 API(POST 호환) */
	@PostMapping("/GetUserApproveProcAdminList")
	public String getUserApproveProcAdminListPost(@RequestParam(value = "param", required = false) String param) throws Exception {
		return getUserApproveProcAdminList(param);
	}

	/* 결재 처리(결재자) 목록 조회 API */
	@SuppressWarnings("unchecked")
	@GetMapping("/GetUserApproveProcList")
	public String getUserApproveProcList(@RequestParam(value = "param", required = false) String param) throws Exception {
		ApproveProcListParamDto p = jsonParamBinder.bind(param, ApproveProcListParamDto.class);
		if (p == null) p = new ApproveProcListParamDto();

		List<ApproveProcRowDto> list = approveQueryService.procList(p);
		return procListToJson(list, p.get__rows(), p.get__page()).toString();
	}

	/* 결재 처리(결재자) 목록 조회 API(POST 호환) */
	@PostMapping("/GetUserApproveProcList")
	public String getUserApproveProcListPost(@RequestParam(value = "param", required = false) String param) throws Exception {
		return getUserApproveProcList(param);
	}

	/* 다운로드 요청 목록 조회 API(조건검색/페이징) */
	@SuppressWarnings("unchecked")
	@GetMapping("/GetDownloadReqList")
	public String getDownloadReqList(@RequestParam(value = "param", required = false) String param) throws Exception {
		DownloadReqListParamDto p = jsonParamBinder.bind(param, DownloadReqListParamDto.class);
		if (p == null) p = new DownloadReqListParamDto();

		List<DownloadReqRowDto> list = approveQueryService.downloadReqList(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;
		for (DownloadReqRowDto r : list) {
			JSONObject d = new JSONObject();
			d.put("USER_CD", r.getUserCd());
			d.put("USER_NM", r.getUserNm());
			d.put("REQ_TIME", r.getReqTime());
			d.put("REQ_DIV", r.getReqDiv());
			d.put("REQ_NM", r.getReqNm());
			d.put("REQ_NUM", r.getReqNum());
			d.put("WARRANT_NUM", r.getWarrantNum());
			d.put("REQ_REASON", r.getReqReason());
			d.put("PROGRAM_ID", r.getProgramId());
			d.put("PROGRAM_NM", r.getProgramNm());
			d.put("REG_DATE", r.getRegDate());
			d.put("PROGRAM_WHERE", r.getProgramWhere());
			d.put("BULK_YN", r.getBulkYn());
			arr.add(d);
			nCount++;
		}
		json.put("rows", arr);
		pagination(json, nCount, p.get__rows(), p.get__page());
		return json.toString();
	}

	/* 다운로드 요청 목록 조회 API(POST 호환) */
	@PostMapping("/GetDownloadReqList")
	public String getDownloadReqListPost(@RequestParam(value = "param", required = false) String param) throws Exception {
		return getDownloadReqList(param);
	}

	/* 마스킹 처리 이력 조회 API(조건검색/페이징) */
	@SuppressWarnings("unchecked")
	@GetMapping("/GetMaskingHistoryList")
	public String getMaskingHistoryList(@RequestParam(value = "param", required = false) String param) throws Exception {
		MaskHistoryListParamDto p = jsonParamBinder.bind(param, MaskHistoryListParamDto.class);
		if (p == null) p = new MaskHistoryListParamDto();

		List<MaskHistoryRowDto> list = approveQueryService.maskHistoryList(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;
		for (MaskHistoryRowDto r : list) {
			JSONObject d = new JSONObject();
			d.put("USER_CD", r.getUserCd());
			d.put("REQ_DATE", r.getReqDate());
			d.put("APPROVAL_REQ_ID", r.getApprovalReqId());
			d.put("USER_NM", r.getUserNm());
			d.put("PROGRAM_ID", r.getProgramId());
			d.put("PROGRAM_NM", r.getProgramNm());
			d.put("PROC_DATE", r.getProcDate());
			arr.add(d);
			nCount++;
		}
		json.put("rows", arr);
		pagination(json, nCount, p.get__rows(), p.get__page());
		return json.toString();
	}

	/* 마스킹 처리 이력 조회 API(POST 호환) */
	@PostMapping("/GetMaskingHistoryList")
	public String getMaskingHistoryListPost(@RequestParam(value = "param", required = false) String param) throws Exception {
		return getMaskingHistoryList(param);
	}

	/* 결재 현황 사용자 팝업용 목록 조회 API */
	@SuppressWarnings("unchecked")
	@GetMapping("/GetUserApproveStatSearchListPop")
	public String getUserApproveStatSearchListPop(@RequestParam(value = "param", required = false) String param) throws Exception {
		ApproveStatSearchParamDto p = jsonParamBinder.bind(param, ApproveStatSearchParamDto.class);
		if (p == null) p = new ApproveStatSearchParamDto();

		List<UserPopRow> list = approveQueryService.statUserPop(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;
		for (UserPopRow r : list) {
			JSONObject d = new JSONObject();
			d.put("user_cd", r.getUserCd());
			d.put("user_nm", r.getUserNm());
			arr.add(d);
			nCount++;
		}
		json.put("data", arr);
		pagination(json, nCount, p.get__rows(), p.get__page());
		return json.toString();
	}

	/* 결재 현황 사용자 팝업용 목록 조회 API(POST 호환) */
	@PostMapping("/GetUserApproveStatSearchListPop")
	public String getUserApproveStatSearchListPopPost(@RequestParam(value = "param", required = false) String param) throws Exception {
		return getUserApproveStatSearchListPop(param);
	}

	/* 결재 처리 목록을 JSON 형태로 변환(레거시 그리드 포맷) */
	@SuppressWarnings("unchecked")
	private JSONObject procListToJson(List<ApproveProcRowDto> list, String rowsStr, String pageStr) {
		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;
		for (ApproveProcRowDto r : list) {
			JSONObject d = new JSONObject();
			d.put("USER_CD", r.getUserCd());
			d.put("USER_NM", r.getUserNm());
			d.put("APPROVAL_DIV_CD", r.getApprovalDivCd());
			d.put("APPROVAL_REQ_ID", r.getApprovalReqId());
			d.put("REQ_DATE", r.getReqDate());
			d.put("APPROVAL_REQ_REASON", r.getApprovalReqReason());
			d.put("APPROVAL_LINE_USER_ID", r.getApprovalLineUserId());
			d.put("APPROVAL_DATE", r.getApprovalDate());
			d.put("APPROVAL_YN", r.getApprovalYn());
			d.put("APPROVAL_YN_NM", r.getApprovalYnNm());
			d.put("APPROVAL_LINE_INDEX", r.getApprovalLineIndex());
			d.put("APPROVAL_REJECT_DOCU", r.getApprovalRejectDocu());
			d.put("APPROVAL_REQ_DOCU", r.getApprovalReqDocu());
			d.put("APPROVAL_LINE_USER_NM", r.getApprovalLineUserNm());
			d.put("PROGRAM_ID", r.getProgramId());
			d.put("PROGRAM_NM", r.getProgramNm());
			d.put("BRC", r.getBrc());
			d.put("BRNM", r.getBrnm());
			d.put("OFT_C", r.getOftC());
			d.put("OFT", r.getOft());
			d.put("IP_ADDRESS", r.getIpAddress());
			d.put("AUTH", r.getAuth());
			d.put("COM_CD", r.getComCd());
			d.put("EXPIRE_DATE", r.getExpireDate());
			if (StringUtils.isNotBlank(r.getBulkCnt())) d.put("BULK_CNT", r.getBulkCnt());
			arr.add(d);
			nCount++;
		}
		json.put("rows", arr);
		pagination(json, nCount, rowsStr, pageStr);
		return json;
	}

	/* 페이징/결과 공통 필드 세팅 */
	@SuppressWarnings("unchecked")
	private void pagination(JSONObject json, int nCount, String rowsStr, String pageStr) {
		if (nCount > 0) {
			int rows = safeInt(rowsStr, 10);
			int page = safeInt(pageStr, 1);
			int total = rows == 0 ? 0 : (nCount / rows);
			json.put("records", nCount);
			json.put("page", page);
			json.put("total", total);
			json.put("result", "OK");
		} else {
			json.put("result", "NOTFOUND");
		}
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private int safeInt(String v, int def) {
		try {
			return Integer.parseInt(StringUtils.defaultString(v));
		} catch (Exception e) {
			return def;
		}
	}
	
	/* 권한 신청 내용 조회 API */
	@SuppressWarnings("unchecked")
	@GetMapping("/GetUserAuthApplyDetail")
	public String GetUserAuthApplyDetail(@RequestParam(value = "param", required = false) String param) throws Exception {
		UserAuthApplyDetailParamDto p = jsonParamBinder.bind(param, UserAuthApplyDetailParamDto.class);
		if (p == null) p = new UserAuthApplyDetailParamDto();
		
		List<UserAuthApplyDetailRowDto> list = approveQueryService.getUserAuthApplyDetail(p); 
		
		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;
		for (UserAuthApplyDetailRowDto r : list) {
			JSONObject d = new JSONObject();
			d.put("USER_CD", r.getUserCd());
			d.put("USER_NM", r.getName());
			d.put("BRMM", r.getBrnm());
			d.put("APPROVAL_REQ_ID", r.getApprovalReqId());
			d.put("OFT", r.getOft());
			d.put("IP_ADDRESS", r.getIpAddress());
			d.put("APPROVAL_REQ_REASON", r.getApprovalReqReason());
			d.put("COM_CD", r.getComCd());
			d.put("EXPIRE_DATE", r.getExpireDate());
			arr.add(d);
			nCount++;
		}
		json.put("rows", arr);
		if (nCount > 0) {
			json.put("records", nCount);
			json.put("total", nCount);
			json.put("result", "OK");
		} else {
			json.put("result", "NOTFOUND");
		}
		return json.toString();
	}

	/* 권한 신청 내용 조회 API(POST 호환) */
	@PostMapping("/GetUserAuthApplyDetail")
	public String GetUserAuthApplyDetailPost(@RequestParam(value = "param", required = false) String param) throws Exception {
		return GetUserAuthApplyDetail(param);
	}
}

