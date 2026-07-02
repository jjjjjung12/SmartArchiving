package com.archiving.auth.service;

import javax.servlet.http.HttpServletRequest;

import com.archiving.auth.dto.UserAuthApplySubmitDto;

public interface UserAuthApplyService {

	/* 사용자 권한신청 화면 초기 데이터 구성(기존 신청/반려 여부 및 IP/만료일 검증 포함) */
	String getApplyInfo(String userCd, HttpServletRequest request) throws Exception;

	/* 사용자 권한 신청 제출(결재요청/결재라인 insert) */
	String submit(UserAuthApplySubmitDto dto) throws Exception;
}
