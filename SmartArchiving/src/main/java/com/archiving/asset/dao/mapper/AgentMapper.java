package com.archiving.asset.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.asset.dto.AgentDto;
import com.archiving.asset.dto.AgentSaveParamDto;

public interface AgentMapper {
	/* 에이전트 목록 조회(서버/에이전트ID/사용여부 조건) */
	List<AgentDto> selectAgentList(
			@Param("serverId") String serverId,
			@Param("agentId") String agentId,
			@Param("useYn") String useYn
	);

	/* 에이전트 신규 등록 */
	int insertAgent(AgentSaveParamDto param);

	/* 에이전트 정보 수정 */
	int updateAgent(AgentSaveParamDto param);

	/* 에이전트 삭제 */
	int deleteAgent(@Param("serverId") String serverId, @Param("agentId") String agentId);
}

