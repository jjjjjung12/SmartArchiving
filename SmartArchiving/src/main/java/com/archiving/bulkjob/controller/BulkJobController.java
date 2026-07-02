package com.archiving.bulkjob.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.bulkjob.dto.BulkDataReqRowDto;
import com.archiving.bulkjob.dto.BulkInquiryReqListParamDto;
import com.archiving.bulkjob.dto.BulkReqJobParamDto;
import com.archiving.bulkjob.dto.BulkReqJobRowDto;
import com.archiving.bulkjob.service.BulkJobService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class BulkJobController {
	private final BulkJobService bulkJobService;
	private final JsonParamBinder jsonParamBinder;

	public BulkJobController(BulkJobService bulkJobService, JsonParamBinder jsonParamBinder) {
		this.bulkJobService = bulkJobService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 벌크잡 요청 목록 조회 API(결재요청 ID 기준) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetBulkReqJobList")
	public String getBulkReqJobList(@RequestParam(value = "param", required = false) String param) throws Exception {
		BulkReqJobParamDto p = jsonParamBinder.bind(param, BulkReqJobParamDto.class);
		List<BulkReqJobRowDto> list = bulkJobService.getBulkReqJobs(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;
		for (BulkReqJobRowDto r : list) {
			JSONObject d = new JSONObject();
			d.put("START_DATE", r.getStartDate());
			d.put("END_DATE", r.getEndDate());
			d.put("ACCOUNT_NO", r.getAccountNo());
			d.put("USER_ID", r.getUserId());
			d.put("CUST_REG_NO", r.getCustRegNo());
			d.put("TEL_NO", r.getTelNo());
			d.put("USER_IP", r.getUserIp());
			d.put("ORG_FILE_NM", r.getOrgFileNm());
			d.put("U_FILE_NM", r.getUFileNm());
			d.put("FILE_URL", r.getFileUrl());
			d.put("BATCH_YN", r.getBatchYn());
			d.put("APPLY_YN", r.getApplyYn());
			d.put("APPROVAL_REQ_ID", r.getApprovalReqId());
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

	/* (레거시 호환) 벌크잡 요청 목록 조회 API(GetBulkReqJobList로 위임) */
	@PostMapping("/GetBulkIReqJobList")
	public String getBulkIReqJobList(@RequestParam(value = "param", required = false) String param) throws Exception {
		return getBulkReqJobList(param);
	}

	/* 벌크조회 요청 목록 조회 API(조건검색/페이징) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetBulkInquiryJobReqList")
	public String getBulkInquiryJobReqList(@RequestParam(value = "param", required = false) String param) throws Exception {
		BulkInquiryReqListParamDto p = jsonParamBinder.bind(param, BulkInquiryReqListParamDto.class);
		if (p == null) p = new BulkInquiryReqListParamDto();

		List<BulkDataReqRowDto> list = bulkJobService.getBulkInquiryReqList(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;
		for (BulkDataReqRowDto r : list) {
			JSONObject d = new JSONObject();
			d.put("USER_CD", r.getUserCd());
			d.put("USER_NM", r.getUserNm());
			d.put("REQ_DATE", r.getReqDate());
			d.put("SEARCH_DIV_CD", r.getSearchDivCd());
			d.put("SEARCH_DIV_VAL", r.getSearchDivVal());
			d.put("WARRANT_NUM", r.getWarrantNum());
			d.put("START_DATE", r.getStartDate());
			d.put("END_DATE", r.getEndDate());
			d.put("FILE_NM", r.getFileNm());
			d.put("FILE_URL", r.getFileUrl());
			d.put("BATCH_YN", r.getBatchYn());
			d.put("APPLY_YN", r.getApplyYn());
			d.put("APPROVAL_REQ_ID", r.getApprovalReqId());
			arr.add(d);
			nCount++;
		}

		json.put("rows", arr);
		if (nCount > 0) {
			int rows = safeInt(p.get__rows(), 10);
			int page = safeInt(p.get__page(), 1);
			int total = rows == 0 ? 0 : (nCount / rows);
			json.put("records", nCount);
			json.put("page", page);
			json.put("total", total);
			json.put("result", "OK");
		} else {
			json.put("result", "NOTFOUND");
		}
		return json.toString();
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private int safeInt(String v, int def) {
		try {
			return Integer.parseInt(StringUtils.defaultString(v));
		} catch (Exception e) {
			return def;
		}
	}
}

