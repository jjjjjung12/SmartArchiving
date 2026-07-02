package com.archiving.magicview.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.magicview.dao.sqream.MagicViewSqreamMapper;
import com.archiving.magicview.dto.AsisSlogHeaderQuery;
import com.archiving.magicview.support.MagicViewRowMaps;

@RestController
public class AsisSlogHeaderController {
	public Logger logger = Logger.getLogger(this.getClass().getName() + ".class");

	@Autowired
	private MagicViewSqreamMapper sqreamMapper;

	/* ASIS SLOG 헤더 통계 조회 API(Sqream 전용 매퍼) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetASISSlogHeader", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getASISSlogHeader(@RequestParam(value = "param", required = false) String param) {
		try {
			String sF_SP_TR_YMD_S = "";
			String sF_SP_TR_YMD_E = "";
			String sF_SP_MED_DS = "";
			String sF_SP_SVC_ID = "";
			String sF_SP_OS = "";
			String sF_SP_LOCALE = "";
			String sRows = "50";
			String sPage = "1";
			String sServerValue = "bl";

			if (param != null) {
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(param);
				sF_SP_TR_YMD_S = (String) json.get("__F_SP_TR_YMD_S");
				sF_SP_TR_YMD_E = (String) json.get("__F_SP_TR_YMD_E");
				sF_SP_MED_DS = (String) json.get("__F_SP_MED_DS");
				sF_SP_SVC_ID = (String) json.get("__F_SP_SVC_ID");
				sF_SP_OS = (String) json.get("__F_SP_OS");
				sF_SP_LOCALE = (String) json.get("__F_SP_LOCALE");
				sRows = (String) json.get("__rows");
				sPage = (String) json.get("__page");
				String sv = (String) json.get("__serverValue");
				if (sv != null && !sv.isEmpty()) {
					sServerValue = sv;
				}
			}

			AsisSlogHeaderQuery q = new AsisSlogHeaderQuery();
			q.setServerValue(sServerValue);
			q.setTrYmdStart(sF_SP_TR_YMD_S);
			q.setTrYmdEnd(sF_SP_TR_YMD_E);
			q.setMedDs(sF_SP_MED_DS);
			q.setSvcId(sF_SP_SVC_ID);
			q.setOs(sF_SP_OS);
			q.setLocale(sF_SP_LOCALE);

			JSONObject jsonobj = new JSONObject();
			JSONArray seriesArray = new JSONArray();

			List<Map<String, Object>> rows = sqreamMapper.selectAsisSlogHeader(q);

			int nCount = 0;
			for (Map<String, Object> row : rows) {
				JSONObject datas = new JSONObject();
				datas.put("SVC_ID", MagicViewRowMaps.getStr(row, "SVC_ID"));
				datas.put("SVC_NM", MagicViewRowMaps.getStr(row, "SVC_NM"));
				datas.put("TR_CN", MagicViewRowMaps.getStr(row, "TR_CN"));
				datas.put("NML_PRC_CN", MagicViewRowMaps.getStr(row, "NML_PRC_CN"));
				datas.put("ERR_PRC_CN", MagicViewRowMaps.getStr(row, "ERR_PRC_CN"));
				datas.put("PARTNER_CN", MagicViewRowMaps.getStr(row, "PARTNER_CN"));
				datas.put("OS1", MagicViewRowMaps.getStr(row, "OS1"));
				datas.put("OS2", MagicViewRowMaps.getStr(row, "OS2"));
				datas.put("OS3", MagicViewRowMaps.getStr(row, "OS3"));
				datas.put("OS4", MagicViewRowMaps.getStr(row, "OS4"));
				datas.put("OS5", MagicViewRowMaps.getStr(row, "OS5"));
				datas.put("OS6", MagicViewRowMaps.getStr(row, "OS6"));
				seriesArray.add(datas);
				nCount++;
			}

			jsonobj.put("rows", seriesArray);
			if (nCount > 0) {
				int total = nCount / safeInt(sRows, nCount);
				jsonobj.put("records", nCount);
				jsonobj.put("page", safeInt(sPage, 1));
				jsonobj.put("total", total);
				jsonobj.put("result", "OK");
			} else {
				jsonobj.put("result", "NOTFOUND");
			}
			return jsonobj;
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject err = new JSONObject();
			err.put("result", "ERR");
			err.put("message", e.getMessage());
			return err;
		}
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
