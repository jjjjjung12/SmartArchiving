package com.archiving.user.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.user.dto.UserGroupRowDto;

public interface UserGroupMapper {

	/* 사용자 그룹 목록 조회(그룹ID/그룹명 조건) */
	List<UserGroupRowDto> selectGroupsGbA(
		@Param("userGroupId") String userGroupId,
		@Param("userGroupNm") String userGroupNm);

	/* 그룹에 아직 배정되지 않은 사용자 목록 조회 */
	List<UserGroupRowDto> selectUsersAvailableGbB();

	/* 특정 그룹에 소속된 사용자 목록 조회 */
	List<UserGroupRowDto> selectMembersOfGroupGbC(@Param("userGroupId") String userGroupId);

	/* 그룹ID 기준으로 멤버 전체 삭제 */
	int deleteMembersByGroupId(@Param("userGrpId") String userGrpId);

	/* 사용자-그룹 멤버십 추가 */
	int insertMember(@Param("userId") String userId, @Param("userGrpId") String userGrpId);

	/* 사용자 그룹 신규 등록 */
	int insertUserGroup(@Param("userGrpId") String userGrpId, @Param("userGrpNm") String userGrpNm);

	/* 사용자 그룹 정보 수정(명/설명/사용여부) */
	int updateUserGroup(@Param("userGrpId") String userGrpId,
		@Param("userGrpNm") String userGrpNm,
		@Param("description") String description,
		@Param("useYn") String useYn);
}
