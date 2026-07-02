package com.archiving.asset.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.asset.dto.TranMaskDto;
import com.archiving.asset.dto.TranMaskListParamDto;
import com.archiving.asset.dto.TranMaskSaveParamDto;
import com.archiving.asset.service.TranMaskService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class TranMaskController {
	private final TranMaskService tranMaskService;
	private final JsonParamBinder jsonParamBinder;

	public TranMaskController(TranMaskService tranMaskService, JsonParamBinder jsonParamBinder) {
		this.tranMaskService = tranMaskService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 전문 마스킹 규칙 목록 조회 API(조건검색/페이징) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetTranMaskList")
	public String getTranMaskList(@RequestParam(value = "param", required = false) String param) throws Exception {
		TranMaskListParamDto p = jsonParamBinder.bind(param, TranMaskListParamDto.class);
		String tgrmNmEng = p == null ? "" : StringUtils.defaultString(p.get__tgrm_nm_eng());
		String rows = p == null ? "10" : StringUtils.defaultString(p.get__rows(), "10");
		String page = p == null ? "1" : StringUtils.defaultString(p.get__page(), "1");

		List<TranMaskDto> list = tranMaskService.list(tgrmNmEng);

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();
		jsonobj.put("result", "ERROR");

		for (TranMaskDto r : list) {
			JSONObject datas = new JSONObject();
			datas.put("TGRM_NM_ENG", StringUtils.defaultString(r.getTgrmNmEng()));
			datas.put("TGRM_NM_KOR", StringUtils.defaultString(r.getTgrmNmKor()));
			datas.put("MSK_PTTRN", StringUtils.defaultString(r.getMskPttrn()));
			datas.put("CHG_DTM", StringUtils.defaultString(r.getChgDtm()));
			seriesArray.add(datas);
		}

		jsonobj.put("rows", seriesArray);

		int nCount = list.size();
		if (nCount > 0) {
			int total = nCount / Integer.parseInt(rows);
			jsonobj.put("records", nCount);
			jsonobj.put("page", Integer.parseInt(page));
			jsonobj.put("total", total);
			jsonobj.put("result", "OK");
		} else {
			jsonobj.put("result", "NOTFOUND");
		}

		return jsonobj.toString();
	}

	/* 전문 마스킹 규칙 저장 API(C/U/D 처리) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetTranMask")
	@Transactional
	public String setTranMask(@RequestParam(value = "param", required = false) String param) throws Exception {
		TranMaskSaveParamDto p = jsonParamBinder.bind(param, TranMaskSaveParamDto.class);
		JSONObject jsonobj = new JSONObject();
		try {
			tranMaskService.save(p);
			jsonobj.put("result", "OK");
			jsonobj.put("genKey", 0);
		} catch (Exception e) {
			jsonobj.put("result", "ERROR");
			jsonobj.put("msg", e.getMessage());
			throw e;
		}
		return jsonobj.toString();
	}
}

