package com.archiving.magicview;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MagicView {
    public Logger logger = Logger.getLogger(this.getClass().getName()+".class");
	
	/* (레거시) MagicView 테스트 컨트롤러 생성자 */
    public MagicView() {
//    	PropertyConfigurator.configure(System.getenv("CATALINA_HOME") + "/log4j.properties");
	}


	/* MagicView 더미 응답 API(테스트용 데이터 반환) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/MagicView", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject magicView(@RequestParam(value = "param", required = false) String param,
								HttpServletRequest request) throws Exception {

		try{

			logger.debug("MagicView ***** Start GetServerList *****"); 
			
			int    nCount = 0;
			
			String sF_SP_TR_YMD_S    = "";
			String sF_SP_TR_YMD_E  	 = "";
			String sF_SP_TR_NO     	 = "";
			String sF_SP_USER_ID   	 = "";
			String sF_SP_CUST_NO   	 = "";
			String sF_SP_IN_IP     	 = "";
			String sF_SP_SERVICE_ID	 = "";
			String sF_SP_ERR_MSG_CD    	 = "";
			String sF_SP_GUID      	 = "";
			String sF_SP_TR_ID     	 = "";
			String sF_SP_TR_NM     	 = "";			
			String sServerId = "";
			String sUseYn    = "";
			String sRows     = "";
			String sPage     = "";

				
  			String Obj = param;
			
			logger.debug("getServerList jsonParam:" + Obj);
			
			if(Obj != null){
				
				logger.debug("getServerList DEBUG"); 
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(Obj.toString());

	            logger.debug("getServerList json:" + json); 
	            
	            sF_SP_TR_YMD_S   = (String)json.get("__F_SP_TR_YMD_S");
	            sF_SP_TR_YMD_E   = (String)json.get("__F_SP_TR_YMD_E");
	            sF_SP_TR_NO      = (String)json.get("__F_SP_TR_NO");
	            sF_SP_USER_ID    = (String)json.get("__F_SP_USER_ID");
	            sF_SP_CUST_NO    = (String)json.get("__F_SP_CUST_NO");
	            sF_SP_IN_IP      = (String)json.get("__F_SP_IN_IP");
	            sF_SP_SERVICE_ID = (String)json.get("__F_SP_SERVICE_ID");
	            sF_SP_ERR_MSG_CD     = (String)json.get("__F_SP_ERR_MSG_CD");
	            sF_SP_GUID       = (String)json.get("__F_SP_GUID");
	            sF_SP_TR_ID      = (String)json.get("__F_SP_TR_ID");
	            sF_SP_TR_NM      = (String)json.get("__F_SP_TR_NM");
	            sServerId = (String)json.get("__server_id");
	            sUseYn    = (String)json.get("__use_yn");
	            sRows     = (String)json.get("__rows");
	            sPage     = (String)json.get("__page");
	            
				logger.debug("getServerList sServerId:" + sServerId);
				logger.debug("getServerList sUseYn   :" + sUseYn);
				logger.debug("getServerList nRows:" + sRows);
				logger.debug("getServerList nPage:" + sPage);
			} 
			
			JSONObject jsonobj = new JSONObject();
	        JSONArray  seriesArray = new JSONArray();	
	        
			int per = safeInt(sRows, 50);
			int page = safeInt(sPage, 1);

			for( int i = 0; i< 20; ++i)
			{
				JSONObject datas = new JSONObject();
				datas.put("SP_TR_YMD"   	, "SP_TR_YMD" + i);
				datas.put("SP_INST_ID"   	, "SP_INST_ID" + i);
				datas.put("SP_TR_NO"   	    , "SP_TR_NO" + i);
				datas.put("SP_USER_ID"   	, "SP_USER_ID" + i);
				datas.put("SP_USER_NAME"   	, "SP_USER_NAME" + i);
				datas.put("SP_JUMIN_NO"   	, "SP_JUMIN_NO" + i);
				datas.put("SP_CUST_NO"   	, "SP_CUST_NO" + i);
				datas.put("SP_IN_IP"      	, "SP_IN_IP" + i);
				datas.put("SP_SERVICE_ID"   , "SP_SERVICE_ID" + i);
				datas.put("SP_SERVICE_NAME" , "SP_SERVICE_NAME" + i);				
				datas.put("SP_RST_CD"   	, "SP_RST_CD" + i);
				datas.put("SP_ERR_MSG_CD"   , "SP_ERR_MSG_CD" + i);
				datas.put("SP_GUID"   	    , "SP_GUID" + i);
				datas.put("SP_UPMU_SID"   	, "SP_UPMU_SID" + i);
				datas.put("SP_TR_ID"   	    , "SP_TR_ID" + i);
				datas.put("SP_TR_NM"   	    , "SP_TR_NM" + i);
				datas.put("SP_BODY_ID"   	, "SP_BODY_ID" + i);
				datas.put("SP_COM_ID"   	, "SP_COM_ID" + i);
				datas.put("SP_TR_CD"   	    , "SP_TR_CD" + i);
				datas.put("SP_DT_CD"   	    , "SP_DT_CD" + i);
				datas.put("SP_TR_VALUE"   	, "SP_TR_VALUE" + i);
				datas.put("SP_STD_DATA"   	, "SP_STD_DATA" + i);
				
				seriesArray.add(datas);
				jsonobj.put("rows"  ,seriesArray);
				nCount++;
			}
			
			int total = per == 0 ? 0 : (nCount / per);
			
			jsonobj.put("records" , nCount  );  
			jsonobj.put("page"    , page      );
			jsonobj.put("total"   , total     );  
			jsonobj.put("result"  , "OK"      );  
	
			logger.debug("getServerList : " + jsonobj.toString() ); 
			return jsonobj;
			
		} catch(Exception e){
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
			if (v == null || v.trim().isEmpty()) return def;
			return Integer.parseInt(v.trim());
		} catch (Exception e) {
			return def;
		}
	}
	
 
}