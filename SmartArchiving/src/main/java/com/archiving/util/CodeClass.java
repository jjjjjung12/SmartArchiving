package com.archiving.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class CodeClass { 
	
	
	public Logger logger = Logger.getLogger(this.getClass().getName()+".class");
	
	
	/** Server List **/
	private static HashMap<String, String > hashMapServer = new HashMap<String, String>();

	/* 서버ID→서버명 캐시 맵 반환 */
	public static synchronized HashMap<String, String> getHashMapServer() {
		return hashMapServer;
	}

	/* 서버ID→서버명 캐시 맵 설정 */
	public static synchronized void setHashMapServer(HashMap<String, String> target) {
		hashMapServer = target;
	}


	
	/** Code List **/
	private static HashMap<String, ArrayList<CodeDetail>> hashMap = new HashMap<String, ArrayList<CodeDetail>>();

	/* 코드그룹→코드상세 목록 캐시 맵 반환 */
	public static synchronized HashMap<String, ArrayList<CodeDetail>> getHashMap() {
		return hashMap;
	}

	/* 코드그룹→코드상세 목록 캐시 맵 설정 */
	public static synchronized void setHashMap(HashMap<String, ArrayList<CodeDetail>> target) {
		hashMap = target;
	}

	public static class CodeDetail {
		
		String grpCD, code, codeNm;

		/* 코드그룹 반환 */
		public String getGrpCD() {
			return grpCD;
		}

		/* 코드그룹 설정 */
		public void setGrpCD(String grpCD) {
			this.grpCD = grpCD;
		}

		/* 코드값 반환 */
		public String getCode() {
			return code;
		}

		/* 코드값 설정 */
		public void setCode(String code) {
			this.code = code;
		}

		/* 코드명 반환 */
		public String getCodeNm() {
			return codeNm;
		}

		/* 코드명 설정 */
		public void setCodeNm(String codeNm) {
			this.codeNm = codeNm;
		}
	}
	
	/* 코드/서버 캐시 접근 유틸 생성자 */
	public CodeClass() {
		super();
		
	//	logger.debug("**** ... CodeClass .........................");
	}
	
	
	/* 서버 목록을 HTML <option> 문자열로 생성 */
	public String getComboBoxByServer(String value, boolean first) {
		
		ArrayList<String> resultList = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		String selChk = "";
	
		String sServerId = "";
		String sServerNm = "";	
		int nInpos = 0;
		
		@SuppressWarnings("rawtypes")
		Set key = CodeClass.getHashMapServer().keySet();
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = key.iterator(); iterator.hasNext();) {
        	selChk = "";
			sServerId = (String) iterator.next(); 
			logger.debug("sServerId:" + sServerId);
			sServerNm = (String) CodeClass.getHashMapServer().get(sServerId); // 값이 따라 형변환 필요 System.out.println(keyName +" = " +valueName); }
			
			if(first && nInpos == 0) {
				selChk = " selected";
				nInpos = 1;
				logger.debug(sServerNm);
			} else if(value != null && value.length() > 0 ) {
				//logger.debug("bean.getCode:" + bean.getCode());
				if(sServerId.equals(value)) {
					selChk = " selected";
					logger.debug(sServerNm);
					nInpos = 1;
				}
			}
			
			sb.append("<option value='" + sServerId + "'" + selChk + ">");
			sb.append(sServerNm);
			sb.append("</option>");
		}
		resultList.add(sb.toString());

		logger.debug(resultList.toString());
		return resultList.toString();
		
	}

	

	
	/* 코드그룹의 코드목록을 HTML <option> 문자열로 생성 */
	public String getComboBoxByCodeList(String grpCD, String value, boolean first) {
		ArrayList<String> resultList = new ArrayList<>();
		
		StringBuffer sb = new StringBuffer();
		String selChk = "";
		
		String key = (grpCD == null) ? null : grpCD.toUpperCase();
		ArrayList<CodeDetail> codeList = key == null ? null : getHashMap().get(key);
		
		if(codeList != null && codeList.size() > 0 ) {
			int nInposFirst = 0;
			for(CodeDetail bean : codeList) {
				selChk = "";
				if(first)  {
					if(nInposFirst == 0 && bean.getCode().equals(value)) {
						selChk = " selected";
						nInposFirst = 1;
					}
				} else if(value != null && value.length() > 0 ) {
					//logger.debug("bean.getCode:" + bean.getCode());
					if(bean.getCode().equals(value)) {
						selChk = " selected";
					}
				}
				
				sb.append("<option value='" + bean.getCode() + "'" + selChk + ">");
				sb.append(bean.getCodeNm());
				sb.append("</option>");
			}
			resultList.add(sb.toString());
		}
		
		//logger.debug(resultList.toString());
		return resultList.toString();
		
	}	
	
	/* 코드그룹/코드값으로 코드명 반환 */
	public String codeSet(String grpCD, String codeVal) {
		//CodeController Codecontroller = new CodeController();
		ArrayList<CodeDetail> codeList = getHashMap().get(grpCD);
		String returnVal = "";
		
		if(codeList != null && codeList.size() > 0 ) {
			for(CodeDetail bean : codeList) {
				if(bean.getCode().equals(codeVal)) {
					returnVal = bean.getCodeNm();
				}
			}
		}
		return returnVal;
	}
}
