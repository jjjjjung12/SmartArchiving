package com.archiving.asset.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.asset.dao.mapper.ServerMapper;
import com.archiving.asset.dto.ServerDto;
import com.archiving.asset.dto.ServerSaveParamDto;
import com.archiving.asset.service.ServerService;

@Service
public class ServerServiceImpl implements ServerService {

	private final ServerMapper serverMapper;

	public ServerServiceImpl(ServerMapper serverMapper) {
		this.serverMapper = serverMapper;
	}

	/* 서버 목록 조회(서버ID/사용여부 조건) */
	@Override
	public List<ServerDto> list(String serverId, String useYn, String searchCol, String searchVal) {
		return serverMapper.selectServerList(serverId, useYn, searchCol, searchVal);
	}

	/* 서버 저장 처리(C:신규, U:수정, D:사용중지) */
	@Override
	@Transactional
	public void save(ServerSaveParamDto p) {
		String crud = p.getCrud();
		if ("C".equalsIgnoreCase(crud)) {
			serverMapper.insertServer(p.getServer_nm(), p.getServer_class_cd(), p.getServer_ip(), p.getServer_desc());
		} else if ("D".equalsIgnoreCase(crud)) {
			serverMapper.softDeleteServer(p.getServer_id());
		} else {
			serverMapper.updateServer(p.getServer_id(), p.getServer_nm(), p.getServer_class_cd(), p.getServer_ip(), p.getServer_desc(), p.getUse_yn());
		}
	}
}
