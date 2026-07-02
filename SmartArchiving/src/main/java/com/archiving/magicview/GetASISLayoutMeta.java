package com.archiving.magicview;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.archiving.util.UtilClass;

@RestController
public class GetASISLayoutMeta {

	public Logger logger = Logger.getLogger(this.getClass().getName() + ".class");

	@Autowired
	private MagicViewSqreamMapper sqreamMapper;

	/* ASIS 레이아웃 메타 조회 API(메시지 헤더 데이터 파싱 포함, Sqream 전용 매퍼) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetASISLayoutMeta", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getASISLayoutMeta(@RequestParam(value = "param", required = false) String param,
			HttpServletRequest request) {

		try {

			logger.debug("MagicView ***** Start GetASISLayoutMeta *****");
			UtilClass utilClass = new UtilClass();

			int nCount = 0;

			String sF_MESSAGE_ID = "";
			String sRows = "";
			String sPage = "";
			String sF_MESSAGE_HEADER_DATA = "";
			String sF_LOG_LEVEL = "";

			String Obj = param;

			logger.debug("GetASISLayoutMeta jsonParam:" + Obj);

			if (Obj != null) {

				logger.debug("GetASISLayoutMeta DEBUG");
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(Obj.toString());
				logger.debug("GetASISLayoutMeta json:" + json);

				sF_MESSAGE_ID = (String) json.get("__F_MESSAGE_ID");
				sF_MESSAGE_HEADER_DATA = (String) json.get("__F_MESSAGE_HEADER_DATA");
				sF_LOG_LEVEL = (String) json.get("__F_LOG_LEVEL");
				sRows = (String) json.get("__rows");
				sPage = (String) json.get("__page");

				logger.debug("GetASISLayoutMeta nRows:" + sRows);
				logger.debug("GetASISLayoutMeta nPage:" + sPage);
			}

			List<Map<String, Object>> rows = sqreamMapper.selectAsisLayoutMeta(utilClass.nvl_trim(sF_LOG_LEVEL),
					utilClass.nvl_trim(sF_MESSAGE_ID));

			JSONObject jsonobj = new JSONObject();
			JSONArray seriesArray = new JSONArray();

			jsonobj.put("result", "ERROR");

			int startIndex = 0;
			int endIndex = 0;
			
			for (Map<String, Object> row : rows) {
				JSONObject datas = new JSONObject();

				String ver = MagicViewRowMaps.getStr(row, "VERSION");
				String mfId = MagicViewRowMaps.getStr(row, "MESSAGE_FIELD_ID");
				String mfName = MagicViewRowMaps.getStr(row, "MESSAGE_FIELD_NAME");
				String sortOrder = MagicViewRowMaps.getStr(row, "SORT_ORDER");
				String dataLenStr = MagicViewRowMaps.getStr(row, "DATA_LENGTH");

				datas.put("VERSION", ver);
				datas.put("MESSAGE_FIELD_ID", mfId);
				datas.put("MESSAGE_FIELD_NAME", mfName + "(" + dataLenStr + ")");
				datas.put("SORT_ORDER", sortOrder);
				datas.put("DATA_LENGTH", dataLenStr);
				endIndex = Integer.parseInt(dataLenStr);
				if (!"".equals(utilClass.nvl_trim(sF_MESSAGE_HEADER_DATA))) {
					datas.put("DATA", sF_MESSAGE_HEADER_DATA.substring(startIndex, (startIndex + endIndex)));
				}

				startIndex += endIndex;
				seriesArray.add(datas);
				jsonobj.put("rows", seriesArray);

				nCount++;
			}

			if (nCount > 0 && !"".equals(utilClass.nvl_trim(sF_MESSAGE_HEADER_DATA))) {
				int total = nCount / Integer.parseInt(sRows);

				jsonobj.put("records", nCount);
				jsonobj.put("page", Integer.parseInt(sPage));
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

}
