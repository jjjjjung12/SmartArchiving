package com.archiving.user.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.user.dto.UserInfoDto;

public interface UserMapper {
	/* 사용자 목록 조회(이름/코드 검색 포함) */
	List<UserInfoDto> selectUserList(@Param("userNm") String userNm);

	/* 사용자 코드 중복/존재 여부 카운트 */
	int countUserCode(@Param("userCd") String userCd);

	/* 사용자 신규 등록 */
	int insertUser(@Param("userCd") String userCd,
		@Param("userNm") String userNm,
		@Param("password") String password,
		@Param("expireDate") String expireDate,
		@Param("telephone") String telephone,
		@Param("email") String email,
		@Param("useYn") String useYn,
		@Param("picture") String picture,
		@Param("brc") String brc,
		@Param("brnm") String brnm,
		@Param("ipAddress") String ipAddress);

	/* 사용자 정보 수정(옵션: 비밀번호/사진 갱신) */
	int updateUser(@Param("userId") String userId,
		@Param("userNm") String userNm,
		@Param("password") String password,
		@Param("updatePassword") boolean updatePassword,
		@Param("expireDate") String expireDate,
		@Param("telephone") String telephone,
		@Param("email") String email,
		@Param("useYn") String useYn,
		@Param("picture") String picture,
		@Param("brc") String brc,
		@Param("brnm") String brnm,
		@Param("ipAddress") String ipAddress);

	/* 사용자 삭제 */
	int deleteUser(@Param("userId") String userId);

	/* 사용자 삭제 시 결재요청 데이터 정리 */
	int deleteApprovalReqOnUserDelete(@Param("userCd") String userCd);

	/* 사용자 삭제 시 결재라인 데이터 정리 */
	int deleteApprovalLineOnUserDelete(@Param("userCd") String userCd);
}

