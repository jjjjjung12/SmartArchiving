package com.archiving.auth.controller;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.auth.dto.MenuSaveParamDto;
import com.archiving.auth.service.MenuAdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MenuAdminController {

	private final MenuAdminService menuAdminService;
	private final ObjectMapper objectMapper;

	public MenuAdminController(MenuAdminService menuAdminService, ObjectMapper objectMapper) {
		this.menuAdminService = menuAdminService;
		this.objectMapper = objectMapper;
	}

	/* 메뉴 마스터 저장 API(C/U/D/DS) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/SetMenuList", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> setMenuList(@RequestParam("param") String paramJson) throws Exception {
		// DS payload has "menu_id": [ {menu_id:..}, ... ] — map it into menu_id_list
		MenuSaveParamDto p = objectMapper.readValue(paramJson, MenuSaveParamDto.class);

		menuAdminService.save(p);

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", "OK");
		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}
}

