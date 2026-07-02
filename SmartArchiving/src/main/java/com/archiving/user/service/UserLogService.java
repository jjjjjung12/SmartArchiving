package com.archiving.user.service;

import java.util.List;

import com.archiving.user.dto.UserLogListParamDto;
import com.archiving.user.dto.UserLogRowDto;

public interface UserLogService {

	/* 사용자 로그 총 건수 조회(조건 검색) */
	int count(UserLogListParamDto p);

	/* 사용자 로그 목록 조회(조건 검색/페이징) */
	List<UserLogRowDto> list(UserLogListParamDto p, int limit, int offset);
}
