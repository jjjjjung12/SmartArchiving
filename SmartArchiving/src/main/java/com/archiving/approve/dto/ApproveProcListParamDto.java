package com.archiving.approve.dto;

public class ApproveProcListParamDto {
	private String __rows;
	private String __page;
	private String user_cd;
	private String user_nm;
	private String team_nm;

	public String get__rows() { return __rows; }
	public void set__rows(String __rows) { this.__rows = __rows; }
	public String get__page() { return __page; }
	public void set__page(String __page) { this.__page = __page; }
	public String getUser_cd() { return user_cd; }
	public void setUser_cd(String user_cd) { this.user_cd = user_cd; }
	public String getUser_nm() { return user_nm; }
	public void setUser_nm(String user_nm) { this.user_nm = user_nm; }
	public String getTeam_nm() { return team_nm; }
	public void setTeam_nm(String team_nm) { this.team_nm = team_nm; }
}

