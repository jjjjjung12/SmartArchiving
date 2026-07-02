package com.archiving.comm.controller;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.comm.dao.mapper.DeptMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@code modalDept.js} / {@code modalSabun.js} dynatree init.
 */
@RestController
public class DeptListController {

	private final DeptMapper deptMapper;
	private final ObjectMapper objectMapper;

	public DeptListController(DeptMapper deptMapper, ObjectMapper objectMapper) {
		this.deptMapper = deptMapper;
		this.objectMapper = objectMapper;
	}

	/* 부서(지점) 목록 조회 API(dynatree 노드 형태) */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/GetDeptList", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getDeptList(@RequestParam(value = "param", required = false) String param) {
		List<Map<String, Object>> rows = deptMapper.selectInsaDeptNodes();
		JSONArray arr = new JSONArray();
		for (Map<String, Object> m : rows) {
			JSONObject n = new JSONObject();
			Object title = m.get("title");
			Object key = m.get("key");
			n.put("title", title != null ? title : "");
			n.put("key", key != null ? key : "");
			n.put("isFolder", Boolean.FALSE);
			arr.add(n);
		}
		return arr.toJSONString();
	}

	/* 부서/사번/이름 조건으로 직원 목록 조회 API */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/GetEmpList", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getEmpList(@RequestParam(value = "param", required = false) String param) throws Exception {
		String name = "*";
		String sabun = "*";
		String orgcd = "*";
		if (param != null && !param.isBlank()) {
			Map<String, Object> p = objectMapper.readValue(param, Map.class);
			Object n = p.get("name");
			Object s = p.get("sabun");
			Object o = p.get("orgcd");
			name = n == null ? "*" : String.valueOf(n);
			sabun = s == null ? "*" : String.valueOf(s);
			orgcd = o == null ? "*" : String.valueOf(o);
		}

		List<Map<String, Object>> rows = deptMapper.selectEmpListByDept(name, sabun, orgcd);
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (Map<String, Object> m : rows) {
			JSONObject d = new JSONObject();
			d.put("name", m.get("name") == null ? "" : String.valueOf(m.get("name")));
			d.put("sabun", m.get("sabun") == null ? "" : String.valueOf(m.get("sabun")));
			data.add(d);
		}
		json.put("data", data);
		json.put("result", data.isEmpty() ? "NOTFOUND" : "OK");
		return json.toJSONString();
	}
}
