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
import com.archiving.magicview.dto.LayoutMetaQuery;
import com.archiving.magicview.support.MagicViewRowMaps;

@RestController
public class GetLayoutMeta {
   
    public Logger logger = Logger.getLogger(this.getClass().getName()+".class");
	@Autowired
	private MagicViewSqreamMapper sqreamMapper;
	
	/* (레거시) 레이아웃 메타 조회 컨트롤러 생성자 */
    public GetLayoutMeta() {
//    	PropertyConfigurator.configure(System.getenv("CATALINA_HOME") + "/log4j.properties");
	}


	/* 전문 레이아웃 메타 조회 API(더미 전문/전체계좌 전문 처리 포함) */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/GetLayoutMeta", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getLayoutMeta(@RequestParam(value = "param", required = false) String param,
									HttpServletRequest request) throws Exception {
		JSONObject jsonobj = new JSONObject();
		try{

			logger.debug("MagicView ***** Start GetLayoutMeta *****"); 
			
			int    nCount = 0;
			
			
			String sF_TRX_DTIME    = "";
			String sF_MESSAGE_ID  	 = "";			
			String sUseYn    = "";
			String sRows     = "";
			String sPage     = "";

			String sCorpValue ="";   //로그구분 : 은행, 상호, 비로그인
			String sServerValue ="";  //서버 구분 은행(bl), 상호(sl)
			String sDetailCorpValue ="";
			String sReq_res_type	="";
			
			String sF_MESSAGE_RES ="";  //dummy res
			
			boolean gubun = true;
				
  			String Obj = param;
  			
  			String userId = (String) request.getSession().getAttribute("userid");
  			String userCd = (String) request.getSession().getAttribute("usercd");
			
			logger.debug("GetLayoutMeta jsonParam:" + Obj);
			logger.debug("GetLayoutMeta userId:" + userId);
			logger.debug("GetLayoutMeta userCd:" + userCd);
			
			if(Obj != null){
				
				logger.debug("getServerList DEBUG"); 
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(Obj.toString());
	            logger.debug("getServerList json:" + json); 
	            
	            sF_TRX_DTIME   = (String)json.get("__F_TRX_DTIME");
	            sF_MESSAGE_ID   = (String)json.get("__F_MESSAGE_ID");	          
	            sUseYn    = (String)json.get("__use_yn");
	            sRows     = (String)json.get("__rows");
	            sPage     = (String)json.get("__page");
	            sReq_res_type = (String)json.get("req_res_type");
	            
	            sF_MESSAGE_RES   = (String)json.get("__F_MESSAGE_RES");	 
	            
	            sCorpValue = (String)json.get("__corpValue");  //회사 value efblview(은행), efsbview(상호)
	            sServerValue = (String)json.get("__serverValue");  //서버 value bl(은행), sl(상호)
	            logger.debug("getServerList sServerValue:" + sServerValue);
	            sDetailCorpValue = (String)json.get("__detailCorpValue");  //팝업애햐할 회사구분코드  은행 1 and "" , 상호 2, 비로그인 0 
	            if( null == sServerValue || sServerValue.equals("") ) sServerValue = "bl";
	            if( null == sCorpValue || sCorpValue.equals("") ) sCorpValue = "efblview";
	            if( null == sDetailCorpValue || sDetailCorpValue.equals("") ) sDetailCorpValue = "1";   //기본 은행에서 찾습니다.
	            
	            
				logger.debug("getServerList sUseYn   :" + sUseYn);
				logger.debug("getServerList nRows:" + sRows);
				logger.debug("getServerList sServerValue:" + sServerValue);
			} 
			
			logger.debug("GetLayoutMeta nPage:" + sPage);
			LayoutMetaQuery q = new LayoutMetaQuery();
			q.setServerValue(sServerValue);
			q.setTrxDtime(sF_TRX_DTIME);
			q.setMessageId(sF_MESSAGE_ID);
			q.setReqResType(sReq_res_type);
			q.setMessageRes(sF_MESSAGE_RES);
			List<Map<String, Object>> metaRows = sqreamMapper.selectLayoutMeta(q);

			JSONArray seriesArray = new JSONArray();

	        jsonobj.put("result"  , "ERROR");  // 寃곌낵�쓽 �꽦怨듭뿬遺�瑜� �솗�씤
	        
	        for (Map<String, Object> row : metaRows) {
				JSONObject datas = new JSONObject();
				
				datas.put("P_REG_DTM", MagicViewRowMaps.getStr(row, "REG_DTM"));
				datas.put("P_CHG_DTM", MagicViewRowMaps.getStr(row, "CHG_DTM"));
				datas.put("P_TGRM_SNO", MagicViewRowMaps.getStr(row, "TGRM_SNO"));
				datas.put("P_REPR_TGRM_ID", MagicViewRowMaps.getStr(row, "REPR_TGRM_ID"));
				datas.put("P_TGRM_NM_ENG", MagicViewRowMaps.getStr(row, "TGRM_NM_ENG"));
				datas.put("P_TGRM_NM_KOR", MagicViewRowMaps.getStr(row, "TGRM_NM_KOR"));
				datas.put("P_ETC_FRWD_YN1", MagicViewRowMaps.getStr(row, "ETC_FRWD_YN1"));
				datas.put("P_MSK_PTTRN", MagicViewRowMaps.getStr(row, "MSK_PTTRN"));
							
				seriesArray.add(datas);
				jsonobj.put("rows"  ,seriesArray);   
			
				nCount++;
				
				String tgrmNmEng = MagicViewRowMaps.getStr(row, "TGRM_NM_ENG");
				if (tgrmNmEng != null && tgrmNmEng.toUpperCase().contains("DUMMY")) {
					gubun = false;
				}
			}
			
	        if(nCount <= 5 && !gubun) {
	        	if( null != sF_MESSAGE_RES && !sF_MESSAGE_RES.equals("") && sReq_res_type.equals("4")  ) {
	        		//if( userCd.equals("newbie"))
	        		logger.debug("GetLayoutMeta dummy MESSAGE_RES ################### :" +sF_MESSAGE_RES); 
	        		JSONObject dummyJsonobj = getDummyResLayout(sF_TRX_DTIME, sF_MESSAGE_RES,sReq_res_type,sServerValue);
	        			if( dummyJsonobj.get("res_dummy") == "dummy" ) {
	        				//logger.debug("GetLayoutMeta 1 dummy size ################ :" +dummyJsonobj.get("res_count")); 
	        				//if( Integer.parseInt( dummyJsonobj.get("res_count").toString()) < 5)   // dummy가 정이 되었어도 layout 필드가 많으면 dummy로 인식하지않는다.
	        					throw new Exception("더미데이터 입니다.");
	        				//else
	        				//	jsonobj = dummyJsonobj ;
	        			}else {
	        				logger.debug("GetLayoutMeta dummy MESSAGE_RES ################### :" +sF_MESSAGE_RES + "## return size:" + dummyJsonobj.get("res_count").toString()); 
//	        				if( dummyJsonobj.size() == 3) {
//	        					throw new Exception("존재하지 않는 전문 입니다.");
//	        				}else {
//	        					jsonobj = dummyJsonobj ;
//	        				}
	        				
	        				if( Integer.parseInt( dummyJsonobj.get("res_count").toString()) <= 3)   // dummy가 정이 되었어도 layout 필드가 많으면 dummy로 인식하지않는다.
	        					throw new Exception("존재하지 않는 전문 입니다.");
	        				else
	        					jsonobj = dummyJsonobj ;
	        			}
	        	}else {
	        		throw new Exception("더미데이터 입니다.");
	        	}
	        }
	        else {//   //전체 계좌 대상 rows 가져 오기 ykh
	        	
	        	//if( userCd.equals("newbie"))
	        		if( sReq_res_type.equals("4")) {  // OUT 전문일때만
	        			if( null != sF_MESSAGE_RES && !sF_MESSAGE_RES.equals("") && !sF_MESSAGE_ID.equals(sF_MESSAGE_RES)){
	        				logger.debug("GetLayoutMeta MESSAGE_RES ################### :" +sF_MESSAGE_RES); 
	        				JSONObject dummyJsonobj = getDummyResLayout(sF_TRX_DTIME, sF_MESSAGE_RES,sReq_res_type,sServerValue);
	        				if( dummyJsonobj.get("res_dummy") == "dummy" ) {
	        					//logger.debug("GetLayoutMeta 2 dummy size ################ :" +dummyJsonobj.size()); 
	        					//if( Integer.parseInt( dummyJsonobj.get("res_count").toString()) < 5)    // dummy가 정이 되었어도 layout 필드가 많으면 dummy로 인식하지않는다.
		        					throw new Exception("더미데이터 입니다.");
		        				//else
		        				//	jsonobj = dummyJsonobj ;
		        			}else {
		        				logger.debug("GetLayoutMeta dummy MESSAGE_RES ################### :" +sF_MESSAGE_RES + "## return size:" + dummyJsonobj.get("res_count").toString()); 
		        				if( dummyJsonobj.size() != 3) {
		        				//if(  Integer.parseInt( dummyJsonobj.get("res_count").toString()) > 3) {
		        					jsonobj = dummyJsonobj ;
		        				}
		        			}
	        			}else {
	        				
	        				JSONObject allAccountJson = getAllAcountLayout(sF_TRX_DTIME, sF_MESSAGE_ID,sReq_res_type,sServerValue);
	        				if( Integer.parseInt( allAccountJson.get("records").toString()) != 0 )
	        					jsonobj = allAccountJson;
	        			}
	        		}
	        	
	        }
	     
	        
	        
			if (nCount> 0 ) {
				int total = nCount / Integer.parseInt(sRows);
				
				jsonobj.put("records" , nCount  );  
				jsonobj.put("page"    , Integer.parseInt(sPage)      ); 
				jsonobj.put("total"   , total     );  
				jsonobj.put("result"  , "OK"      );  
			} else {  
				jsonobj.put("result"  , "NOTFOUND");  
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.debug("GetLayoutMeta err :" + e.getMessage()); 
			jsonobj.put("result"  , "ERR"      		);  
			jsonobj.put("message" , e.getMessage()  );  
		} finally { }

		 return jsonobj;

    }

	//전체 계좌 layout 가져 오기 : ykh
	/* 전체 계좌 레이아웃 메타 조회 */
	JSONObject getAllAcountLayout(String sF_TRX_DTIME, String sF_MESSAGE_ID, String sReq_res_type, String sServerValue)
	{
		int nCount = 0 ;
		JSONObject jsonobj = new JSONObject();
		JSONArray  seriesAcountArray = new JSONArray();
		try{
			LayoutMetaQuery q = new LayoutMetaQuery();
			q.setServerValue(sServerValue);
			q.setTrxDtime(sF_TRX_DTIME);
			q.setMessageId(sF_MESSAGE_ID);
			q.setReqResType(sReq_res_type);
			List<Map<String, Object>> acctRows = sqreamMapper.selectAllAccountLayout(q);
	
			nCount = 0;
			for (Map<String, Object> acctRow : acctRows){
				JSONObject dataSet = new JSONObject();
				
				dataSet.put("P_REG_DTM", MagicViewRowMaps.getStr(acctRow, "REG_DTM"));
				dataSet.put("P_CHG_DTM", MagicViewRowMaps.getStr(acctRow, "CHG_DTM"));
				dataSet.put("P_TGRM_SNO", MagicViewRowMaps.getStr(acctRow, "TGRM_SNO"));
				dataSet.put("P_REPR_TGRM_ID", MagicViewRowMaps.getStr(acctRow, "REPR_TGRM_ID"));
				dataSet.put("P_TGRM_NM_ENG", MagicViewRowMaps.getStr(acctRow, "TGRM_NM_ENG"));
				dataSet.put("P_TGRM_NM_KOR", MagicViewRowMaps.getStr(acctRow, "TGRM_NM_KOR"));
				dataSet.put("P_ETC_FRWD_YN1", MagicViewRowMaps.getStr(acctRow, "ETC_FRWD_YN1"));
				dataSet.put("P_MSK_PTTRN", MagicViewRowMaps.getStr(acctRow, "MSK_PTTRN"));
							
				seriesAcountArray.add(dataSet);
				jsonobj.put("rows"  ,seriesAcountArray);   
			
				nCount++;
				
				
			}
	      
	        
	       
		} catch(Exception e){
			e.printStackTrace();
		} finally { }
		jsonobj.put("records"  ,nCount);
		return jsonobj;

	}

	//dummy layout 가져 오기 : ykh
	/* 더미 응답 전문 레이아웃 메타 조회 */
	JSONObject getDummyResLayout(String sF_TRX_DTIME, String sF_MESSAGE_RES, String sReq_res_type, String sServerValue)
		{
			int nCount = 0 ;
			JSONObject jsonobj = new JSONObject();

			JSONArray  seriesAcountArray = new JSONArray();

			try{
				LayoutMetaQuery q = new LayoutMetaQuery();
				q.setServerValue(sServerValue);
				q.setTrxDtime(sF_TRX_DTIME);
				q.setReqResType(sReq_res_type);
				q.setMessageRes(sF_MESSAGE_RES);
				List<Map<String, Object>> dummyRows = sqreamMapper.selectDummyResLayout(q);
				jsonobj.put("res_dummy"  ,"");   
				jsonobj.put("res_count"  ,nCount);   
				for (Map<String, Object> drow : dummyRows){
					JSONObject dataSet = new JSONObject();
					
					dataSet.put("P_REG_DTM", MagicViewRowMaps.getStr(drow, "REG_DTM"));
					dataSet.put("P_CHG_DTM", MagicViewRowMaps.getStr(drow, "CHG_DTM"));
					dataSet.put("P_TGRM_SNO", MagicViewRowMaps.getStr(drow, "TGRM_SNO"));
					dataSet.put("P_REPR_TGRM_ID", MagicViewRowMaps.getStr(drow, "REPR_TGRM_ID"));
					dataSet.put("P_TGRM_NM_ENG", MagicViewRowMaps.getStr(drow, "TGRM_NM_ENG"));
					dataSet.put("P_TGRM_NM_KOR", MagicViewRowMaps.getStr(drow, "TGRM_NM_KOR"));
					dataSet.put("P_ETC_FRWD_YN1", MagicViewRowMaps.getStr(drow, "ETC_FRWD_YN1"));
					dataSet.put("P_MSK_PTTRN", MagicViewRowMaps.getStr(drow, "MSK_PTTRN"));
								
					seriesAcountArray.add(dataSet);
					jsonobj.put("rows"  ,seriesAcountArray);   
					String eng = MagicViewRowMaps.getStr(drow, "TGRM_NM_ENG");
					if (eng != null && eng.toUpperCase().contains("DUMMY")) {
						jsonobj.put("res_dummy"  ,"dummy");   
					}
					nCount++;
				}
				jsonobj.put("res_count"  ,nCount);   
		        
		       
			} catch(Exception e){
				e.printStackTrace();
			} finally { }
		//	logger.debug("## return getDummyResLayout:" + jsonobj.toString());
			return jsonobj;
		
		}
}