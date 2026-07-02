package com.archiving.asset.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.asset.dao.mapper.DaemonMapper;
import com.archiving.asset.dto.DaemonDto;
import com.archiving.asset.dto.DaemonSaveParamDto;
import com.archiving.asset.service.DaemonService;
import com.archiving.util.UtilClass;

@Service
public class DaemonServiceImpl implements DaemonService {
	private final DaemonMapper daemonMapper;

	public DaemonServiceImpl(DaemonMapper daemonMapper) {
		this.daemonMapper = daemonMapper;
	}

	/* 데몬 목록 조회(데몬ID/사용여부 조건) */
	@Override
	public List<DaemonDto> list(String daemonId, String useYn, String searchCol, String searchVal) {
		return daemonMapper.selectDaemonList(daemonId, useYn, searchCol, searchVal);
	}

	/* 데몬 저장 처리(C/U/D) */
	@Override
	@Transactional
	public void save(DaemonSaveParamDto param) {
		if (param == null || param.getCrud() == null) {
			throw new IllegalArgumentException("crud is required");
		}
		switch (param.getCrud()) {
			case "C":
				daemonMapper.insertDaemon(param);
				break;
			case "U":
				daemonMapper.updateDaemon(param);
				break;
			case "D":
				daemonMapper.deleteDaemon(param.getDaemon_id());
				break;
			default:
				throw new IllegalArgumentException("invalid crud: " + param.getCrud());
		}
	}

	/* 데몬 START/STOP/RESTART 명령 실행 및 이력 기록 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public JSONObject executeCommand(DaemonSaveParamDto param) {
		if (param == null || StringUtils.isBlank(param.getCrud())) {
			throw new IllegalArgumentException("crud is required");
		}
		if (StringUtils.isBlank(param.getDaemon_id())) {
			throw new IllegalArgumentException("daemon_id is required");
		}

		String crud = StringUtils.defaultString(param.getCrud()).toUpperCase();
		String daemonId = StringUtils.defaultString(param.getDaemon_id());
		String daemonNm = StringUtils.defaultString(param.getDaemon_nm());
		String daemonCd = StringUtils.defaultString(param.getDaemon_cd());
		String userId = StringUtils.defaultString(param.getUser_id());
		String startPath = StringUtils.defaultString(param.getDaemon_start_path());
		String stopPath = StringUtils.defaultString(param.getDaemon_stop_path());

		UtilClass utilClass = new UtilClass();

		JSONObject jsonobj = new JSONObject();
		JSONArray seriesArray = new JSONArray();
		JSONObject datas = new JSONObject();

		switch (crud) {
			case "START":
				if (utilClass.getProcInfo(daemonCd)) {
					datas.put("START_RST", daemonNm + "이미 프로세스가 존재합니다.");
				} else {
					daemonMapper.updateDaemonCommand(daemonId, "START");
					daemonMapper.insertDaemonHist(daemonId, daemonNm, userId, "START");
					utilClass.commandExecute(startPath);
					datas.put("START_RST", daemonNm + "시작 완료");
				}
				break;
			case "STOP":
				if (utilClass.getProcInfo(daemonCd)) {
					daemonMapper.updateDaemonCommand(daemonId, "STOP");
					daemonMapper.insertDaemonHist(daemonId, daemonNm, userId, "STOP");
					utilClass.commandExecute(stopPath);
					datas.put("STOP_RST", daemonNm + "종료시까지 기다려주세요.");
				} else {
					datas.put("STOP_RST", daemonNm + "이미 종료되었습니다.");
				}
				break;
			case "RESTART":
				if (utilClass.getProcInfo(daemonCd)) {
					daemonMapper.updateDaemonCommand(daemonId, "STOP");
					daemonMapper.insertDaemonHist(daemonId, daemonNm, userId, "STOP");
					utilClass.commandExecute(stopPath);
				}
				daemonMapper.updateDaemonCommand(daemonId, "START");
				daemonMapper.insertDaemonHist(daemonId, daemonNm, userId, "START");
				utilClass.commandExecute(startPath);
				datas.put("RESTART_RST", daemonNm + "재시작 요청 완료");
				break;
			default:
				throw new IllegalArgumentException("invalid crud: " + param.getCrud());
		}

		seriesArray.add(datas);
		jsonobj.put("rows", seriesArray);
		jsonobj.put("result", "OK");
		jsonobj.put("genKey", 0);
		return jsonobj;
	}
}
