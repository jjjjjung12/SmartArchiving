package com.archiving.monitor.controller;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.monitor.dto.MonitorParamDto;
import com.archiving.monitor.service.MonitorService;
import com.archiving.mvc.util.JsonParamBinder;
import com.archiving.mvc.util.LegacyGridJson;

@RestController
public class MonitorController {
	private final MonitorService service;
	private final JsonParamBinder binder;

	public MonitorController(MonitorService service, JsonParamBinder binder) {
		this.service = service;
		this.binder = binder;
	}

	/* 모니터링 데이터 조회 API(__gb에 따라 사용자/메뉴 권한 조회) */
	@PostMapping(value = "/GetMonitor", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getMonitor(@RequestParam(value = "param", required = false) String param) throws Exception {
		MonitorParamDto p = binder.bind(param, MonitorParamDto.class);
		List<Map<String, Object>> rows = service.list(nvl(p.get__gb()), nvl(p.get__user_gb()), nvl(p.get__user_id()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* null 문자열을 빈 문자열로 치환 */
	private static String nvl(String v) {
		return v == null ? "" : v;
	}
}

