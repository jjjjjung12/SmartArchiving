package com.archiving.approve.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.approve.dto.UserApproveProcParamDto;
import com.archiving.approve.service.ApproveProcService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class ApproveProcController {
	private final ApproveProcService approveProcService;
	private final JsonParamBinder jsonParamBinder;

	public ApproveProcController(ApproveProcService approveProcService, JsonParamBinder jsonParamBinder) {
		this.approveProcService = approveProcService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 결재 처리 저장 API(승인/반려 처리) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetUserApproveProc")
	public String setUserApproveProc(@RequestParam(value = "param", required = false) String param) throws Exception {
		JSONObject json = new JSONObject();
		try {
			UserApproveProcParamDto p = jsonParamBinder.bind(param, UserApproveProcParamDto.class);
			if (p == null) p = new UserApproveProcParamDto();
			approveProcService.process(p);
			json.put("result", "OK");
		} catch (Exception e) {
			json.put("result", "ERR");
			json.put("msg", e.getMessage());
		}
		return json.toString();
	}
}

