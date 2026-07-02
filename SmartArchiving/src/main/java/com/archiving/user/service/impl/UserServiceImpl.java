package com.archiving.user.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.user.dao.mapper.UserMapper;
import com.archiving.user.dto.UserInfoDto;
import com.archiving.user.dto.UserSaveParamDto;
import com.archiving.user.service.UserService;
import com.archiving.util.UtilClass;

@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;

	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	/* 사용자 목록 조회(이름/코드 검색은 Mapper 쿼리에서 처리) */
	@Override
	public List<UserInfoDto> getUserList(String userNm) {
		return userMapper.selectUserList(userNm);
	}

	/* 사용자 저장(C:신규, U:수정, D:삭제 및 연관 결재데이터 정리) */
	@Override
	@Transactional
	public void saveUser(UserSaveParamDto p) throws Exception {
		String crud = StringUtils.defaultString(p.getCrud());
		if ("C".equalsIgnoreCase(crud)) {
			int cnt = userMapper.countUserCode(StringUtils.defaultString(p.getUser_cd()));
			if (cnt > 0) {
				throw new IllegalArgumentException(p.getUser_cd() + " 해당 코드가 중복됩니다.");
			}
			userMapper.insertUser(
				StringUtils.defaultString(p.getUser_cd()),
				StringUtils.defaultString(p.getUser_nm()),
				new UtilClass().encryptSHA256(StringUtils.defaultString(p.getPassword())),
				StringUtils.defaultString(p.getExpire_date()),
				StringUtils.defaultString(p.getTelephone()),
				StringUtils.defaultString(p.getEmail()),
				StringUtils.defaultString(p.getUse_yn()),
				StringUtils.defaultString(p.getFilename()),
				StringUtils.defaultString(p.getBrc()),
				StringUtils.defaultString(p.getBrnm()),
				StringUtils.defaultString(p.getIp_address())
			);
			return;
		}

		if ("D".equalsIgnoreCase(crud)) {
			userMapper.deleteUser(StringUtils.defaultString(p.getUser_id()));
			userMapper.deleteApprovalReqOnUserDelete(StringUtils.defaultString(p.getUser_cd()));
			userMapper.deleteApprovalLineOnUserDelete(StringUtils.defaultString(p.getUser_cd()));
			return;
		}

		boolean updatePassword = !StringUtils.equals(
			StringUtils.defaultString(p.getPassword()),
			StringUtils.defaultString(p.getPasswordorg())
		);
		userMapper.updateUser(
			StringUtils.defaultString(p.getUser_id()),
			StringUtils.defaultString(p.getUser_nm()),
			updatePassword ? new UtilClass().encryptSHA256(StringUtils.defaultString(p.getPassword())) : "",
			updatePassword,
			StringUtils.defaultString(p.getExpire_date()),
			StringUtils.defaultString(p.getTelephone()),
			StringUtils.defaultString(p.getEmail()),
			StringUtils.defaultString(p.getUse_yn()),
			StringUtils.defaultString(p.getFilename()),
			StringUtils.defaultString(p.getBrc()),
			StringUtils.defaultString(p.getBrnm()),
			StringUtils.defaultString(p.getIp_address())
		);
	}
}
