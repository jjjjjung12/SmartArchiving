package com.archiving.bulkjob.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.bulkjob.dto.ServiceInfoParamDto;
import com.archiving.bulkjob.dto.ServiceInfoRowDto;
import com.archiving.bulkjob.dto.ServiceInfoSaveParamDto;
import com.archiving.bulkjob.service.ServiceInfoService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class ServiceInfoController {
	private final ServiceInfoService service;
	private final JsonParamBinder jsonParamBinder;

	public ServiceInfoController(ServiceInfoService service, JsonParamBinder jsonParamBinder) {
		this.service = service;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 서비스 정보 목록 조회 API(환경 DB에 따라 EFBL/DFSL 조회) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetServiceInfo")
	public String getServiceInfo(@RequestParam(value = "param", required = false) String param) throws Exception {
		ServiceInfoParamDto p = jsonParamBinder.bind(param, ServiceInfoParamDto.class);
		if (p == null) p = new ServiceInfoParamDto();

		List<ServiceInfoRowDto> list = service.list(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;

		for (ServiceInfoRowDto r : list) {
			JSONObject d = new JSONObject();
			d.put("application_group_id", r.getApplicationGroupId());
			d.put("service_id", r.getServiceId());
			d.put("sel_gubun", r.getSelGubun());
			d.put("del_yn", r.getDelYn());
			arr.add(d);
			nCount++;
		}

		json.put("rows", arr);

		String rowsStr = StringUtils.defaultString(p.get__rows(), "20");
		String pageStr = StringUtils.defaultString(p.get__page(), "1");
		if (nCount > 0) {
			int rows = safeInt(rowsStr, 20);
			int page = safeInt(pageStr, 1);
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

	/* 서비스 정보 저장 API(일반 사용자) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetServiceInfo")
	public String setServiceInfo(@RequestParam(value = "param", required = false) String param) throws Exception {
		JSONObject json = new JSONObject();
		try {
			ServiceInfoSaveParamDto p = jsonParamBinder.bind(param, ServiceInfoSaveParamDto.class);
			if (p == null) p = new ServiceInfoSaveParamDto();
			service.save(p);
			json.put("result", "OK");
		} catch (Exception e) {
			json.put("result", "ERR");
			json.put("msg", e.getMessage());
		}
		return json.toString();
	}

	/* 서비스 정보 관리자 저장 API(전체 교체) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetServiceInfoAdmin")
	public String setServiceInfoAdmin(@RequestParam(value = "param", required = false) String param) throws Exception {
		JSONObject json = new JSONObject();
		try {
			ServiceInfoSaveParamDto p = jsonParamBinder.bind(param, ServiceInfoSaveParamDto.class);
			if (p == null) p = new ServiceInfoSaveParamDto();
			service.adminReplaceAll(p);
			json.put("result", "OK");
		} catch (Exception e) {
			json.put("result", "ERR");
			json.put("msg", e.getMessage());
		}
		return json.toString();
	}

	/* (레거시) 서비스 정보 1건 저장 API(파라미터 키 변환 후 저장) */
	// legacy "one row" endpoint uses different param keys; kept separate
	@SuppressWarnings("unchecked")
	@PostMapping("/SetServiceInfoOne")
	public String setServiceInfoOne(@RequestParam(value = "param", required = false) String param) throws Exception {
		JSONObject json = new JSONObject();
		try {
			JSONObject in = (JSONObject) new org.json.simple.parser.JSONParser().parse(StringUtils.defaultString(param, "{}"));
			JSONArray arr = new JSONArray();
			JSONObject row = new JSONObject();
			row.put("application_group_id", in.get("APPLICATION_GROUP_ID"));
			row.put("service_id", in.get("SERVICE_ID"));
			row.put("sel_gubun", in.get("SEL_GUBUN"));
			row.put("del_yn", in.get("DEL_YN"));
			arr.add(row);
			ServiceInfoSaveParamDto p = new ServiceInfoSaveParamDto();
			p.setServiceInfoList(arr.toString());
			service.save(p);
			json.put("result", "OK");
		} catch (Exception e) {
			json.put("result", "ERR");
			json.put("msg", e.getMessage());
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

