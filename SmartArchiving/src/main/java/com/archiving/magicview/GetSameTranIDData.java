package com.archiving.magicview;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
//import java.security.spec.RSAOtherPrimeInfo;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.magicview.dao.mapper.MagicViewPostgresMapper;
import com.archiving.magicview.dao.sqream.MagicViewSqreamMapper;
import com.archiving.magicview.dto.SameTranIdQuery;
import com.archiving.magicview.support.MagicViewRowMaps;
import com.archiving.util.CodeClass;
import com.archiving.util.UtilClass;

import sinsiway.PcaSessionPool;

@RestController
public class GetSameTranIDData {
    private static final long serialVersionUID = 1L;
 
    UtilClass utilClass = new UtilClass();
	@Autowired
	private MagicViewSqreamMapper sqreamMapper;

	@Autowired
	private MagicViewPostgresMapper postgresMapper;

	public Logger logger = Logger.getLogger(this.getClass().getName() + ".class");

	/* (레거시) 동일 거래ID 상세 조회 컨트롤러 생성자 */
    public GetSameTranIDData() {
//    	PropertyConfigurator.configure(System.getenv("CATALINA_HOME") + "/log4j.properties");
	}


    sinsiway.PcaSession session = null;
    
	/* 동일 거래ID 상세 데이터 조회 API(복호화/압축해제 및 마스킹 이력 확인 포함) */
	@SuppressWarnings({ "unchecked", "unused" })
	@PostMapping(value = "/GetSameTranIDData", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getSameTranIdData(@RequestParam(value = "param", required = false) String param,
										HttpServletRequest request,
										HttpServletResponse response) throws Exception {

	    /**
	     * 신시웨이(Petra Cipher) 세션 — 네이티브 pcjapi 미설치 환경(로컬 Eclipse 등)에서는
	     * UnsatisfiedLinkError / ExceptionInInitializerError 가 나므로 전체 Throwable 을 처리한다.
	     * 이 경우 session 은 null 로 두고 MESSAGE_DATA 는 DB 저장값 그대로 내려간다(평문이면 그대로 표시).
	     */
		try {
			session = PcaSessionPool.getSession();
		} catch (Throwable t) {
			logger.warn("Petra Cipher(PcaSessionPool) 사용 불가 — pcjapi 네이티브 또는 설정 확인. 상세 본문 복호화 생략.", t);
			session = null;
		}
		
		JSONObject jsonobj = new JSONObject();
		
		CodeClass codeClass = new CodeClass();
		
		
		String userCd = (String) request.getSession().getAttribute("usercd");
			
		logger.debug("GetLayoutMeta userCd:" + userCd);
		
		try{

			logger.debug("MagicView ***** Start GetSameTranIDData *****"); 
			
			int    nCount = 0;
			int    nCount2 = 0;
			
			String sF_SP_TR_YMD_S    = "";
			String sF_SP_TR_YMD_E  	 = "";
			String sF_MESSAGE_SER_NO = "";
			String sF_USER_CD 		 = "";
			
			
			String sUseYn    = "";
			String sRows     = "";
			String sPage     = "";
			
			String sCorpValue ="";   //로그구분 : 은행, 상호, 비로그인
			String sServerValue ="";  //서버 구분 은행(bl), 상호(sl)
			String sDetailCorpValue ="";
			
  			String Obj = param;
			
			logger.debug("GetSameTranIDData jsonParam:" + Obj);
			
			if(Obj != null){
				
				logger.debug("getServerList DEBUG"); 
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(Obj.toString());

	            logger.debug("GetSameTranIDData json:" + json); 
	            	    		        	
	        	
	            sF_SP_TR_YMD_S   = (String)json.get("__F_SP_TR_YMD_S");
	            sF_SP_TR_YMD_E   = (String)json.get("__F_SP_TR_YMD_E");
	            sF_MESSAGE_SER_NO= (String)json.get("__F_MESSAGE_SER_NO");
	            sF_USER_CD    = (String)json.get("__F_USER_CD_DETAIL");
	            sUseYn    = (String)json.get("__use_yn");
	            sRows     = (String)json.get("__rows");
	            sPage     = (String)json.get("__page");
	            
	            sCorpValue = (String)json.get("__corpValue");  //회사 value efblview(은행), efsbview(상호)
	            sServerValue = (String)json.get("__serverValue");  //서버 value bl(은행), sl(상호)
	            sDetailCorpValue = (String)json.get("__detailCorpValue");  //팝업애햐할 회사구분코드  은행 1 and "" , 상호 2, 비로그인 0 
	            if( null == sServerValue || sServerValue.equals("") ) sServerValue = "bl";
	            if( null == sCorpValue || sCorpValue.equals("") ) sCorpValue = "efblview";
	            if( null == sDetailCorpValue || sDetailCorpValue.equals("") ) sDetailCorpValue = "1";   //기본 은행에서 찾습니다.
	            
	            response.setContentType("application/x-json charset=UTF-8");		
				logger.debug("GetSameTranIDData sUseYn   :" + sUseYn);
				logger.debug("GetSameTranIDData nRows:" + sRows);
				logger.debug("GetSameTranIDData nPage:" + sPage);
			} 
			
			if( null == sServerValue || sServerValue.equals("") ) sServerValue = "bl";
			
			jsonobj.put("result"  , "ERROR"); // 결과 초기화(exception 발생시 활용)
	        JSONArray  seriesArray = new JSONArray();	
			SameTranIdQuery q = new SameTranIdQuery();
			q.setServerValue(sServerValue);
			q.setCorpValue(sCorpValue);
			q.setDetailCorpValue(sDetailCorpValue);
			q.setTrYmdStart(sF_SP_TR_YMD_S);
			q.setTrYmdEnd(sF_SP_TR_YMD_E);
			q.setMessageSerNo(sF_MESSAGE_SER_NO);
			List<Map<String, Object>> sqRows = sqreamMapper.selectSameTranIdData(q);

			for (Map<String, Object> row : sqRows) {
				JSONObject datas = new JSONObject();
				datas.put("P_MESSAGE_SER_NO", MagicViewRowMaps.getStr(row, "MESSAGE_SER_NO"));
				datas.put("P_TRX_DTIME", MagicViewRowMaps.getStr(row, "TRX_DTIME"));
				datas.put("P_MESSAGE_ID", MagicViewRowMaps.getStr(row, "MESSAGE_ID"));
				datas.put("P_TRX_TRACKING_NO", MagicViewRowMaps.getStr(row, "TRX_TRACKING_NO"));

				datas.put("P_USER_ID", MagicViewRowMaps.getStr(row, "USER_ID"));
				datas.put("P_CUST_REG_NO", MagicViewRowMaps.getStr(row, "CUST_REG_NO"));
				datas.put("P_IP", MagicViewRowMaps.getStr(row, "IP"));
				datas.put("P_MESSAGE_RES", MagicViewRowMaps.getStr(row, "MESSAGE_RES"));

				datas.put("P_REQ_RES_TYPE", MagicViewRowMaps.getStr(row, "REQ_RES_TYPE"));
				datas.put("P_REQ_RES_TYPE_NM",
						codeClass.codeSet("REQ_RES_TYPE_CD", MagicViewRowMaps.getStr(row, "REQ_RES_TYPE")));
			
				 logger.debug("GetSameTranIDData dec before ###################################[" + nCount + "]"); 
				String decData = DeCrypt(MagicViewRowMaps.getStr(row, "MESSAGE_DATA"));
				
				String DecompressMessage = "";
				if(decData != null) {
					if(decData.length() > 0 )
					{
//						byte[] bodyDecode64 = Base64.getDecoder().decode( decData.getBytes("UTF-8"));
//						if( bodyDecode64.length > 0)
//						{
//							DecompressMessage = decompressMessage( bodyDecode64 );
//						}
						
						try {
					        byte[] bodyDecode64 = Base64.getDecoder().decode(decData.getBytes("UTF-8"));
					        if (bodyDecode64.length > 0) {
					            DecompressMessage = decompressMessage(bodyDecode64);
					        }
					    } catch (IllegalArgumentException e) {
					        // Base64가 아닌 경우 원본 텍스트 그대로 사용
					        DecompressMessage = decData;
					    }
					}
				}
				
            	datas.put("P_MESSAGE_DATA"   	, DecompressMessage );			
				//datas.put("P_MESSAGE_DATA"   	,  rs.getString("MESSAGE_DATA")  );			
				seriesArray.add(datas);
				jsonobj.put("rows"  ,seriesArray);   
			
				nCount++;
			}
	        
	        //rs.close();
	        //stmt.close();
	        //connectionDest.close();
	        /* ykh  masking은 login 시점에 실행  **/
	        long time = System.currentTimeMillis();
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");
			String sCurrTime = dayTime.format(new Date(time));
	        
	        logger.debug("GetSameTranIDData mask check userCd=" + sF_USER_CD + " date=" + sCurrTime);
	        if (sF_USER_CD != null && !sF_USER_CD.isEmpty()) {
	        	nCount2 = postgresMapper.countMaskHistoryToday(sF_USER_CD, sCurrTime);
	        }
	        /**  **/                                                                                                                                                                                                                                               
	        if (nCount> 0 ) {
				int total = nCount / Integer.parseInt(sRows);
				
				//마스킹 해제 결재 유무
				/* ykh  if(nCount2 > 0) {
					jsonobj.put("maskYn" , "Y"  );
				}else {
					jsonobj.put("maskYn" , "N"  );
				}
				**/
				jsonobj.put("records" , nCount  );  
				jsonobj.put("page"    , Integer.parseInt(sPage)      ); 
				jsonobj.put("total"   , total     );  
				jsonobj.put("result"  , "OK"      );  
			} else {  
				jsonobj.put("result"  , "NOTFOUND");  
			}
				
	        logger.debug("GetSameTranIDData End 111111111111###################################"); 
//	        response.setContentType("text/plain");
//	        response.setCharacterEncoding("UTF-8");
//	        response.getWriter().write(jsonobj.toString());
			//logger.debug("getServerList :" + jsonobj.toString() ); 
	        //rs2.close();
	        //stmt2.close();
	        //connectionDest2.close();
	        
		} catch(Exception e){
			   logger.debug("GetSameTranIDData Exception 2222222222222###################################"); 
			e.printStackTrace();
		}
		
		return jsonobj;
    }
	
	
    /**
     * 암호화 진행
     * 은행 정책명 : BK.SEED
     * 상호 정책명 : MF.SEED
     */
	/* Petra Cipher 정책으로 문자열 암호화(session 없으면 원문 반환) */
    private String EnCrypt( String src) throws Exception{
    	if (session == null) {
    		return src;
    	}
    	String encrypted_data = session.encrypt(utilClass.GetCommSimpleProperty("server.properties", "cryptedServer"), src, 1);
		//String decrypted_data = session.decrypt("정채명", encrypted_data, 1);
		//session.logCurrRequest(0, "프로그램명", "사용자 ID");
		return encrypted_data;
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