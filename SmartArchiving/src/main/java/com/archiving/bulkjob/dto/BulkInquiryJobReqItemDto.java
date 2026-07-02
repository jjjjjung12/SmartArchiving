package com.archiving.bulkjob.dto;

public class BulkInquiryJobReqItemDto {
	private String START_DATE;
	private String END_DATE;
	private String ACCOUNT_NO;
	private String USER_ID;
	private String CUST_REG_NO;
	private String TEL_NO;
	private String USER_IP;
	private String ORG_FILE_NM;
	private String U_FILE_NM;

	public String getSTART_DATE() { return START_DATE; }
	public void setSTART_DATE(String sTART_DATE) { START_DATE = sTART_DATE; }
	public String getEND_DATE() { return END_DATE; }
	public void setEND_DATE(String eND_DATE) { END_DATE = eND_DATE; }
	public String getACCOUNT_NO() { return ACCOUNT_NO; }
	public void setACCOUNT_NO(String aCCOUNT_NO) { ACCOUNT_NO = aCCOUNT_NO; }
	public String getUSER_ID() { return USER_ID; }
	public void setUSER_ID(String uSER_ID) { USER_ID = uSER_ID; }
	public String getCUST_REG_NO() { return CUST_REG_NO; }
	public void setCUST_REG_NO(String cUST_REG_NO) { CUST_REG_NO = cUST_REG_NO; }
	public String getTEL_NO() { return TEL_NO; }
	public void setTEL_NO(String tEL_NO) { TEL_NO = tEL_NO; }
	public String getUSER_IP() { return USER_IP; }
	public void setUSER_IP(String uSER_IP) { USER_IP = uSER_IP; }
	public String getORG_FILE_NM() { return ORG_FILE_NM; }
	public void setORG_FILE_NM(String oRG_FILE_NM) { ORG_FILE_NM = oRG_FILE_NM; }
	public String getU_FILE_NM() { return U_FILE_NM; }
	public void setU_FILE_NM(String u_FILE_NM) { U_FILE_NM = u_FILE_NM; }
}

