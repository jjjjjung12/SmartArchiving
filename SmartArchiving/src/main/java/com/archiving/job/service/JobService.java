package com.archiving.job.service;

import java.util.List;
import java.util.Map;

import com.archiving.job.dto.ConvJobSaveParamDto;
import com.archiving.job.dto.DbJobSaveParamDto;
import com.archiving.job.dto.LoadJobSaveParamDto;

public interface JobService {

	// ===== list =====
	/* 로드 잡 목록 조회 */
	List<Map<String, Object>> loadJobList(String serverId, String jobId, String jobNm, String useYn);

	/* 변환(Conv) 잡 목록 조회 */
	List<Map<String, Object>> convJobList(String jobId, String jobNm, String useYn);

	/* DB 잡 목록 조회(데몬ID 기준) */
	List<Map<String, Object>> dbJobList(int daemonId, String jobId, String jobNm, String useYn);

	// ===== history =====
	/* 로드 잡 이력 조회 */
	List<Map<String, Object>> loadJobHistory(String serverId, String jobId, String jobNm, String jobTmFrom, String jobTmTo, String stat);

	/* 잡 이력 조회(TB_JOB_HISTORY) */
	List<Map<String, Object>> jobHistory(String serverId, String jobId, String jobNm, String jobTmFrom, String jobTmTo, String stat);

	/* DB 잡 이력 조회 */
	List<Map<String, Object>> dbJobHistory(int daemonId, String jobId, String jobNm, String jobTmFrom, String jobTmTo);

	/* Export 일자별 이력 조회 */
	List<Map<String, Object>> exportDateHistory(String jobDate);

	/* 변환(Conv) 잡 이력 조회 */
	List<Map<String, Object>> convJobHistory(String jobId, String jobNm, String infoFileNm, String jobTmFrom, String jobTmTo);

	// ===== monitor =====
	/* 로드 잡 모니터링 조회(A/B/C) */
	List<Map<String, Object>> loadMonitor(String stat, String jobTmFrom, String jobTmTo);

	/* TB_JOB_HISTORY 기반 모니터링 조회(A/B/C) */
	List<Map<String, Object>> monitor2back(String stat, String jobTmFrom, String jobTmTo);

	// ===== save =====
	/* 로드 잡 저장(C/U/D) */
	int saveLoadJob(LoadJobSaveParamDto p);

	/* 변환(Conv) 잡 저장(C/U/D) */
	int saveConvJob(ConvJobSaveParamDto p);

	/* DB 잡 저장(C/U/D) */
	int saveDbJob(DbJobSaveParamDto p);
}
