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
import com.archiving.magicview.dto.ServiceReportQuery;
import com.archiving.magicview.support.MagicViewRowMaps;
import com.archiving.util.CodeClass;

@RestController
public class ServiceReportController {
	public Logger logger = Logger.getLogger(this.getClass().getName() + ".class");
	@Autowired
	private MagicViewSqreamMapper sqreamMapper;

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetServiceReport", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getServiceReport(@RequestParam(value = "param", required = false) String param) {
		JSONObject jsonobj = new JSONObject();

		try {
			logger.debug("GetServiceReport ***** Start GetServiceReport *****");

			String mediaCategoryCd = "";
			String trYmdStart = "";
			String trYmdEnd = "";
			String selGubun = "";
			String delYn = "";
			String serverValue = "bl";

			if (param != null) {
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(param);
				mediaCategoryCd = (String) json.get("F_MEDIA_CATEGORY_CD");
				trYmdStart = (String) json.get("F_SP_START_TR_YMD");
				trYmdEnd = (String) json.get("F_SP_END_TR_YMD");
				selGubun = (String) json.get("F_SEL_GUBUN");
				delYn = (String) json.get("F_DEL_YN");
				String sv = (String) json.get("__serverValue");
				if (sv != null && !sv.isEmpty()) {
					serverValue = sv;
				}
			}

			ServiceReportQuery q = new ServiceReportQuery();
			q.setServerValue(serverValue);
			q.setMediaCategoryCd(mediaCategoryCd);
			q.setTrYmdStart(trYmdStart);
			q.setTrYmdEnd(trYmdEnd);
			q.setSelGubun(selGubun);
			q.setDelYn(delYn);

			CodeClass codeClass = new CodeClass();
			List<Map<String, Object>> rows = sqreamMapper.selectServiceReport(q);
			JSONArray seriesArray = new JSONArray();
			int nCount = 0;

			for (Map<String, Object> row : rows) {
				JSONObject data = new JSONObject();
				String appGrp = MagicViewRowMaps.getStr(row, "APPLICATION_GROUP_ID");
				data.put("APPLICATION_GROUP_ID", codeClass.codeSet("MEDIA_CATEGORY_CD", appGrp));
				data.put("SEL_GUBUN", codeClass.codeSet("ILOG_SEL_GUBUN", MagicViewRowMaps.getStr(row, "SEL_GUBUN")));
				data.put("DEL_YN", MagicViewRowMaps.getStr(row, "DEL_YN"));
				data.put("TOTAL_SUCCESS_COUNT", MagicViewRowMaps.getStr(row, "TOTAL_SUCCESS_COUNT"));
				data.put("TOTAL_FAIL_COUNT", MagicViewRowMaps.getStr(row, "TOTAL_FAIL_COUNT"));
				seriesArray.add(data);
				nCount++;
			}

			jsonobj.put("rows", seriesArray);
			if (nCount > 0) {
				jsonobj.put("records", nCount);
				jsonobj.put("total", nCount);
				jsonobj.put("result", "OK");
			} else {
				jsonobj.put("result", "NOTFOUND");
			}
			return jsonobj;
		} catch (Exception e) {
			e.printStackTrace();
			jsonobj.put("result", "ERR");
			jsonobj.put("message", e.getMessage());
			return jsonobj;
		}
	}
}
