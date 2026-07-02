package com.archiving.asset.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.archiving.asset.dto.DaemonDto;
import com.archiving.asset.dto.DaemonSaveParamDto;

public interface DaemonService {

	/* 데몬 목록 조회(데몬ID/사용여부 조건) */
	List<DaemonDto> list(String daemonId, String useYn, String searchCol, String searchVal);

	/* 데몬 저장 처리(C/U/D) */
	void save(DaemonSaveParamDto param);

	/* 데몬 START/STOP/RESTART 명령 실행 및 이력 기록 */
	JSONObject executeCommand(DaemonSaveParamDto param);
}
