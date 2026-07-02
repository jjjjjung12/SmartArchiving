package com.archiving.comm.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.comm.dto.WorkListParamDto;
import com.archiving.comm.dto.WorkRowDto;
import com.archiving.comm.dto.WorkSaveParamDto;
import com.archiving.comm.service.WorkService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class WorkController {
	private final WorkService workService;
	private final JsonParamBinder jsonParamBinder;

	public WorkController(WorkService workService, JsonParamBinder jsonParamBinder) {
		this.workService = workService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 업무 목록 조회 API(조건검색/페이징) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetWorkList")
	public String getWorkList(@RequestParam(value = "param", required = false) String param) throws Exception {
		WorkListParamDto p = jsonParamBinder.bind(param, WorkListParamDto.class);
		if (p == null) p = new WorkListParamDto();

		String rowsStr = StringUtils.defaultString(p.get__rows(), "10");
		String pageStr = StringUtils.defaultString(p.get__page(), "1");

		List<WorkRowDto> list = workService.list(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");

		JSONArray arr = new JSONArray();
		int nCount = 0;
		for (WorkRowDto r : list) {
			JSONObject d = new JSONObject();
			d.put("SERVER_ID", r.getServerId());
			d.put("SERVER_NM", r.getServerNm());
			d.put("WORK_CD", r.getWorkCd());
			d.put("WORK_NM", r.getWorkNm());
			d.put("USE_YN", r.getUseYn());
			d.put("ACCOUNT_CD", StringUtils.defaultIfBlank(r.getAccountCd(), " "));
			d.put("DESCRIPTION", StringUtils.defaultIfBlank(r.getDescription(), " "));
			arr.add(d);
			nCount++;
		}

		json.put("rows", arr);

		if (nCount > 0) {
			int rows = safeInt(rowsStr, 10);
			int page = safeInt(pageStr, 1);
			int total = rows == 0 ? 0 : (nCount / rows);
			json.put("result", "OK");
			json.put("records", nCount);
			json.put("page", page);
			json.put("total", total);
		} else {
			json.put("result", "NOTFOUND");
		}

		return json.toString();
	}

	/* 업무 저장 API(C/U/D 처리) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetWork")
	public String setWork(@RequestParam(value = "param", required = false) String param) throws Exception {
		WorkSaveParamDto p = jsonParamBinder.bind(param, WorkSaveParamDto.class);
		if (p == null) p = new WorkSaveParamDto();
		workService.save(p);

		JSONObject json = new JSONObject();
		json.put("result", "OK");
		return json.toString();
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private int safeInt(String v, int def) {
		try {
			return Integer.parseInt(v);
		} catch (Exception e) {
			return def;
		}
	}
}

