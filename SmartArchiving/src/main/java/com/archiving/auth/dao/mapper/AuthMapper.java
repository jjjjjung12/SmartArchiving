package com.archiving.auth.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.auth.dto.AuthMenuSimpleDto;
import com.archiving.auth.dto.UserGroupDto;

public interface AuthMapper {
	/* 사용 중(Y)인 사용자 그룹 목록 조회 */
	List<UserGroupDto> selectActiveUserGroups();

	/* 그룹에 미등록된 메뉴 목록 조회 */
	List<AuthMenuSimpleDto> selectMenusNotInGroup(@Param("userGb") String userGb);

	/* 그룹에 등록된 메뉴 목록 조회 */
	List<AuthMenuSimpleDto> selectMenusInGroup(@Param("userGb") String userGb);

	/* 그룹 권한(메뉴) 전체 삭제 */
	int deleteUserAuthByGroup(@Param("userGb") String userGb);

	/* 그룹 권한(메뉴) 1건 추가 */
	int insertUserAuth(@Param("userGb") String userGb, @Param("menuId") String menuId);
}

