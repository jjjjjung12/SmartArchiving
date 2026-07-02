package com.archiving.approve.dto;

public class DownloadReqListParamDto {
	private String start_dt;
	private String end_dt;
	private String user_nm;
	private String down_cd;
	private String req_num;
	private String bulk_yn;
	private String __rows;
	private String __page;

	public String getStart_dt() { return start_dt; }
	public void setStart_dt(String start_dt) { this.start_dt = start_dt; }
	public String getEnd_dt() { return end_dt; }
	public void setEnd_dt(String end_dt) { this.end_dt = end_dt; }
	public String getUser_nm() { return user_nm; }
	public void setUser_nm(String user_nm) { this.user_nm = user_nm; }
	public String getDown_cd() { return down_cd; }
	public void setDown_cd(String down_cd) { this.down_cd = down_cd; }
	public String getReq_num() { return req_num; }
	public void setReq_num(String req_num) { this.req_num = req_num; }
	public String getBulk_yn() { return bulk_yn; }
	public void setBulk_yn(String bulk_yn) { this.bulk_yn = bulk_yn; }
	public String get__rows() { return __rows; }
	public void set__rows(String __rows) { this.__rows = __rows; }
	public String get__page() { return __page; }
	public void set__page(String __page) { this.__page = __page; }
}

