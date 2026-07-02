package com.archiving.comm.dto;

public class DownloadReqSaveParamDto {
	private String user_cd;
	private String user_nm;
	private String down_cd;
	private String req_nm;
	private String req_num;
	private String req_reason;
	private String reg_date;
	private String page_name;
	private String where;

	public String getUser_cd() { return user_cd; }
	public void setUser_cd(String user_cd) { this.user_cd = user_cd; }
	public String getUser_nm() { return user_nm; }
	public void setUser_nm(String user_nm) { this.user_nm = user_nm; }
	public String getDown_cd() { return down_cd; }
	public void setDown_cd(String down_cd) { this.down_cd = down_cd; }
	public String getReq_nm() { return req_nm; }
	public void setReq_nm(String req_nm) { this.req_nm = req_nm; }
	public String getReq_num() { return req_num; }
	public void setReq_num(String req_num) { this.req_num = req_num; }
	public String getReq_reason() { return req_reason; }
	public void setReq_reason(String req_reason) { this.req_reason = req_reason; }
	public String getReg_date() { return reg_date; }
	public void setReg_date(String reg_date) { this.reg_date = reg_date; }
	public String getPage_name() { return page_name; }
	public void setPage_name(String page_name) { this.page_name = page_name; }
	public String getWhere() { return where; }
	public void setWhere(String where) { this.where = where; }
}

