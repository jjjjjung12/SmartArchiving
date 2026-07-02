package com.archiving.asset.service;

import java.util.List;

import com.archiving.asset.dto.ServerDto;
import com.archiving.asset.dto.ServerSaveParamDto;

public interface ServerService {

	/* 서버 목록 조회(서버ID/사용여부/검색 조건) */
	List<ServerDto> list(String serverId, String useYn, String searchCol, String searchVal);

	/* 서버 저장 처리(C:신규, U:수정, D:사용중지) */
	void save(ServerSaveParamDto p);
}
