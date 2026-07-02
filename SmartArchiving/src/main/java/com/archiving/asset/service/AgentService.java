package com.archiving.asset.service;

import java.util.List;

import com.archiving.asset.dto.AgentDto;
import com.archiving.asset.dto.AgentSaveParamDto;

public interface AgentService {

	/* 에이전트 목록 조회(서버/에이전트ID/사용여부 조건) */
	List<AgentDto> list(String serverId, String agentId, String useYn);

	/* 에이전트 저장 처리(C/U/D) */
	void save(AgentSaveParamDto param);
}
