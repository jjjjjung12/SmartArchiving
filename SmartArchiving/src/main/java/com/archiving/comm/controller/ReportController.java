package com.archiving.comm.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.comm.dto.ReportAttrRowDto;
import com.archiving.comm.dto.ReportInfoDto;
import com.archiving.comm.dto.ReportListParamDto;
import com.archiving.comm.dto.ReportSaveParamDto;
import com.archiving.comm.service.ReportService;
import com.archiving.mvc.util.JsonParamBinder;

@RestController
public class ReportController {
	private final ReportService reportService;
	private final JsonParamBinder jsonParamBinder;

	public ReportController(ReportService reportService, JsonParamBinder jsonParamBinder) {
		this.reportService = reportService;
		this.jsonParamBinder = jsonParamBinder;
	}

	/* 리포트/속성 목록 조회 API(__gb=A/T:리포트, 그 외:속성) */
	@SuppressWarnings("unchecked")
	@PostMapping("/GetReportList")
	public String getReportList(@RequestParam(value = "param", required = false) String param) throws Exception {
		ReportListParamDto p = jsonParamBinder.bind(param, ReportListParamDto.class);
		if (p == null) p = new ReportListParamDto();

		String gb = StringUtils.defaultString(p.get__gb());
		String rowsStr = StringUtils.defaultString(p.get__rows(), "10");
		String pageStr = StringUtils.defaultString(p.get__page(), "1");

		JSONObject json = new JSONObject();
		json.put("result", "ERROR");

		JSONArray arr = new JSONArray();
		int nCount = 0;

		if ("A".equals(gb) || "T".equals(gb)) {
			List<ReportInfoDto> list = reportService.listInfo(p);
			for (ReportInfoDto r : list) {
				JSONObject d = new JSONObject();
				d.put("REPORT_ID", r.getReportId());
				d.put("REPORT_CD", r.getReportCd());
				d.put("REPORT_NM", r.getReportNm());
				d.put("REPORT_TYPE", r.getReportType());
				d.put("DETAIL_PAGE", r.getDetailPage());
				d.put("DESCRIPTION", StringUtils.defaultIfBlank(r.getDescription(), " "));
				arr.add(d);
				nCount++;
			}
		} else {
			List<ReportAttrRowDto> list = reportService.listTableAttrs(p);
			for (ReportAttrRowDto r : list) {
				JSONObject d = new JSONObject();
				d.put("ATTR_ID", r.getAttrId());
				d.put("REPORT_ID", r.getReportId());
				d.put("TABLE_ID", r.getTableId());
				d.put("ATTR_ORDER", r.getAttrOrder());
				d.put("ATTR_CD", r.getAttrCd());
				d.put("ATTR_NM", r.getAttrNm());
				d.put("ATTR_TYPE_CD", r.getAttrTypeCd());
				d.put("ATTR_SIZE", r.getAttrSize());
				d.put("DECIMAL_SIZE", r.getDecimalSize());
				d.put("MDFY_INDEX", r.getMdfyIndex());
				d.put("ATTR_NULL_YN", r.getAttrNullYn());
				d.put("USE_YN", r.getUseYn());
				d.put("WHERE_YN", r.getWhereYn());
				d.put("OUTPUT_PATH", r.getOutputPath());
				d.put("DATE_TYPE_YN", r.getDateTypeYn());
				d.put("TIME_TYPE_YN", r.getTimeTypeYn());
				d.put("DATETIME_YN", r.getDatetimeYn());
				d.put("JOIN_YN", r.getJoinYn());
				d.put("JOIN_ALIASS", r.getJoinAliass());
				d.put("GROUP_BY_YN", r.getGroupByYn());
				d.put("ORDER_BY_YN", r.getOrderByYn());
				d.put("PREFIX", r.getPrefix());
				arr.add(d);
				nCount++;
			}
		}

		json.put("rows", arr);

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

	/* (레거시 호환) 리포트 목록 조회 API(GetReportList로 위임) */
	@PostMapping("/GetReportsList")
	public String getReportsList(@RequestParam(value = "param", required = false) String param) throws Exception {
		return getReportList(param);
	}

	/* 리포트/속성 저장 API(crud에 따라 C/U/D 및 속성 처리) */
	@SuppressWarnings("unchecked")
	@PostMapping("/SetReport")
	public String setReport(@RequestParam(value = "param", required = false) String param) throws Exception {
		ReportSaveParamDto p = jsonParamBinder.bind(param, ReportSaveParamDto.class);
		if (p == null) p = new ReportSaveParamDto();

		int genKey = reportService.save(p);

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

