package com.archiving.approve.dto;

public class ApproveStatSearchParamDto {
	private String start_tr_ymd;
	private String end_tr_ymd;
	private String user_cd;
	private String __rows;
	private String __page;

	// pop
	private String __user_cd;

	public String getStart_tr_ymd() { return start_tr_ymd; }
	public void setStart_tr_ymd(String start_tr_ymd) { this.start_tr_ymd = start_tr_ymd; }
	public String getEnd_tr_ymd() { return end_tr_ymd; }
	public void setEnd_tr_ymd(String end_tr_ymd) { this.end_tr_ymd = end_tr_ymd; }
	public String getUser_cd() { return user_cd; }
	public void setUser_cd(String user_cd) { this.user_cd = user_cd; }
	public String get__rows() { return __rows; }
	public void set__rows(String __rows) { this.__rows = __rows; }
	public String get__page() { return __page; }
	public void set__page(String __page) { this.__page = __page; }
	public String get__user_cd() { return __user_cd; }
	public void set__user_cd(String __user_cd) { this.__user_cd = __user_cd; }
}

