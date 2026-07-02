package com.archiving.user.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.user.dto.UserLogRowDto;

public interface UserLogMapper {

	/* 사용자 로그 총 건수 조회(조건 검색) */
	int countUserLogs(
		@Param("userCd") String userCd,
		@Param("userNm") String userNm,
		@Param("menuNm") String menuNm,
		@Param("startDt") String startDt,
		@Param("endDt") String endDt);

	/* 사용자 로그 목록 조회(조건 검색/페이징) */
	List<UserLogRowDto> selectUserLogs(
		@Param("userCd") String userCd,
		@Param("userNm") String userNm,
		@Param("menuNm") String menuNm,
		@Param("startDt") String startDt,
		@Param("endDt") String endDt,
		@Param("limit") int limit,
		@Param("offset") int offset);
}
