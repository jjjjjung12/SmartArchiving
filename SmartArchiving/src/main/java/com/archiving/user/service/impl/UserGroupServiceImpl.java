package com.archiving.user.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.user.dao.mapper.UserGroupMapper;
import com.archiving.user.dto.UserGroupListParamDto;
import com.archiving.user.dto.UserGroupMemberItemDto;
import com.archiving.user.dto.UserGroupRowDto;
import com.archiving.user.dto.UserGroupSaveParamDto;
import com.archiving.user.service.UserGroupService;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	private final UserGroupMapper userGroupMapper;

	public UserGroupServiceImpl(UserGroupMapper userGroupMapper) {
		this.userGroupMapper = userGroupMapper;
	}

	/* 사용자 그룹 관련 목록 조회(__gb=A:그룹, B:미배정 사용자, C:그룹 멤버) */
	@Override
	public List<UserGroupRowDto> list(UserGroupListParamDto p) {
		String gb = p == null ? "" : StringUtils.defaultString(p.get__gb(), "");
		String userGroupId = p == null ? "" : StringUtils.defaultString(p.get__userGroup_id(), "");
		String userGroupNm = p == null ? "" : StringUtils.defaultString(p.get__userGroup_nm(), "");

		if ("A".equalsIgnoreCase(gb)) {
			return userGroupMapper.selectGroupsGbA(userGroupId, userGroupNm);
		}
		if ("B".equalsIgnoreCase(gb)) {
			return userGroupMapper.selectUsersAvailableGbB();
		}
		if ("C".equalsIgnoreCase(gb)) {
			return userGroupMapper.selectMembersOfGroupGbC(userGroupId);
		}
		throw new IllegalArgumentException("Unsupported __gb for user group list: " + gb);
	}

	/* 그룹 멤버 교체(기존 삭제 후 전달된 멤버로 재등록) */
	@Override
	@Transactional
	public void replaceGroupMembers(String userGrpId, List<UserGroupMemberItemDto> members) {
		if (StringUtils.isBlank(userGrpId)) {
			throw new IllegalArgumentException("user_grp_id is required");
		}
		userGroupMapper.deleteMembersByGroupId(userGrpId.trim());
		if (members == null || members.isEmpty()) {
			return;
		}
		for (UserGroupMemberItemDto m : members) {
			if (m == null || StringUtils.isBlank(m.getUser_id())) {
				continue;
			}
			userGroupMapper.insertMember(m.getUser_id().trim(), userGrpId.trim());
		}
	}

	/* 사용자 그룹 저장(C:신규, U:수정) */
	@Override
	@Transactional
	public void saveUserGroup(UserGroupSaveParamDto p) {
		String crud = StringUtils.defaultString(p.getCrud());
		if ("C".equalsIgnoreCase(crud)) {
			userGroupMapper.insertUserGroup(
				StringUtils.defaultString(p.getUser_grp_id()),
				StringUtils.defaultString(p.getUser_grp_nm())
			);
			return;
		}
		userGroupMapper.updateUserGroup(
			StringUtils.defaultString(p.getUser_grp_id()),
			StringUtils.defaultString(p.getUser_grp_nm()),
			StringUtils.defaultString(p.getDescription()),
			StringUtils.defaultString(p.getUse_yn())
		);
	}
}
