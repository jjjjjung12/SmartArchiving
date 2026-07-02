package com.archiving.auth.dto;

public class MenuListParamDto {
	private String user_id;
	private String user_cd;
	private String sel_cd;
	private String menu_nm;
	private String url;
	private String use_yn;
	private String __rows;
	private String __page;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_cd() {
		return user_cd;
	}

	public void setUser_cd(String user_cd) {
		this.user_cd = user_cd;
	}

	public String getSel_cd() {
		return sel_cd;
	}

	public void setSel_cd(String sel_cd) {
		this.sel_cd = sel_cd;
	}

	public String getMenu_nm() {
		return menu_nm;
	}

	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
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

