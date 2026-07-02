package com.archiving.comm.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.comm.dto.DownloadReqCheckParamDto;
import com.archiving.comm.dto.DownloadReqSaveParamDto;
import com.archiving.comm.service.DownloadReqService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class DownloadReqController {
	private final DownloadReqService downloadReqService;
	private final JsonParamBinder jsonParamBinder;

	public DownloadReqController(DownloadReqService downloadReqService, JsonParamBinder jsonParamBinder) {
		this.downloadReqService = downloadReqService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 다운로드 요청 가능 여부 조회 API(당일 요청 여부 및 지점정보 반환) */
	@SuppressWarnings("unchecked")
	@GetMapping("/GetDownloadReq")
	public String getDownloadReq(@RequestParam(value = "param", required = false) String param) throws Exception {
		DownloadReqCheckParamDto p = jsonParamBinder.bind(param, DownloadReqCheckParamDto.class);
		DownloadReqService.Result r = downloadReqService.checkToday(p);

		JSONObject json = new JSONObject();
		json.put("result", r.getResult());
		if ("NotFound".equals(r.getResult())) {
			json.put("brc", r.getBrc());
			json.put("brnm", r.getBrnm());
		}
		return json.toString();
	}

	/* 다운로드 요청 등록 API */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetDownloadReq")
	public String setDownloadReq(@RequestParam(value = "param", required = false) String param) throws Exception {
		DownloadReqSaveParamDto p = jsonParamBinder.bind(param, DownloadReqSaveParamDto.class);
		if (p == null) p = new DownloadReqSaveParamDto();

		downloadReqService.save(p);

		JSONObject json = new JSONObject();
		json.put("result", "OK");
		return json.toString();
	}
}

