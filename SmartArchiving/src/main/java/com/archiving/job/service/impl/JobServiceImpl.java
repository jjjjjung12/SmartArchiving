package com.archiving.job.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.job.dao.mapper.JobMapper;
import com.archiving.job.dto.ConvJobSaveParamDto;
import com.archiving.job.dto.DbJobSaveParamDto;
import com.archiving.job.dto.LoadJobSaveParamDto;
import com.archiving.job.service.JobService;

@Service
public class JobServiceImpl implements JobService {
	private final JobMapper mapper;

	public JobServiceImpl(JobMapper mapper) {
		this.mapper = mapper;
	}

	// ===== list =====
	/* 로드 잡 목록 조회 */
	@Override
	public List<Map<String, Object>> loadJobList(String serverId, String jobId, String jobNm, String useYn) {
		return mapper.selectLoadJobList(serverId, jobId, jobNm, useYn);
	}

	/* 변환(Conv) 잡 목록 조회 */
	@Override
	public List<Map<String, Object>> convJobList(String jobId, String jobNm, String useYn) {
		return mapper.selectConvJobList(jobId, jobNm, useYn);
	}

	/* DB 잡 목록 조회(데몬ID 기준) */
	@Override
	public List<Map<String, Object>> dbJobList(int daemonId, String jobId, String jobNm, String useYn) {
		return mapper.selectDbJobList(daemonId, jobId, jobNm, useYn);
	}

	// ===== history =====
	/* 로드 잡 이력 조회 */
	@Override
	public List<Map<String, Object>> loadJobHistory(String serverId, String jobId, String jobNm, String jobTmFrom, String jobTmTo, String stat) {
		return mapper.selectLoadJobHistory(serverId, jobId, jobNm, jobTmFrom, jobTmTo, stat);
	}

	/* 잡 이력 조회(TB_JOB_HISTORY) */
	@Override
	public List<Map<String, Object>> jobHistory(String serverId, String jobId, String jobNm, String jobTmFrom, String jobTmTo, String stat) {
		return mapper.selectJobHistory(serverId, jobId, jobNm, jobTmFrom, jobTmTo, stat);
	}

	/* DB 잡 이력 조회 */
	@Override
	public List<Map<String, Object>> dbJobHistory(int daemonId, String jobId, String jobNm, String jobTmFrom, String jobTmTo) {
		return mapper.selectDbJobHistory(daemonId, jobId, jobNm, jobTmFrom, jobTmTo);
	}

	/* Export 일자별 이력 조회 */
	@Override
	public List<Map<String, Object>> exportDateHistory(String jobDate) {
		return mapper.selectExportDateHistory(jobDate);
	}

	/* 변환(Conv) 잡 이력 조회 */
	@Override
	public List<Map<String, Object>> convJobHistory(String jobId, String jobNm, String infoFileNm, String jobTmFrom, String jobTmTo) {
		return mapper.selectConvJobHistory(jobId, jobNm, infoFileNm, jobTmFrom, jobTmTo);
	}

	// ===== monitor =====
	/* 로드 잡 모니터링 조회(A/B/C) */
	@Override
	public List<Map<String, Object>> loadMonitor(String stat, String jobTmFrom, String jobTmTo) {
		if ("A".equals(stat)) return mapper.selectLoadJobMonitorA(jobTmFrom, jobTmTo);
		if ("B".equals(stat)) return mapper.selectLoadJobMonitorB();
		if ("C".equals(stat)) return mapper.selectLoadJobMonitorC(jobTmFrom, jobTmTo);
		return List.of();
	}

	/* TB_JOB_HISTORY 기반 모니터링 조회(A/B/C) */
	@Override
	public List<Map<String, Object>> monitor2back(String stat, String jobTmFrom, String jobTmTo) {
		if ("A".equals(stat)) return mapper.selectJobMonitor2A(jobTmFrom, jobTmTo);
		if ("B".equals(stat)) return mapper.selectJobMonitor2B();
		if ("C".equals(stat)) return mapper.selectJobMonitor2C(jobTmFrom, jobTmTo);
		return List.of();
	}

	// ===== save =====
	/* 로드 잡 저장(C/U/D) */
	@Override
	@Transactional
	public int saveLoadJob(LoadJobSaveParamDto p) {
		if ("C".equals(p.getCrud())) {
			mapper.insertLoadJob(p);
			return 0;
		}
		if ("D".equals(p.getCrud())) {
			mapper.deleteLoadJob(p.getJob_id());
			return 0;
		}
		mapper.updateLoadJob(p);
		return 0;
	}

	/* 변환(Conv) 잡 저장(C/U/D) */
	@Override
	@Transactional
	public int saveConvJob(ConvJobSaveParamDto p) {
		if ("C".equals(p.getCrud())) {
			mapper.insertConvJob(p);
			return 0;
		}
		if ("D".equals(p.getCrud())) {
			mapper.deleteConvJob(p.getJob_id());
			return 0;
		}
		mapper.updateConvJob(p);
		return 0;
	}

	/* DB 잡 저장(C/U/D) */
	@Override
	@Transactional
	public int saveDbJob(DbJobSaveParamDto p) {
		if ("C".equals(p.getCrud())) {
			mapper.insertDbJob(p);
			return 0;
		}
		if ("D".equals(p.getCrud())) {
			mapper.deleteDbJob(p.getJob_id());
			return 0;
		}
		mapper.updateDbJob(p);
		return 0;
	}
}
