package com.archiving.user.service;

import java.util.List;

import com.archiving.user.dto.UserGroupListParamDto;
import com.archiving.user.dto.UserGroupMemberItemDto;
import com.archiving.user.dto.UserGroupRowDto;
import com.archiving.user.dto.UserGroupSaveParamDto;

public interface UserGroupService {

	/* 사용자 그룹 관련 목록 조회(__gb=A:그룹, B:미배정 사용자, C:그룹 멤버) */
	List<UserGroupRowDto> list(UserGroupListParamDto p);

	/* 그룹 멤버 교체(기존 삭제 후 전달된 멤버로 재등록) */
	void replaceGroupMembers(String userGrpId, List<UserGroupMemberItemDto> members);

	/* 사용자 그룹 저장(C:신규, U:수정) */
	void saveUserGroup(UserGroupSaveParamDto p);
}
