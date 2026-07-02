package com.archiving.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Params from {@code userLogList.js} ({@code param} JSON POST body).
 */
public class UserLogListParamDto {

	private String user_cd;
	private String user_nm;
	private String menu_nm;
	private String start_dt;
	private String end_dt;

	@JsonProperty("__rows")
	private String __rows;

	@JsonProperty("__page")
	private String __page;

	public String getUser_cd() {
		return user_cd;
	}

	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public String getMenu_nm() {
		return menu_nm;
	}

	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}

	public String getStart_dt() {
		return start_dt;
	}

	public void setStart_dt(String start_dt) {
		this.start_dt = start_dt;
	}

	public String getEnd_dt() {
		return end_dt;
	}

	public void setEnd_dt(String end_dt) {
		this.end_dt = end_dt;
	}

	public String get__rows() {
		return __rows;
	}

	public void set__rows(String __rows) {
		this.__rows = __rows;
	}

	public String get__page() {
		return __page;
	}

	public void set__page(String __page) {
		this.__page = __page;
	}
}
