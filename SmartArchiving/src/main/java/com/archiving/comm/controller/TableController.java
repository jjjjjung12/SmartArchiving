package com.archiving.comm.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.comm.dto.TableAttrDto;
import com.archiving.comm.dto.TableInfoDto;
import com.archiving.comm.dto.TableListParamDto;
import com.archiving.comm.dto.TableSaveParamDto;
import com.archiving.comm.service.TableService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class TableController {
	private final TableService tableService;
	private final JsonParamBinder jsonParamBinder;

	public TableController(TableService tableService, JsonParamBinder jsonParamBinder) {
		this.tableService = tableService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 테이블/컬럼 목록 조회 API(__gb=A/T:테이블, 그 외:컬럼) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetTableList")
	public String getTableList(@RequestParam(value = "param", required = false) String param) throws Exception {
		TableListParamDto p = jsonParamBinder.bind(param, TableListParamDto.class);
		if (p == null) p = new TableListParamDto();

		String gb = StringUtils.defaultString(p.get__gb());
		String rowsStr = StringUtils.defaultString(p.get__rows(), "10");
		String pageStr = StringUtils.defaultString(p.get__page(), "1");

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");

		JSONArray arr = new JSONArray();
		int nCount = 0;

		if ("A".equals(gb) || "T".equals(gb)) {
			List<TableInfoDto> list = tableService.getTableInfoList(p);
			for (TableInfoDto r : list) {
				JSONObject d = new JSONObject();
				d.put("TABLE_ID", r.getTableId());
				d.put("SERVER_ID", r.getServerId());
				d.put("TABLE_CD", r.getTableCd());
				d.put("TABLE_NM", r.getTableNm());
				d.put("USE_YN", r.getUseYn());
				d.put("TABLE_JOIN_NM", r.getTableJoinNm());
				d.put("SAVE_PREQ_CD", StringUtils.defaultIfBlank(r.getSavePreqCd(), " "));
				d.put("SAVE_PREQ_NM", StringUtils.defaultIfBlank(r.getSavePreqNm(), " "));
				d.put("SAVE_PREQ", StringUtils.defaultIfBlank(r.getSavePreq(), " "));
				d.put("EXP_PREQ_CD", StringUtils.defaultIfBlank(r.getExpPreqCd(), " "));
				d.put("EXP_PREQ_NM", StringUtils.defaultIfBlank(r.getExpPreqNm(), " "));
				d.put("EXP_PREQ", StringUtils.defaultIfBlank(r.getExpPreq(), " "));
				d.put("DESCRIPTION", StringUtils.defaultIfBlank(r.getDescription(), " "));
				arr.add(d);
				nCount++;
			}
			json.put("rows", arr);
		} else {
			List<TableAttrDto> list = tableService.getTableAttrs(p);
			for (TableAttrDto r : list) {
				if (r == null) continue;
				JSONObject d = new JSONObject();
				d.put("ATTR_CD", r.getAttrCd());
				d.put("ATTR_NM", r.getAttrNm());
				d.put("ATTR_TYPE_CD", r.getAttrTypeCd());
				d.put("ATTR_SIZE", r.getAttrSize());
				d.put("DECIMAL_SIZE", r.getDecimalSize());
				d.put("ATTR_NULL_YN", r.getAttrNullYn());
				d.put("ATTR_USE_YN", r.getAttrUseYn());
				d.put("ATTR_ORDER", r.getAttrOrder());
				d.put("WHERE_INDEX", r.getWhereIndex());
				d.put("OUTPUT_INDEX", r.getOutputIndex());
				d.put("DATE_TYPE_YN", r.getDateTypeYn());
				d.put("TIME_TYPE_YN", r.getTimeTypeYn());
				d.put("ATTR_TYPE_NM", StringUtils.defaultIfBlank(r.getAttrTypeNm(), " "));
				arr.add(d);
				nCount++;
			}
			json.put("rows", arr);
		}

		if (nCount > 0) {
			int rows = safeInt(rowsStr, 10);
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

	/* 테이블 선택 팝업용 목록 조회 API */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetTableListPop")
	public String getTableListPop(@RequestParam(value = "param", required = false) String param) throws Exception {
		TableListParamDto p = jsonParamBinder.bind(param, TableListParamDto.class);
		if (p == null) p = new TableListParamDto();

		String rowsStr = StringUtils.defaultString(p.get__rows(), "10");
		String pageStr = StringUtils.defaultString(p.get__page(), "1");

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");

		JSONArray arr = new JSONArray();
		int nCount = 0;
		List<TableInfoDto> list = tableService.getTableInfoPop(p);
		for (TableInfoDto r : list) {
			JSONObject d = new JSONObject();
			d.put("TABLE_ID", r.getTableId());
			d.put("TABLE_CD", r.getTableCd());
			d.put("TABLE_NM", r.getTableNm());
			arr.add(d);
			nCount++;
		}
		json.put("data", arr);

		if (nCount > 0) {
			int rows = safeInt(rowsStr, 10);
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

	/* 테이블/컬럼 저장 API(crud에 따라 C/U/D 및 컬럼 처리) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetTable")
	public String setTable(@RequestParam(value = "param", required = false) String param) throws Exception {
		TableSaveParamDto p = jsonParamBinder.bind(param, TableSaveParamDto.class);
		if (p == null) p = new TableSaveParamDto();

		int genKey = tableService.save(p);

		JSONObject json = new JSONObject();
		json.put("result", "OK");
		json.put("genKey", genKey);
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

