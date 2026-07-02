package com.archiving.approve.service;

import com.archiving.approve.dto.UserApproveProcParamDto;

public interface ApproveProcService {

	/* 결재 처리 실행(결재라인 업데이트, 승인 시 권한/유저/마스킹/벌크 작업 반영) */
	void process(UserApproveProcParamDto p) throws Exception;
}
