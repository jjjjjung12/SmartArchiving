package com.archiving.auth.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.auth.dao.mapper.AuthMapper;
import com.archiving.auth.dto.AuthMenuSimpleDto;
import com.archiving.auth.dto.UserGroupDto;
import com.archiving.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthMapper authMapper;

	public AuthServiceImpl(AuthMapper authMapper) {
		this.authMapper = authMapper;
	}

	/* 사용 중(Y)인 사용자 그룹 목록 조회 */
	@Override
	public List<UserGroupDto> getActiveGroups() {
		return authMapper.selectActiveUserGroups();
	}

	/* 그룹에 미등록된 메뉴 목록 조회 */
	@Override
	public List<AuthMenuSimpleDto> getMenusNotInGroup(String userGb) {
		return authMapper.selectMenusNotInGroup(userGb);
	}

	/* 그룹에 등록된 메뉴 목록 조회 */
	@Override
	public List<AuthMenuSimpleDto> getMenusInGroup(String userGb) {
		return authMapper.selectMenusInGroup(userGb);
	}

	/* 그룹 권한(메뉴) 교체(기존 삭제 후 전달된 메뉴로 재등록) */
	@Override
	@Transactional
	public void replaceGroupAuth(String userGb, List<String> menuIds) {
		authMapper.deleteUserAuthByGroup(userGb);
		if (menuIds == null) return;
		for (String menuId : menuIds) {
			authMapper.insertUserAuth(userGb, menuId);
		}
	}
}
