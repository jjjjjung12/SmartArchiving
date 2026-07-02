package com.archiving.util;

import java.io.IOException;


import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetEncDecRst {
 
    public Logger logger = Logger.getLogger(this.getClass().getName()+".class");
    static UtilClass  utilClass = new UtilClass();

	
	/* (레거시) 암복호화 테스트 컨트롤러 생성자 */
    public GetEncDecRst() {
//    	PropertyConfigurator.configure(System.getenv("CATALINA_HOME") + "/log4j.properties");
	}

	/* 입력 문자열을 암/복호화하여 결과 반환(__TYPE=ENC/DEC) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetEncDecRst", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getEncDecRst(@RequestParam(value = "param", required = false) String param) throws Exception {
	
		
		try{

			logger.debug("getEncDec ***** Start getEncDec *****"); 
			

			
			String sInput = "";
			String sType  = "";
			String sOutput = "";
			
			String jsonParam = param;
			
			if(jsonParam != null){ 
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(jsonParam.toString());

	            logger.debug("getJobMonitor json:" + json); 
	              
	            sInput = (String)json.get("__F_INPUT_TEXT");
	            sType = (String)json.get("__TYPE");


	            if(sType.equals("ENC"))
	            {
	            	logger.debug("Enc Process" );	            	
	            	sOutput = utilClass.encrypt(sInput);
	            }else
	            {
	            	logger.debug("Dec Process" );
	            	sOutput = utilClass.decrypt(sInput);
	            }
	            
				logger.debug("getEncDec Input:" + sInput);
				logger.debug("getEncDec Rst:" + sOutput);

				JSONObject jsonobj = new JSONObject();
			    
		    	jsonobj.put("OUTPUT" , sOutput  ); 			
		    	jsonobj.put("result", "OK");
				return jsonobj;
			}
			

			
		}catch(Exception e){
			logger.debug("Rst:" + e.getMessage());
			JSONObject err = new JSONObject();
			err.put("result", "ERR");
			err.put("message", e.getMessage());
			return err;
		}

		JSONObject out = new JSONObject();
		out.put("result", "ERR");
		out.put("message", "NO_PARAM");
		return out;
    }
 
}