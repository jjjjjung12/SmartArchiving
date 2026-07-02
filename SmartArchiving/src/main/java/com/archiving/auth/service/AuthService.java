package com.archiving.auth.service;

import java.util.List;

import com.archiving.auth.dto.AuthMenuSimpleDto;
import com.archiving.auth.dto.UserGroupDto;

public interface AuthService {

	/* 사용 중(Y)인 사용자 그룹 목록 조회 */
	List<UserGroupDto> getActiveGroups();

	/* 그룹에 미등록된 메뉴 목록 조회 */
	List<AuthMenuSimpleDto> getMenusNotInGroup(String userGb);

	/* 그룹에 등록된 메뉴 목록 조회 */
	List<AuthMenuSimpleDto> getMenusInGroup(String userGb);

	/* 그룹 권한(메뉴) 교체(기존 삭제 후 전달된 메뉴로 재등록) */
	void replaceGroupAuth(String userGb, List<String> menuIds);
}
