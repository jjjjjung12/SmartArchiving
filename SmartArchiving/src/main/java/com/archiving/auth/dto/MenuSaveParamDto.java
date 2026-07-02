package com.archiving.auth.dto;

import java.util.List;

public class MenuSaveParamDto {
	private String crud;
	private String menu_id;
	private List<MenuIdWrapper> menu_id_list;

	private String menu_nm;
	private String menu_cd;
	private String menu_url;
	private String menu_desc;
	private String menu_order;
	private String use_yn;
	private String icon_class_id;

	// for DS payload compatibility: "menu_id" can be array of objects
	public static class MenuIdWrapper {
		private String menu_id;
		public String getMenu_id() { return menu_id; }
		public void setMenu_id(String menu_id) { this.menu_id = menu_id; }
	}

	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
	public String getMenu_id() { return menu_id; }
	public void setMenu_id(String menu_id) { this.menu_id = menu_id; }
	public List<MenuIdWrapper> getMenu_id_list() { return menu_id_list; }
	public void setMenu_id_list(List<MenuIdWrapper> menu_id_list) { this.menu_id_list = menu_id_list; }
	public String getMenu_nm() { return menu_nm; }
	public void setMenu_nm(String menu_nm) { this.menu_nm = menu_nm; }
	public String getMenu_cd() { return menu_cd; }
	public void setMenu_cd(String menu_cd) { this.menu_cd = menu_cd; }
	public String getMenu_url() { return menu_url; }
	public void setMenu_url(String menu_url) { this.menu_url = menu_url; }
	public String getMenu_desc() { return menu_desc; }
	public void setMenu_desc(String menu_desc) { this.menu_desc = menu_desc; }
	public String getMenu_order() { return menu_order; }
	public void setMenu_order(String menu_order) { this.menu_order = menu_order; }
	public String getUse_yn() { return use_yn; }
	public void setUse_yn(String use_yn) { this.use_yn = use_yn; }
	public String getIcon_class_id() { return icon_class_id; }
	public void setIcon_class_id(String icon_class_id) { this.icon_class_id = icon_class_id; }
}

