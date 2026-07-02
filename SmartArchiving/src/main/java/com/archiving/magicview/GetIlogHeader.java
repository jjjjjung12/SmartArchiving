package com.archiving.magicview;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.magicview.dao.sqream.MagicViewSqreamMapper;
import com.archiving.magicview.dto.IlogHeaderQuery;
import com.archiving.magicview.support.MagicViewRowMaps;
import com.archiving.util.CodeClass;
import com.archiving.util.UtilClass;

@RestController
public class GetIlogHeader {

     
    public Logger logger = Logger.getLogger(this.getClass().getName()+".class");	
	@Autowired
	private MagicViewSqreamMapper sqreamMapper;
	
	/* ILOG 헤더 조회 API(조건 검색/페이징/대량건수 제한) */
	@SuppressWarnings({ "unchecked", "resource" })
	@PostMapping(value = "/GetIlogHeader", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getIlogHeader(@RequestParam(value = "param", required = false) String param,
									HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		JSONObject jsonobj = new JSONObject();
 		String jsonParam = param;
		
 		UtilClass utilClass = new UtilClass();
 		CodeClass codeClass = new CodeClass();
 		
		try{
			logger.debug("MagicView ***** Start GetIlogHeader *****"); 
			
			int    nCount = 0;
			
			String sF_SP_TR_YMD_S    = "";
			String sF_SP_TR_YMD_E  	 = "";
			String sF_SP_MESSAGE_SER_NO  = "";
			String sF_SP_USER_ID   	 = "";
			String sF_SP_REG_CUST_NO = "";
			String sF_SP_IN_IP 		 = "";
			String sF_SP_MESSAGE_ID  = "";
			String sF_MAC       	 = "";
			String sF_TR_ACNO        = "";
			String sF_IN_ACNO        = "";
			String sF_TEL_NO       	 = "";
			String sF_SP_SCRN_ID	 = "";
			String sF_LOG_CR_INFO	 = "";
			
			String sServerId = "";
			String sUseYn    = "";
			String sRows     = "";
			String sPage     = "";
			String sCorpValue ="";   //로그구분 : 은행, 상호, 비로그인
			String sServerValue ="";  //서버 구분 은행(bl), 상호(sl)
				
  			logger.debug("GetIlogHeader jsonParam:" + jsonParam);
  			
  			if(jsonParam != null){
				JSONParser parser = new JSONParser();
				JSONObject json;
				json = (JSONObject) parser.parse(jsonParam.toString());
	            logger.debug("GetIlogHeader json:" + json); 
	            
	            sF_SP_TR_YMD_S   	= utilClass.nvl_trim((String)json.get("__F_SP_TR_YMD_S"));
	            sF_SP_TR_YMD_E   	= utilClass.nvl_trim((String)json.get("__F_SP_TR_YMD_E"));
	            sF_SP_MESSAGE_SER_NO= utilClass.nvl_trim((String)json.get("__F_SP_MESSAGE_SER_NO"));
	            sF_SP_USER_ID    	= utilClass.nvl_trim((String)json.get("__F_SP_USER_ID"));
	            sF_SP_REG_CUST_NO   = utilClass.nvl_trim((String)json.get("__F_SP_REG_CUST_NO"));
	            sF_SP_IN_IP      	= utilClass.nvl_trim((String)json.get("__F_SP_IN_IP"));
	            sF_SP_MESSAGE_ID 	= utilClass.nvl_trim((String)json.get("__F_SP_MESSAGE_ID"));
	            sF_MAC 				= utilClass.nvl_trim((String)json.get("__F_MAC"));
	            sF_TR_ACNO 			= utilClass.nvl_trim((String)json.get("__F_TR_ACNO"));
	            sF_IN_ACNO 			= utilClass.nvl_trim((String)json.get("__F_IN_ACNO"));
	            sF_TEL_NO 			= utilClass.nvl_trim((String)json.get("__F_TEL_NO"));
	            sF_SP_SCRN_ID		= utilClass.nvl_trim((String)json.get("__F_SP_SCRN_ID"));
	            sF_LOG_CR_INFO		= utilClass.nvl_trim((String)json.get("__F_LOG_CR_INFO"));
	            
	            sServerId = (String)json.get("__server_id");
	            sUseYn    = (String)json.get("__use_yn");
	            sRows     = (String)json.get("__rows");
	            sPage     = (String)json.get("__page");
	            sCorpValue = (String)json.get("__corpValue");  //회사 value efblview(은행), efsbview(상호)
	            logger.debug("GetIlogHeader sCorpValue:" + sCorpValue);
	            if( null == sCorpValue || sCorpValue.equals("") ) sCorpValue = "efblview";
	            sServerValue = (String)json.get("__serverValue");  //서버 value bl(은행), sl(상호)
	            if( null == sServerValue || sServerValue.equals("") ) sServerValue = "bl";
	            response.setContentType("application/x-json charset=UTF-8");
				logger.debug("GetIlogHeader sServerId:" + sServerId);
				logger.debug("GetIlogHeader sUseYn   :" + sUseYn);
				logger.debug("GetIlogHeader nRows:" + sRows);
				logger.debug("GetIlogHeader nPage:" + sPage);
				logger.debug("GetIlogHeader sCorpValue:" + sCorpValue);
			} 
			IlogHeaderQuery q = new IlogHeaderQuery();
			q.setServerValue(sServerValue);
			q.setCorpValue(sCorpValue);
			q.setTrYmdStart(sF_SP_TR_YMD_S);
			q.setTrYmdEnd(sF_SP_TR_YMD_E);
			q.setLogCrInfo(sF_LOG_CR_INFO);
			q.setMessageSerNo(sF_SP_MESSAGE_SER_NO);
			q.setUserId(sF_SP_USER_ID);
			q.setRegCustNo(sF_SP_REG_CUST_NO);
			q.setInIp(sF_SP_IN_IP);
			q.setMessageId(sF_SP_MESSAGE_ID);
			q.setMac(sF_MAC);
			q.setTrAcno(sF_TR_ACNO);
			q.setInAcno(sF_IN_ACNO);
			q.setTelNo(sF_TEL_NO);
			q.setScrnId(sF_SP_SCRN_ID);
			List<Map<String, Object>> rows = sqreamMapper.selectIlogHeader(q);

			JSONArray seriesArray = new JSONArray();

			for (Map<String, Object> row : rows) {
				JSONObject datas = new JSONObject();

				if (nCount > 500000) {
					nCount = 0;
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
				jsonobj.put("rows", seriesArray);

				nCount++;
			}
			
			if (nCount> 0 ) {
				int total = nCount / Integer.parseInt(sRows);
				
				jsonobj.put("records" , nCount  );  
				jsonobj.put("page"    , Integer.parseInt(sPage)      ); 
				jsonobj.put("total"   , total     );  
				jsonobj.put("result"  , "OK"      );  
			} else {  
				if("over".equals(jsonobj.get("result"))) {
					jsonobj.put("rows", "");
					jsonobj.put("result"  , "데이터가 500000건이 넘습니다. \n 조회조건을 바꿔주세요.");
				}else {
					jsonobj.put("result"  , "NOTFOUND");
				}
				
				  
			}
			
		} catch(Exception e){
			e.printStackTrace();
			jsonobj.put("result"  , e.getMessage());
			
		} finally { }
		return jsonobj;
	}
}