package com.archiving.magicview.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.magicview.dao.sqream.MagicViewSqreamMapper;
import com.archiving.magicview.dto.SlogHeaderQuery;
import com.archiving.magicview.support.MagicViewRowMaps;
import com.archiving.util.CodeClass;

@RestController
public class SlogHeaderController {
	public Logger logger = Logger.getLogger(this.getClass().getName() + ".class");
	@Autowired
	private MagicViewSqreamMapper sqreamMapper;

	/* SLOG 헤더 집계 조회 API(서버/기간/서비스 조건) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetSlogHeader", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getSlogHeader(@RequestParam(value = "param", required = false) String param) {
		JSONObject jsonobj = new JSONObject();

		try {
			logger.debug("Slog ***** Start GetSlog *****");

			String sF_SP_TR_YMD_S = "";
			String sF_SP_TR_YMD_E = "";
			String sF_SP_SERVICE_ID = "";
			String sF_SP_SERVICE_NM = "";
			String sF_MEDIA_CATEGORY_CD = "";

			String sRows = "50";
			String sPage = "1";
			String sServerValue = "bl"; // bl(은행), sl(상호)

			if (param != null) {
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(param);
				sF_SP_TR_YMD_S = (String) json.get("__F_SP_TR_YMD_S");
				sF_SP_TR_YMD_E = (String) json.get("__F_SP_TR_YMD_E");
				sF_SP_SERVICE_ID = (String) json.get("__F_SP_SERVICE_ID");
				sF_SP_SERVICE_NM = (String) json.get("__F_SP_SERVICE_NM");
				sF_MEDIA_CATEGORY_CD = (String) json.get("__F_MEDIA_CATEGORY_CD");
				sRows = (String) json.get("__rows");
				sPage = (String) json.get("__page");
				String sv = (String) json.get("__serverValue");
				if (sv != null && !sv.isEmpty()) {
					sServerValue = sv;
				}
			}

			JSONObject out = new JSONObject();
			JSONArray seriesArray = new JSONArray();
			CodeClass codeClass = new CodeClass();
			SlogHeaderQuery q = new SlogHeaderQuery();
			q.setServerValue(sServerValue);
			q.setTrYmdStart(sF_SP_TR_YMD_S);
			q.setTrYmdEnd(sF_SP_TR_YMD_E);
			q.setServiceId(sF_SP_SERVICE_ID);
			q.setServiceName(sF_SP_SERVICE_NM);
			q.setMediaCategoryCd(sF_MEDIA_CATEGORY_CD);
			List<Map<String, Object>> rows = sqreamMapper.selectSlogHeader(q);

			int nCount = 0;
			for (Map<String, Object> row : rows) {
				JSONObject datas = new JSONObject();
				datas.put("SERVICE_ID", MagicViewRowMaps.getStr(row, "SERVICE_ID"));
				datas.put("SERVICE_NAME", MagicViewRowMaps.getStr(row, "SERVICE_NAME"));
				datas.put("APPLICATION_GROUP_ID", MagicViewRowMaps.getStr(row, "APPLICATION_GROUP_ID"));
				String appGrp = MagicViewRowMaps.getStr(row, "APPLICATION_GROUP_ID");
				datas.put("APPLICATION_GROUP_NM",
						codeClass.codeSet("MEDIA_CATEGORY_CD", appGrp) + "(" + appGrp + ")");
				datas.put("SUCCESS_YN", MagicViewRowMaps.getStr(row, "SUCCESS_YN"));
				datas.put("SUCCESS_COUNT", MagicViewRowMaps.getStr(row, "SUCCESS_COUNT"));
				datas.put("FAIL_COUNT", MagicViewRowMaps.getStr(row, "FAIL_COUNT"));
				datas.put("OS1", MagicViewRowMaps.getStr(row, "OS1"));
				datas.put("OS2", MagicViewRowMaps.getStr(row, "OS2"));
				datas.put("OS3", MagicViewRowMaps.getStr(row, "OS3"));
				datas.put("OS4", MagicViewRowMaps.getStr(row, "OS4"));
				datas.put("OS5", MagicViewRowMaps.getStr(row, "OS5"));
				datas.put("OS6", MagicViewRowMaps.getStr(row, "OS6"));
				seriesArray.add(datas);
				nCount++;
			}

			out.put("rows", seriesArray);
			if (nCount > 0) {
				int total = nCount / safeInt(sRows, nCount);
				out.put("records", nCount);
				out.put("page", safeInt(sPage, 1));
				out.put("total", total);
				out.put("result", "OK");
			} else {
				out.put("result", "NOTFOUND");
			}
			return out;
		} catch (Exception e) {
			e.printStackTrace();
			jsonobj.put("result", "ERR");
			jsonobj.put("message", e.getMessage());
			return jsonobj;
		} finally { }
	}

	/* 문자열을 안전하게 int로 변환(기본값 제공) */
	private static int safeInt(String v, int def) {
		try {
			if (v == null || v.trim().isEmpty()) {
				return def;
			}
			return Integer.parseInt(v.trim());
		} catch (Exception e) {
			return def;
		}
	}
}
