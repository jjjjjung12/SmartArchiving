package com.archiving.comm.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.comm.dao.mapper.UserPopMapper.UserRow;
import com.archiving.comm.dto.UserListPopParamDto;
import com.archiving.comm.service.UserPopService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class UserPopController {
	private final UserPopService userPopService;
	private final JsonParamBinder jsonParamBinder;

	public UserPopController(UserPopService userPopService, JsonParamBinder jsonParamBinder) {
		this.userPopService = userPopService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 사용자 선택 팝업용 사용자 목록 조회 API */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetUserListPop")
	public String getUserListPop(@RequestParam(value = "param", required = false) String param) throws Exception {
		UserListPopParamDto p = jsonParamBinder.bind(param, UserListPopParamDto.class);
		if (p == null) p = new UserListPopParamDto();

		List<UserRow> list = userPopService.getUsers(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;

		for (UserRow r : list) {
			JSONObject d = new JSONObject();
			d.put("user_cd", r.getUserCd());
			d.put("user_nm", r.getUserNm());
			arr.add(d);
			nCount++;
		}

		json.put("data", arr);

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
			return Integer.parseInt(v);
		} catch (Exception e) {
			return def;
		}
	}
}

