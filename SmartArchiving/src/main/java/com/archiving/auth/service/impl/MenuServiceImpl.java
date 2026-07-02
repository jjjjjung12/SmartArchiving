package com.archiving.auth.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.archiving.auth.dao.mapper.AuthMenuMapper;
import com.archiving.auth.dto.MenuItemDto;
import com.archiving.auth.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	private final AuthMenuMapper authMenuMapper;

	public MenuServiceImpl(AuthMenuMapper authMenuMapper) {
		this.authMenuMapper = authMenuMapper;
	}

	/* 사용자별 메뉴 목록 조회(관리자/전체는 전체 메뉴 반환) */
	@Override
	public List<MenuItemDto> getMenus(String userCd, String selCd, String userId) {
		if ("ALL__".equalsIgnoreCase(userCd) || "admin".equalsIgnoreCase(userCd)) {
			return authMenuMapper.selectMenusForAll(selCd);
		}
		return authMenuMapper.selectMenusForUser(userId);
	}

	/* 관리자 메뉴 관리 화면용 메뉴 검색 */
	@Override
	public List<MenuItemDto> getAdminMenus(String menuNm, String url, String useYn) {
		return authMenuMapper.selectMenusForAdminSearch(menuNm, url, useYn);
	}
}
