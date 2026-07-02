package com.archiving.comm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.archiving.comm.dao.mapper.UserPopMapper;
import com.archiving.comm.dao.mapper.UserPopMapper.UserRow;
import com.archiving.comm.dto.UserListPopParamDto;
import com.archiving.comm.service.UserPopService;

@Service
public class UserPopServiceImpl implements UserPopService {
	private final UserPopMapper userPopMapper;

	public UserPopServiceImpl(UserPopMapper userPopMapper) {
		this.userPopMapper = userPopMapper;
	}

	/* 사용자 선택 팝업용 사용자 목록 조회(사용자코드 조건) */
	@Override
	public List<UserRow> getUsers(UserListPopParamDto p) {
		String userCd = p == null ? null : p.get__user_cd();
		return userPopMapper.selectUserList(StringUtils.defaultString(userCd));
	}
}
