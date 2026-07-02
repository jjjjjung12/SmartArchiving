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
import com.archiving.magicview.support.MagicViewRowMaps;

@RestController
public class AsisSameTranIdDataController {
	public Logger logger = Logger.getLogger(this.getClass().getName() + ".class");

	@Autowired
	private MagicViewSqreamMapper sqreamMapper;

	/* ASIS 동일 거래ID 상세 데이터 조회 API(Sqream 전용 매퍼) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetASISSameTranIDData", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getASISSameTranIdData(@RequestParam(value = "param", required = false) String param) {
		try {
			String sF_MESSAGE_SER_NO = "";
			String sRows = "50";
			String sPage = "1";

			if (param != null) {
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(param);
				sF_MESSAGE_SER_NO = (String) json.get("__F_MESSAGE_SER_NO");
				sRows = (String) json.get("__rows");
				sPage = (String) json.get("__page");
			}

			JSONObject jsonobj = new JSONObject();
			JSONArray seriesArray = new JSONArray();

			List<Map<String, Object>> rows = sqreamMapper.selectAsisSameTranIdDetail(sF_MESSAGE_SER_NO);

			int nCount = 0;
			for (Map<String, Object> row : rows) {
				JSONObject datas = new JSONObject();
				datas.put("P_MESSAGE_SER_NO", MagicViewRowMaps.getStr(row, "MESSAGE_SER_NO"));
				datas.put("P_TRX_TRACKING_NO", MagicViewRowMaps.getStr(row, "TRX_TRACKING_NO"));
				datas.put("P_MESSAGE_ID", MagicViewRowMaps.getStr(row, "MESSAGE_ID"));
				datas.put("P_MESSAGE_NAME", MagicViewRowMaps.getStr(row, "MESSAGE_NAME"));
				datas.put("P_CUST_REG_NO", MagicViewRowMaps.getStr(row, "CUST_REG_NO"));
				datas.put("P_USER_ID", MagicViewRowMaps.getStr(row, "USER_ID"));
				datas.put("P_TRX_DTIME", MagicViewRowMaps.getStr(row, "TRX_DTIME"));
				datas.put("P_TR_ACNO", MagicViewRowMaps.getStr(row, "TR_ACNO"));
				datas.put("P_IN_ACNO", MagicViewRowMaps.getStr(row, "IN_ACNO"));
				datas.put("P_SRCH_ACNO", MagicViewRowMaps.getStr(row, "SRCH_ACNO"));
				datas.put("P_IP", MagicViewRowMaps.getStr(row, "IP"));
				datas.put("P_ERROR_CODE", MagicViewRowMaps.getStr(row, "ERROR_CODE"));
				datas.put("P_CHANNEL_CODE", MagicViewRowMaps.getStr(row, "CHANNEL_CODE"));
				datas.put("P_MESSAGE_HEADER_DATA", MagicViewRowMaps.getStr(row, "MESSAGE_HEADER_DATA"));
				datas.put("P_MESSAGE_BODY_DATA", MagicViewRowMaps.getStr(row, "MESSAGE_BODY_DATA"));
				datas.put("P_MESSAGE_BODY_DATA2", MagicViewRowMaps.getStr(row, "MESSAGE_BODY_DATA2"));
				datas.put("P_MESSAGE_BODY_DATA3", MagicViewRowMaps.getStr(row, "MESSAGE_BODY_DATA3"));
				datas.put("P_MESSAGE_BODY_DATA4", MagicViewRowMaps.getStr(row, "MESSAGE_BODY_DATA4"));
				datas.put("P_MESSAGE_BODY_DATA5", MagicViewRowMaps.getStr(row, "MESSAGE_BODY_DATA5"));
				datas.put("P_LOG_LEVEL", MagicViewRowMaps.getStr(row, "LOG_LEVEL"));
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
