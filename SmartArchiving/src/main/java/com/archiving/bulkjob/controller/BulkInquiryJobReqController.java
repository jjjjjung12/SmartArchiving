package com.archiving.bulkjob.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.bulkjob.dto.BulkInquiryJobReqSaveParamDto;
import com.archiving.bulkjob.service.BulkInquiryJobReqService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class BulkInquiryJobReqController {
	private final BulkInquiryJobReqService bulkInquiryJobReqService;
	private final JsonParamBinder jsonParamBinder;

	public BulkInquiryJobReqController(BulkInquiryJobReqService bulkInquiryJobReqService, JsonParamBinder jsonParamBinder) {
		this.bulkInquiryJobReqService = bulkInquiryJobReqService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 벌크조회 요청 저장 API(결재요청/결재라인/벌크요청 등록) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetBulkInquiryJobReq")
	public String setBulkInquiryJobReq(@RequestParam(value = "param", required = false) String param) throws Exception {
		JSONObject json = new JSONObject();
		try {
			BulkInquiryJobReqSaveParamDto p = jsonParamBinder.bind(param, BulkInquiryJobReqSaveParamDto.class);
			if (p == null) p = new BulkInquiryJobReqSaveParamDto();
			bulkInquiryJobReqService.save(p);
			json.put("result", "OK");
		} catch (Exception e) {
			json.put("result", "ERR");
			json.put("msg", e.getMessage());
		}
		return json.toString();
	}
}

