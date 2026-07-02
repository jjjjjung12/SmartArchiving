package com.archiving.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.magicview.support.MagicViewRowMaps;

import sinsiway.PcaSessionPool;

@RestController
public class GetDataDecRst {
 
    public Logger logger = Logger.getLogger(this.getClass().getName()+".class");
    static UtilClass  utilClass = new UtilClass();
    
    sinsiway.PcaSession session = null;

	
	/* (레거시) 암복호화 테스트 컨트롤러 생성자 */
    public GetDataDecRst() {
//    	PropertyConfigurator.configure(System.getenv("CATALINA_HOME") + "/log4j.properties");
	}

	/* 입력 문자열을 암/복호화하여 결과 반환(__TYPE=ENC/DEC) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetDataDecRst", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getDataDecRst(@RequestParam(value = "param", required = false) String param) throws Exception {
	
		
		try{

			logger.debug("getDataDec ***** Start getDataDecRst *****"); 
			

			
			String sInput = "";
			String sType  = "";
			String decData = "";
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
	            } else
	            {
	            	logger.debug("Dec Process" );
	            	System.out.println("===================" + utilClass.GetCommSimpleProperty("server.properties", "cryptedServer"));
	            	decData = DeCrypt(sInput);
	            	
	            	if(decData != null) {
	            		if(sInput.length() > 0) {
	            			try {
						        byte[] bodyDecode64 = Base64.getDecoder().decode(decData.getBytes("UTF-8"));
						        if (bodyDecode64.length > 0) {
						        	sOutput = decompressMessage(bodyDecode64);
						        }
						    } catch (IllegalArgumentException e) {
						        // Base64가 아닌 경우 원본 텍스트 그대로 사용
						    	sOutput = decData;
						    }
	            		}
	            	}
	            	
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
	
	/* Petra Cipher 정책으로 문자열 복호화(session 없으면 원문 반환) */
    private String DeCrypt( String encrypted_data) throws Exception{
    	if (encrypted_data == null || encrypted_data.isEmpty()) {
    		return encrypted_data;
    	}
    	if (session == null) {
    		return encrypted_data;
    	}
		String decrypted_data = session.decrypt(utilClass.GetCommSimpleProperty("server.properties", "cryptedServer"), encrypted_data, 1);
		//session.logCurrRequest(0, "프로그램명", "사용자 ID");
		return decrypted_data;
    }
    
	/**
     * 메시지 압축 해제
     * @param message
     */
	/* GZIP 압축된 메시지 바이트를 UTF-8 문자열로 복원 */
    private String decompressMessage (byte[] message) {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(message));
             InputStreamReader reader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8)) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[8192];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) > 0) {
                stringBuilder.append(buffer, 0, bytesRead);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error Decompressing data", e);
        }
    }
 
}