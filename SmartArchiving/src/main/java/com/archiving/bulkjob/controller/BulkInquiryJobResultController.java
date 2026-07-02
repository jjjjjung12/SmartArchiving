package com.archiving.bulkjob.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.bulkjob.dao.mapper.BulkInquiryJobResultMapper.MenuRow;
import com.archiving.bulkjob.dto.BulkInquiryJobResultParamDto;
import com.archiving.bulkjob.service.BulkInquiryJobResultService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class BulkInquiryJobResultController {
	private final BulkInquiryJobResultService service;
	private final JsonParamBinder jsonParamBinder;

	public BulkInquiryJobResultController(BulkInquiryJobResultService service, JsonParamBinder jsonParamBinder) {
		this.service = service;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 벌크조회 결과 메뉴 목록 조회 API(전체/사용자별) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetBulkInquiryJobResultList")
	public String getBulkInquiryJobResultList(@RequestParam(value = "param", required = false) String param) throws Exception {
		BulkInquiryJobResultParamDto p = jsonParamBinder.bind(param, BulkInquiryJobResultParamDto.class);
		if (p == null) p = new BulkInquiryJobResultParamDto();

		String rowsStr = StringUtils.defaultString(p.get__rows(), "100");
		String pageStr = StringUtils.defaultString(p.get__page(), "1");

		List<MenuRow> list = service.list(p);

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");
		JSONArray arr = new JSONArray();
		int nCount = 0;
		for (MenuRow r : list) {
			JSONObject d = new JSONObject();
			d.put("MENU_CD", r.getMenuCd());
			d.put("MENU_NM", r.getMenuNm());
			d.put("MENU_URL", r.getMenuUrl());
			d.put("MENU_ORDER", r.getMenuOrder());
			d.put("MENU_ID", r.getMenuId());
			d.put("MENU_DESC", r.getMenuDesc());
			d.put("UP_MENU_ID", r.getUpMenuId());
			d.put("USE_YN", r.getUseYn());
			d.put("MENU_YN", r.getMenuYn());
			d.put("ICON_CLASS_ID", r.getIconClassId());
			arr.add(d);
			nCount++;
		}

		json.put("rows", arr);

		if (nCount > 0) {
			int rows = safeInt(rowsStr, 100);
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

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private int safeInt(String v, int def) {
		try {
			return Integer.parseInt(StringUtils.defaultString(v));
		} catch (Exception e) {
			return def;
		}
	}
}

