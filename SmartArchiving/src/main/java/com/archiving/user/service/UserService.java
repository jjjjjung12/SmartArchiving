package com.archiving.user.service;

import java.util.List;

import com.archiving.user.dto.UserInfoDto;
import com.archiving.user.dto.UserSaveParamDto;

public interface UserService {

	/* 사용자 목록 조회(이름/코드 검색은 Mapper 쿼리에서 처리) */
	List<UserInfoDto> getUserList(String userNm);

	/* 사용자 저장(C:신규, U:수정, D:삭제 및 연관 결재데이터 정리) */
	void saveUser(UserSaveParamDto p) throws Exception;
}
