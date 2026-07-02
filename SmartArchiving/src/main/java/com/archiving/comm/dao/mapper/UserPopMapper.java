package com.archiving.comm.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserPopMapper {
	/* 사용자 선택 팝업용 사용자 목록 조회(사용자코드 조건) */
	List<UserRow> selectUserList(@Param("userCd") String userCd);

	class UserRow {
		private String userCd;
		private String userNm;
		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getUserNm() { return userNm; }
		public void setUserNm(String userNm) { this.userNm = userNm; }
	}
}

