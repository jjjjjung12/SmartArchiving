package com.archiving.asset.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code certList.js} expects {@code result} + {@code data} array.
 * Implement DB mapping when certificate master schema is available (현재는 빈 목록으로 UI 오류 방지).
 */
@RestController
public class CertKeyController {

	/* 인증서 키 목록 조회 API(현재는 빈 목록 반환으로 UI 오류 방지) */
	@GetMapping(value = "/GetCertiKey", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getCertiKey(@RequestParam(value = "param", required = false) String param) {
		JSONObject j = new JSONObject();
		j.put("result", "NOTFOUND");
		j.put("data", new JSONArray());
		return j.toJSONString();
	}
}
