package com.archiving.asset.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.asset.dao.mapper.AgentMapper;
import com.archiving.asset.dto.AgentDto;
import com.archiving.asset.dto.AgentSaveParamDto;
import com.archiving.asset.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService {
	private final AgentMapper agentMapper;

	public AgentServiceImpl(AgentMapper agentMapper) {
		this.agentMapper = agentMapper;
	}

	/* 에이전트 목록 조회(서버/에이전트ID/사용여부 조건) */
	@Override
	public List<AgentDto> list(String serverId, String agentId, String useYn) {
		return agentMapper.selectAgentList(serverId, agentId, useYn);
	}

	/* 에이전트 저장 처리(C/U/D) */
	@Override
	@Transactional
	public void save(AgentSaveParamDto param) {
		if (param == null || param.getCrud() == null) {
			throw new IllegalArgumentException("crud is required");
		}
		switch (param.getCrud()) {
			case "C":
				agentMapper.insertAgent(param);
				break;
			case "U":
				agentMapper.updateAgent(param);
				break;
			case "D":
				agentMapper.deleteAgent(param.getServer_id(), param.getAgent_id());
				break;
			default:
				throw new IllegalArgumentException("invalid crud: " + param.getCrud());
		}
	}
}
