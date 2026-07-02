package com.archiving.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.auth.dto.AuthListParamDto;
import com.archiving.auth.dto.AuthMenuSimpleDto;
import com.archiving.auth.dto.UserGroupDto;
import com.archiving.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AuthController {

	private final AuthService authService;
	private final ObjectMapper objectMapper;

	public AuthController(AuthService authService, ObjectMapper objectMapper) {
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	/* 권한 조회 API(__gb=A:그룹, B:미등록메뉴, G:등록메뉴) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetAuthList", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getAuthList(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		AuthListParamDto param = (paramJson == null || paramJson.isBlank())
			? new AuthListParamDto()
			: objectMapper.readValue(paramJson, AuthListParamDto.class);

		String gb = param.get__gb();
		String userGb = param.get__user_gb();
		int rows = safeInt(param.get__rows(), 20);
		int page = safeInt(param.get__page(), 1);

		JSONArray outRows = new JSONArray();
		int nCount = 0;

		if ("A".equalsIgnoreCase(gb)) {
			List<UserGroupDto> groups = authService.getActiveGroups();
			for (UserGroupDto g : groups) {
				JSONObject datas = new JSONObject();
				datas.put("USER_GRP_ID", g.getUserGrpId());
				datas.put("USER_GRP_NM", g.getUserGrpNm());
				outRows.add(datas);
				nCount++;
			}
		} else if ("B".equalsIgnoreCase(gb)) {
			List<AuthMenuSimpleDto> menus = authService.getMenusNotInGroup(userGb);
			for (AuthMenuSimpleDto m : menus) {
				JSONObject datas = new JSONObject();
				datas.put("MENU_ID", m.getMenuId());
				datas.put("MENU_NM", m.getMenuNm());
				datas.put("MENU_URL", m.getMenuUrl());
				datas.put("MENU_ORDER", m.getMenuOrder());
				outRows.add(datas);
				nCount++;
			}
		} else if ("G".equalsIgnoreCase(gb)) {
			List<AuthMenuSimpleDto> menus = authService.getMenusInGroup(userGb);
			for (AuthMenuSimpleDto m : menus) {
				JSONObject datas = new JSONObject();
				datas.put("MENU_ID", m.getMenuId());
				datas.put("MENU_CD", m.getMenuCd());
				datas.put("MENU_NM", m.getMenuNm());
				datas.put("MENU_URL", m.getMenuUrl());
				datas.put("MENU_ORDER", m.getMenuOrder());
				outRows.add(datas);
				nCount++;
			}
		}

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("rows", outRows);
		jsonobj.put("records", nCount);
		jsonobj.put("page", page);
		jsonobj.put("total", rows <= 0 ? 0 : (int) Math.ceil((double) nCount / rows));
		jsonobj.put("result", nCount > 0 ? "OK" : "NOTFOUND");

		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}

	/* 그룹 권한(메뉴) 일괄 저장(기존 삭제 후 재등록) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/SetAuth", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> setAuth(@RequestParam("user_gb") String userGb,
		@RequestParam(value = "param", required = false) String paramJson) throws Exception {

		List<String> menuIds = new ArrayList<>();
		if (paramJson != null && !paramJson.isBlank()) {
			JSONArray arr = (JSONArray) new org.json.simple.parser.JSONParser().parse(paramJson);
			for (Object o : arr) {
				JSONObject j = (JSONObject) o;
				Object menuId = j.get("menu_id");
				if (menuId != null) menuIds.add(menuId.toString());
			}
		}

		authService.replaceGroupAuth(userGb, menuIds);

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", "OK");

		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private int safeInt(String v, int def) {
		try {
			if (v == null) return def;
			return Integer.parseInt(v);
		} catch (Exception e) {
			return def;
		}
	}
}

