package com.archiving.auth.dto;

public class AuthMenuSimpleDto {
	private String menuId;
	private String menuCd;
	private String menuNm;
	private String menuUrl;
	private String menuOrder;

	public String getMenuId() { return menuId; }
	public void setMenuId(String menuId) { this.menuId = menuId; }
	public String getMenuCd() { return menuCd; }
	public void setMenuCd(String menuCd) { this.menuCd = menuCd; }
	public String getMenuNm() { return menuNm; }
	public void setMenuNm(String menuNm) { this.menuNm = menuNm; }
	public String getMenuUrl() { return menuUrl; }
	public void setMenuUrl(String menuUrl) { this.menuUrl = menuUrl; }
	public String getMenuOrder() { return menuOrder; }
	public void setMenuOrder(String menuOrder) { this.menuOrder = menuOrder; }
}

