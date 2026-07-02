package com.archiving.auth.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.auth.dto.MenuItemDto;
import com.archiving.auth.dto.MenuListParamDto;
import com.archiving.auth.service.MenuService;
import com.archiving.auth.support.MenuUrlNormalizer;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MenuController {

	private final MenuService menuService;
	private final ObjectMapper objectMapper;

	public MenuController(MenuService menuService, ObjectMapper objectMapper) {
		this.menuService = menuService;
		this.objectMapper = objectMapper;
	}

	/* 사용자 메뉴 목록 조회 API(레거시 menuData/rows 포맷 유지) */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/GetMenuList", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getMenuList(HttpServletRequest request,
		@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		MenuListParamDto param = (paramJson == null || paramJson.isBlank())
			? new MenuListParamDto()
			: objectMapper.readValue(paramJson, MenuListParamDto.class);

		List<MenuItemDto> menus = menuService.getMenus(param.getUser_cd(), param.getSel_cd(), param.getUser_id());
		final String ctx = request.getContextPath();

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", menus.isEmpty() ? "NOTFOUND" : "OK");

		JSONArray items = new JSONArray();
		for (MenuItemDto m : menus) {
			JSONObject item = new JSONObject();
			item.put("MENU_CD", m.getMenuCd());
			item.put("MENU_NM", m.getMenuNm());
			item.put("MENU_URL", MenuUrlNormalizer.toClientMenuUrl(ctx, m.getMenuUrl()));
			item.put("MENU_ID", m.getMenuId());
			item.put("MENU_DESC", m.getMenuDesc());
			item.put("MENU_ORDER", m.getMenuOrder());
			item.put("UP_MENU_ID", m.getUpMenuId());
			item.put("MENU_YN", m.getMenuYn());
			item.put("USE_YN", m.getUseYn());
			item.put("ICON_CLASS_ID", m.getIconClassId());
			items.add(item);
		}
		// Legacy JSP expects `menuData`; keep `rows` for consistency with grid-style endpoints.
		jsonobj.put("menuData", items);
		jsonobj.put("rows", items);

		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}

	/* 관리자 메뉴 검색 목록 조회 API */
	@PostMapping(value = "/GetMenuList", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getMenuListPost(HttpServletRequest request,
		@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		MenuListParamDto param = (paramJson == null || paramJson.isBlank())
			? new MenuListParamDto()
			: objectMapper.readValue(paramJson, MenuListParamDto.class);

		List<MenuItemDto> menus = menuService.getAdminMenus(param.getMenu_nm(), param.getUrl(), param.getUse_yn());
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", menus.isEmpty() ? "NOTFOUND" : "OK");

		JSONArray items = new JSONArray();
		for (MenuItemDto m : menus) {
			JSONObject item = new JSONObject();
			item.put("MENU_CD", m.getMenuCd());
			item.put("MENU_NM", m.getMenuNm());
			item.put("MENU_URL", MenuUrlNormalizer.toClientMenuUrl(request.getContextPath(), m.getMenuUrl()));
			item.put("MENU_ID", m.getMenuId());
			item.put("MENU_DESC", m.getMenuDesc());
			item.put("MENU_ORDER", m.getMenuOrder());
			item.put("UP_MENU_ID", m.getUpMenuId());
			item.put("MENU_YN", m.getMenuYn());
			item.put("USE_YN", m.getUseYn());
			item.put("ICON_CLASS_ID", m.getIconClassId());
			items.add(item);
		}
		jsonobj.put("rows", items);
		jsonobj.put("records", menus.size());
		jsonobj.put("page", 1);
		jsonobj.put("total", menus.isEmpty() ? 0 : 1);

		return ResponseEntity.ok()
			.contentType(new MediaType("text", "plain"))
			.body(jsonobj.toString());
	}

	/* 메뉴 목록 조회 API 별칭(GetMenuList로 위임) */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/GetMenuList2", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getMenuList2(HttpServletRequest request,
		@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		return getMenuList(request, paramJson);
	}

}

