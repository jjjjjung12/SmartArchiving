package com.archiving.approve.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.approve.dto.TranMaskReqParamDto;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class TranMaskReqController {
	private final JsonParamBinder jsonParamBinder;

	public TranMaskReqController(JsonParamBinder jsonParamBinder) {
		this.jsonParamBinder = jsonParamBinder;
	}

	/* (레거시) 마스킹 해제 요청 엔드포인트(현재는 OK만 반환) */
	/**
	 * Legacy endpoint contract: transactionMaskPop.js calls this and only checks {result:"OK"}.
	 * The old servlet contained incomplete SQL; we keep the endpoint stable and return OK.
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetTranMaskReq")
	public String setTranMaskReq(@RequestParam(value = "param", required = false) String param) throws Exception {
		// bind to validate json shape; currently not persisted because legacy SQL was incomplete.
		jsonParamBinder.bind(param, TranMaskReqParamDto.class);

		JSONObject json = new JSONObject();
		json.put("result", "OK");
		return json.toString();
	}
}

