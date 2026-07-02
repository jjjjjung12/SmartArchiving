package com.archiving.comm.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.comm.dto.CodeDetailDto;
import com.archiving.comm.dto.CodeGroupDto;
import com.archiving.comm.dto.CodeManagerParamDto;
import com.archiving.comm.service.CodeService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class CodeController {
	private final CodeService codeService;
	private final JsonParamBinder jsonParamBinder;

	public CodeController(CodeService codeService, JsonParamBinder jsonParamBinder) {
		this.codeService = codeService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 코드상세 목록 조회 API(그룹/코드 조건) */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/GetCodeList", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getCodeList(
			@RequestParam("class") String groupCd,
			@RequestParam("code") String code
	) {
		List<CodeDetailDto> list = codeService.listDetails(groupCd, code);

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();

		int nCount = 0;
		for (CodeDetailDto r : list) {
			JSONObject datas = new JSONObject();
			datas.put("idx", nCount + 1);
			datas.put("_CODE_", r.getDetailCd());
			datas.put("_NAME_", r.getDetailNm());
			seriesArray.add(datas);
			nCount++;
		}
		jsonobj.put("data", seriesArray);

		if (nCount > 0) {
			jsonobj.put("result", "OK");
			jsonobj.put("count", nCount);
		} else {
			jsonobj.put("result", "NOTFOUND");
			jsonobj.put("count", 0);
		}

		return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(jsonobj.toString());
	}

	/* 코드관리 조회 API(R:그룹목록, RD:상세목록) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetCodeManager", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> getCodeManager(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		CodeManagerParamDto p = jsonParamBinder.bind(paramJson, CodeManagerParamDto.class);
		String crud = p == null ? "" : StringUtils.defaultString(p.get__crud());
		String groupCd = p == null ? "" : StringUtils.defaultString(p.get__group_cd());
		String rows = p == null ? "20" : StringUtils.defaultString(p.get__rows(), "20");
		String page = p == null ? "1" : StringUtils.defaultString(p.get__page(), "1");

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();
		int nCount = 0;

		String groupNm = p == null ? "" : StringUtils.defaultString(p.get__group_nm());
		String useYn = p == null ? "Y" : StringUtils.defaultIfBlank(p.get__use_yn(), "Y");

		if ("R".equals(crud)) {
			List<CodeGroupDto> list = codeService.listGroups(groupNm, useYn);
			for (CodeGroupDto r : list) {
				JSONObject datas = new JSONObject();
				datas.put("GROUP_CD", r.getGroupCd());
				datas.put("GROUP_NM", r.getGroupNm());
				datas.put("USE_YN", r.getUseYn());
				seriesArray.add(datas);
				nCount++;
			}
		} else if ("RD".equals(crud)) {
			List<CodeDetailDto> list = codeService.listDetailsByGroup(groupCd, useYn);
			for (CodeDetailDto r : list) {
				JSONObject datas = new JSONObject();
				datas.put("GROUP_CD", r.getGroupCd());
				datas.put("DETAIL_CD", r.getDetailCd());
				datas.put("DETAIL_NM", r.getDetailNm());
				datas.put("USE_YN", r.getUseYn());
				seriesArray.add(datas);
				nCount++;
			}
		}

		jsonobj.put("rows", seriesArray);
		if (nCount > 0) {
			int total = nCount / Integer.parseInt(rows);
			jsonobj.put("records", nCount);
			jsonobj.put("page", Integer.parseInt(page));
			jsonobj.put("total", total);
			jsonobj.put("result", "OK");
		} else {
			jsonobj.put("result", "NOTFOUND");
		}

		return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(jsonobj.toString());
	}

	/* 코드관리 저장 API(C/U/D/상세 CRUD 처리) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/SetCodeManager", produces = MediaType.TEXT_PLAIN_VALUE)
	@Transactional
	public ResponseEntity<String> setCodeManager(@RequestParam(value = "param", required = false) String paramJson) throws Exception {
		CodeManagerParamDto p = jsonParamBinder.bind(paramJson, CodeManagerParamDto.class);
		JSONObject jsonobj = new JSONObject();
		try {
			codeService.apply(p);
			jsonobj.put("result", "OK");
			jsonobj.put("msg", "정상적으로 처리되었습니다.");
			jsonobj.put("genKey", 0);
		} catch (Exception e) {
			jsonobj.put("result", "NOTOK");
			jsonobj.put("msg", e.getMessage());
			jsonobj.put("genKey", 0);
			throw e;
		}
		return ResponseEntity.ok().contentType(new MediaType("text", "plain")).body(jsonobj.toString());
	}
}

