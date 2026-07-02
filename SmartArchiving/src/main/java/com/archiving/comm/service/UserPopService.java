package com.archiving.comm.service;

import java.util.List;

import com.archiving.comm.dao.mapper.UserPopMapper.UserRow;
import com.archiving.comm.dto.UserListPopParamDto;

public interface UserPopService {

	/* 사용자 선택 팝업용 사용자 목록 조회(사용자코드 조건) */
	List<UserRow> getUsers(UserListPopParamDto p);
}
