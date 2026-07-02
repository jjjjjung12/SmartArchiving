package com.archiving.auth.service;

import java.util.List;

import com.archiving.auth.dto.MenuItemDto;

public interface MenuService {

	/* 사용자별 메뉴 목록 조회(관리자/전체는 전체 메뉴 반환) */
	List<MenuItemDto> getMenus(String userCd, String selCd, String userId);

	/* 관리자 메뉴 관리 화면용 메뉴 검색 */
	List<MenuItemDto> getAdminMenus(String menuNm, String url, String useYn);
}
