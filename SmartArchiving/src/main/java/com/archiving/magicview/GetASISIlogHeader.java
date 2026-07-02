package com.archiving.magicview;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.magicview.dao.sqream.MagicViewSqreamMapper;
import com.archiving.magicview.dto.AsisIlogHeaderQuery;
import com.archiving.magicview.support.MagicViewRowMaps;
import com.archiving.util.UtilClass;

@RestController
public class GetASISIlogHeader {

	public Logger logger = Logger.getLogger(this.getClass().getName() + ".class");

	private static final MediaType TEXT_PLAIN_UTF8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);

	@Autowired
	private MagicViewSqreamMapper sqreamMapper;

	/* ASIS ILOG 헤더 조회 API(Sqream 전용 매퍼, 구 서블릿과 동일 응답 형식) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetASISIlogHeader", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> getASISIlogHeader(@RequestParam(value = "param", required = false) String param,
			HttpServletRequest request) {

		JSONObject jsonobj = new JSONObject();
		UtilClass utilClass = new UtilClass();

		try {

			logger.debug("MagicView ***** Start GetASISIlogHeader *****");

			int nCount = 0;

			String sF_SP_TR_YMD_S = "";
			String sF_SP_TR_YMD_E = "";
			String sF_SP_CHANNEL_CODE = "";
			String sF_SP_USER_ID = "";
			String sF_SP_TR_ACNO = "";
			String sF_SP_CUS_NO = "";
			String sF_SP_IN_ACNO = "";
			String sF_IP = "";
			String sServerId = "";
			String sRows = "";
			String sPage = "";
			String sCorpValue = "";
			String sServerValue = "";

			String Obj = param;

			logger.debug("GetASISIlogHeader jsonParam:" + Obj);

			if (Obj != null) {

				logger.debug("GetASISIlogHeader DEBUG");
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(Obj.toString());

				logger.debug("GetASISIlogHeader json:" + json);

				sF_SP_TR_YMD_S = utilClass.nvl_trim((String) json.get("__F_SP_TR_YMD_S"));
				sF_SP_TR_YMD_E = utilClass.nvl_trim((String) json.get("__F_SP_TR_YMD_E"));
				sF_SP_CHANNEL_CODE = utilClass.nvl_trim((String) json.get("__F_SP_CHANNEL_CODE"));
				sF_SP_USER_ID = utilClass.nvl_trim((String) json.get("__F_SP_USER_ID"));
				sF_SP_TR_ACNO = utilClass.nvl_trim((String) json.get("__F_SP_TR_ACNO"));
				sF_SP_CUS_NO = utilClass.nvl_trim((String) json.get("__F_SP_CUS_NO"));
				sF_SP_IN_ACNO = utilClass.nvl_trim((String) json.get("__F_SP_IN_ACNO"));
				sF_IP = utilClass.nvl_trim((String) json.get("__F_IP"));
				sServerId = (String) json.get("__server_id");
				sRows = (String) json.get("__rows");
				sPage = (String) json.get("__page");
				sCorpValue = (String) json.get("__corpValue");
				if (sCorpValue == null || sCorpValue.isEmpty()) {
					sCorpValue = "efblview";
				}
				sServerValue = (String) json.get("__serverValue");
				if (sServerValue == null || sServerValue.isEmpty()) {
					sServerValue = "bl";
				}

				logger.debug("GetASISIlogHeader sServerId:" + sServerId);
				logger.debug("GetASISIlogHeader nRows:" + sRows);
				logger.debug("GetASISIlogHeader nPage:" + sPage);
				logger.debug("GetASISIlogHeader sCorpValue:" + sCorpValue + " sServerValue:" + sServerValue);
			}

			AsisIlogHeaderQuery q = new AsisIlogHeaderQuery();
			q.setYmdStart(sF_SP_TR_YMD_S);
			q.setYmdEnd(sF_SP_TR_YMD_E);
			q.setChannelCode(sF_SP_CHANNEL_CODE);
			q.setUserId(sF_SP_USER_ID);
			q.setTrAcno(sF_SP_TR_ACNO);
			q.setCusNo(sF_SP_CUS_NO);
			q.setInAcno(sF_SP_IN_ACNO);
			q.setIp(sF_IP);

			JSONArray seriesArray = new JSONArray();

			int cnt = sqreamMapper.countAsisIlogHeader(q);
			if (cnt > 500000) {
				throw new Exception("데이터가 500000건이 넘습니다. \n 조회조건을 바꿔주세요.");
			}

			List<Map<String, Object>> rows = sqreamMapper.selectAsisIlogHeader(q);
			for (Map<String, Object> row : rows) {
				JSONObject datas = new JSONObject();

				datas.put("MESSAGE_SER_NO", MagicViewRowMaps.getStr(row, "MESSAGE_SER_NO"));
				datas.put("TRX_DTIME", MagicViewRowMaps.getStr(row, "TRX_DTIME"));
				datas.put("TRX_TRACKING_NO", MagicViewRowMaps.getStr(row, "TRX_TRACKING_NO"));
				datas.put("USER_ID", MagicViewRowMaps.getStr(row, "USER_ID"));
				datas.put("MESSAGE_ID", MagicViewRowMaps.getStr(row, "MESSAGE_ID"));
				datas.put("MESSAGE_NAME", MagicViewRowMaps.getStr(row, "MESSAGE_NAME"));
				datas.put("ERROR_CODE", MagicViewRowMaps.getStr(row, "ERROR_CODE"));
				datas.put("TR_ACNO", MagicViewRowMaps.getStr(row, "TR_ACNO"));
				datas.put("IN_ACNO", MagicViewRowMaps.getStr(row, "IN_ACNO"));
				datas.put("IP", MagicViewRowMaps.getStr(row, "IP"));
				datas.put("CUS_NO", MagicViewRowMaps.getStr(row, "CUS_NO"));
				datas.put("CHANNEL_CODE", MagicViewRowMaps.getStr(row, "CHANNEL_CODE"));
				datas.put("SERVICE_ID", MagicViewRowMaps.getStr(row, "SERVICE_ID"));

				seriesArray.add(datas);

				nCount++;
			}

			if (nCount > 0) {
				jsonobj.put("rows", seriesArray);
				int total = nCount / Integer.parseInt(sRows);

				jsonobj.put("records", nCount);
				jsonobj.put("page", Integer.parseInt(sPage));
				jsonobj.put("total", total);
				jsonobj.put("result", "OK");
			} else {
				jsonobj.put("result", "NOTFOUND");
			}

		} catch (Exception e) {
			e.printStackTrace();
			jsonobj.put("result", e.getMessage());
		}

		return ResponseEntity.ok().contentType(TEXT_PLAIN_UTF8).body(jsonobj.toString());
	}

}
