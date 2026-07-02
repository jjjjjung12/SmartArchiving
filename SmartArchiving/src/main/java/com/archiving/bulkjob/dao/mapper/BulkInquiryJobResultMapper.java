package com.archiving.bulkjob.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BulkInquiryJobResultMapper {
	/* 메뉴 전체 조회(옵션: 사용중 메뉴만) */
	List<MenuRow> selectAllMenus(@Param("onlyUseYn") boolean onlyUseYn);

	/* 사용자별 권한 메뉴 조회 */
	List<MenuRow> selectUserMenus(@Param("userId") String userId);

	class MenuRow {
		private String menuCd;
		private String menuNm;
		private String menuUrl;
		private String menuOrder;
		private String menuId;
		private String menuDesc;
		private String upMenuId;
		private String useYn;
		private String menuYn;
		private String iconClassId;
		public String getMenuCd() { return menuCd; }
		public void setMenuCd(String menuCd) { this.menuCd = menuCd; }
		public String getMenuNm() { return menuNm; }
		public void setMenuNm(String menuNm) { this.menuNm = menuNm; }
		public String getMenuUrl() { return menuUrl; }
		public void setMenuUrl(String menuUrl) { this.menuUrl = menuUrl; }
		public String getMenuOrder() { return menuOrder; }
		public void setMenuOrder(String menuOrder) { this.menuOrder = menuOrder; }
		public String getMenuId() { return menuId; }
		public void setMenuId(String menuId) { this.menuId = menuId; }
		public String getMenuDesc() { return menuDesc; }
		public void setMenuDesc(String menuDesc) { this.menuDesc = menuDesc; }
		public String getUpMenuId() { return upMenuId; }
		public void setUpMenuId(String upMenuId) { this.upMenuId = upMenuId; }
		public String getUseYn() { return useYn; }
		public void setUseYn(String useYn) { this.useYn = useYn; }
		public String getMenuYn() { return menuYn; }
		public void setMenuYn(String menuYn) { this.menuYn = menuYn; }
		public String getIconClassId() { return iconClassId; }
		public void setIconClassId(String iconClassId) { this.iconClassId = iconClassId; }
	}
}

