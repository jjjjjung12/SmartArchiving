package com.archiving.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.archiving.user.dao.mapper.UserLogMapper;
import com.archiving.user.dto.UserLogListParamDto;
import com.archiving.user.dto.UserLogRowDto;
import com.archiving.user.service.UserLogService;

@Service
public class UserLogServiceImpl implements UserLogService {

	private final UserLogMapper userLogMapper;

	public UserLogServiceImpl(UserLogMapper userLogMapper) {
		this.userLogMapper = userLogMapper;
	}

	/* 사용자 로그 총 건수 조회(조건 검색) */
	@Override
	public int count(UserLogListParamDto p) {
		return userLogMapper.countUserLogs(
			trimToNull(p.getUser_cd()),
			trimToNull(p.getUser_nm()),
			trimToNull(p.getMenu_nm()),
			trimToNull(p.getStart_dt()),
			trimToNull(p.getEnd_dt()));
	}

	/* 사용자 로그 목록 조회(조건 검색/페이징) */
	@Override
	public List<UserLogRowDto> list(UserLogListParamDto p, int limit, int offset) {
		return userLogMapper.selectUserLogs(
			trimToNull(p.getUser_cd()),
			trimToNull(p.getUser_nm()),
			trimToNull(p.getMenu_nm()),
			trimToNull(p.getStart_dt()),
			trimToNull(p.getEnd_dt()),
			limit,
			offset);
	}

	/* 입력 문자열 트림 후 빈값은 null 처리 */
	private static String trimToNull(String s) {
		if (s == null) {
			return null;
		}
		String t = s.trim();
		return t.isEmpty() ? null : t;
	}
}
