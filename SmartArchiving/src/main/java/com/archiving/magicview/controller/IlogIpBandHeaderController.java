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
import com.archiving.magicview.dto.IlogIpBandQuery;
import com.archiving.magicview.support.MagicViewRowMaps;
import com.archiving.util.CodeClass;
import com.archiving.util.UtilClass;

@RestController
public class IlogIpBandHeaderController {
	public Logger logger = Logger.getLogger(this.getClass().getName() + ".class");
	@Autowired
	private MagicViewSqreamMapper sqreamMapper;

	/* ILOG IP 대역별 헤더 조회 API(기간/IP 대역 조건) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetIlogIpBandHeader", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getIlogIpBandHeader(@RequestParam(value = "param", required = false) String param) {
		JSONObject jsonobj = new JSONObject();
		UtilClass utilClass = new UtilClass();
		CodeClass codeClass = new CodeClass();

		try {
			String sF_SP_TR_YMD_S = "";
			String sF_SP_TR_YMD_E = "";
			String sF_SP_IP_START = "";
			String sF_SP_IP_END = "";

			String sRows = "50";
			String sPage = "1";
			String sCorpValue = "efblview";
			String sServerValue = "bl";

			if (param != null) {
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(param);
				sF_SP_TR_YMD_S = utilClass.nvl_trim((String) json.get("__F_SP_TR_YMD_S"));
				sF_SP_TR_YMD_E = utilClass.nvl_trim((String) json.get("__F_SP_TR_YMD_E"));
				sF_SP_IP_START = utilClass.nvl_trim((String) json.get("__F_SP_IP_START"));
				sF_SP_IP_END = utilClass.nvl_trim((String) json.get("__F_SP_IP_END"));
				sRows = (String) json.get("__rows");
				sPage = (String) json.get("__page");
				String cv = (String) json.get("__corpValue");
				if (cv != null && !cv.isEmpty()) {
					sCorpValue = cv;
				}
				String sv = (String) json.get("__serverValue");
				if (sv != null && !sv.isEmpty()) {
					sServerValue = sv;
				}
			}

			logger.debug("GetIlogIpBandHeader corpValue=" + sCorpValue + ", serverValue=" + sServerValue);

			IlogIpBandQuery q = new IlogIpBandQuery();
			q.setServerValue(sServerValue);
			q.setTrYmdStart(sF_SP_TR_YMD_S);
			q.setTrYmdEnd(sF_SP_TR_YMD_E);
			q.setIpStart(sF_SP_IP_START);
			q.setIpEnd(sF_SP_IP_END);
			List<Map<String, Object>> rows = sqreamMapper.selectIlogIpBandHeader(q);

			JSONArray seriesArray = new JSONArray();
			int nCount = 0;
			for (Map<String, Object> row : rows) {
				JSONObject datas = new JSONObject();
				if (nCount > 500000) {
					jsonobj.put("result", "over");
					break;
				}
				datas.put("CORP", MagicViewRowMaps.getStr(row, "CORP"));
				datas.put("MESSAGE_SER_NO", MagicViewRowMaps.getStr(row, "MESSAGE_SER_NO"));
				datas.put("TRX_DTIME", MagicViewRowMaps.getStr(row, "TRX_DTIME"));
				datas.put("REQ_RES_TYPE_NM",
						codeClass.codeSet("REQ_RES_TYPE_CD", MagicViewRowMaps.getStr(row, "REQ_RES_TYPE")));
				datas.put("REQ_RES_TYPE", MagicViewRowMaps.getStr(row, "REQ_RES_TYPE"));
				datas.put("TRX_TRACKING_NO", MagicViewRowMaps.getStr(row, "TRX_TRACKING_NO"));
				datas.put("USER_ID", MagicViewRowMaps.getStr(row, "USER_ID"));
				datas.put("REG_CUST_NO", MagicViewRowMaps.getStr(row, "CUST_REG_NO"));
				datas.put("MESSAGE_ID", MagicViewRowMaps.getStr(row, "MESSAGE_ID"));
				datas.put("MESSAGE_NAME", MagicViewRowMaps.getStr(row, "COM_IF_NM"));
				datas.put("ERROR_CODE", MagicViewRowMaps.getStr(row, "ERROR_CODE"));
				datas.put("LOG_CR_INFO", MagicViewRowMaps.getStr(row, "LOG_CR_INFO"));
				String logCr = MagicViewRowMaps.getStr(row, "LOG_CR_INFO");
				datas.put("LOG_CR_INFO_NM", codeClass.codeSet("MEDIA_CATEGORY_CD", logCr) + "(" + logCr + ")");
				datas.put("OS", MagicViewRowMaps.getStr(row, "OS"));
				datas.put("IP", MagicViewRowMaps.getStr(row, "IP"));
				datas.put("CORP_NM", codeClass.codeSet("CORP_TYPE_CD", MagicViewRowMaps.getStr(row, "CORP")));
				datas.put("SCRN_ID", MagicViewRowMaps.getStr(row, "SCRN_ID"));
				datas.put("MAC", MagicViewRowMaps.getStr(row, "MAC"));
				datas.put("PROFILE", MagicViewRowMaps.getStr(row, "PROFILE"));
				datas.put("TR_ACNO", MagicViewRowMaps.getStr(row, "TR_ACNO"));
				datas.put("IN_ACNO", MagicViewRowMaps.getStr(row, "IN_ACNO"));
				datas.put("TEL_NO", MagicViewRowMaps.getStr(row, "TEL_NO"));

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
				if ("over".equals(jsonobj.get("result"))) {
					jsonobj.put("rows", "");
					jsonobj.put("result", "데이터가 500000건이 넘습니다. \n 조회조건을 바꿔주세요.");
				} else {
					jsonobj.put("result", "NOTFOUND");
				}
			}
			return jsonobj;
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
