package com.archiving.job.controller;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.job.dto.ConvJobHistoryParamDto;
import com.archiving.job.dto.ConvJobSaveParamDto;
import com.archiving.job.dto.DbJobSaveParamDto;
import com.archiving.job.dto.JobHistoryParamDto;
import com.archiving.job.dto.JobListParamDto;
import com.archiving.job.dto.JobMonitorParamDto;
import com.archiving.job.dto.LoadJobSaveParamDto;
import com.archiving.job.service.JobService;
import com.archiving.mvc.util.JsonParamBinder;
import com.archiving.mvc.util.LegacyGridJson;

@RestController
public class JobController {
	private final JobService service;
	private final JsonParamBinder binder;

	public JobController(JobService service, JsonParamBinder binder) {
		this.service = service;
		this.binder = binder;
	}

	// ===== list =====
	/* 로드 잡 목록 조회 API */
	@PostMapping(value = "/GetLoadJobList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getLoadJobList(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobListParamDto p = binder.bind(param, JobListParamDto.class);
		List<Map<String, Object>> rows = service.loadJobList(nvl(p.get__server_id()), nvl(p.get__job_id()), nvl(p.get__job_nm()), nvl(p.get__use_yn()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* 변환(Conv) 잡 목록 조회 API */
	@PostMapping(value = "/GetCnvJobList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getCnvJobList(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobListParamDto p = binder.bind(param, JobListParamDto.class);
		List<Map<String, Object>> rows = service.convJobList(nvl(p.get__job_id()), nvl(p.get__job_nm()), nvl(p.get__use_yn()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* Export(DB) 잡 목록 조회 API */
	@PostMapping(value = "/GetExportJobList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getExportJobList(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobListParamDto p = binder.bind(param, JobListParamDto.class);
		List<Map<String, Object>> rows = service.dbJobList(2, nvl(p.get__job_id()), nvl(p.get__job_nm()), nvl(p.get__use_yn()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* Reorg(DB) 잡 목록 조회 API */
	@PostMapping(value = "/GetReorgJobList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getReorgJobList(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobListParamDto p = binder.bind(param, JobListParamDto.class);
		List<Map<String, Object>> rows = service.dbJobList(1, nvl(p.get__job_id()), nvl(p.get__job_nm()), nvl(p.get__use_yn()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* Delete(DB) 잡 목록 조회 API */
	@PostMapping(value = "/GetDeleteJobList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getDeleteJobList(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobListParamDto p = binder.bind(param, JobListParamDto.class);
		List<Map<String, Object>> rows = service.dbJobList(3, nvl(p.get__job_id()), nvl(p.get__job_nm()), nvl(p.get__use_yn()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	// ===== history =====
	/* 로드 잡 이력 조회 API */
	@PostMapping(value = "/GetLoadJobHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getLoadJobHistory(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobHistoryParamDto p = binder.bind(param, JobHistoryParamDto.class);
		List<Map<String, Object>> rows = service.loadJobHistory(nvl(p.get__server_id()), nvl(p.get__job_id()), nvl(p.get__job_nm()),
				nvl(p.get__job_tm_from()), nvl(p.get__job_tm_to()), nvl(p.get__stat()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* 잡 이력 조회 API(TB_JOB_HISTORY 기반) */
	@PostMapping(value = "/GetJobHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getJobHistory(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobHistoryParamDto p = binder.bind(param, JobHistoryParamDto.class);
		List<Map<String, Object>> rows = service.jobHistory(nvl(p.get__server_id()), nvl(p.get__job_id()), nvl(p.get__job_nm()),
				nvl(p.get__job_tm_from()), nvl(p.get__job_tm_to()), nvl(p.get__stat()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* Export(DB) 잡 이력 조회 API */
	@PostMapping(value = "/GetExportJobHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getExportJobHistory(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobHistoryParamDto p = binder.bind(param, JobHistoryParamDto.class);
		List<Map<String, Object>> rows = service.dbJobHistory(2, nvl(p.get__job_id()), nvl(p.get__job_nm()), nvl(p.get__job_tm_from()), nvl(p.get__job_tm_to()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* Reorg(DB) 잡 이력 조회 API */
	@PostMapping(value = "/GetReorgJobHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getReorgJobHistory(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobHistoryParamDto p = binder.bind(param, JobHistoryParamDto.class);
		List<Map<String, Object>> rows = service.dbJobHistory(1, nvl(p.get__job_id()), nvl(p.get__job_nm()), nvl(p.get__job_tm_from()), nvl(p.get__job_tm_to()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* Delete(DB) 잡 이력 조회 API */
	@PostMapping(value = "/GetDeleteJobHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getDeleteJobHistory(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobHistoryParamDto p = binder.bind(param, JobHistoryParamDto.class);
		List<Map<String, Object>> rows = service.dbJobHistory(3, nvl(p.get__job_id()), nvl(p.get__job_nm()), nvl(p.get__job_tm_from()), nvl(p.get__job_tm_to()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* Export 일자별 이력 조회 API */
	@PostMapping(value = "/GetExportDateHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getExportDateHistory(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobHistoryParamDto p = binder.bind(param, JobHistoryParamDto.class);
		List<Map<String, Object>> rows = service.exportDateHistory(nvl(p.get__job_tm_from()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* 변환(Conv) 잡 이력 조회 API */
	@PostMapping(value = "/GetCnvJobHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getCnvJobHistory(@RequestParam(value = "param", required = false) String param) throws Exception {
		ConvJobHistoryParamDto p = binder.bind(param, ConvJobHistoryParamDto.class);
		List<Map<String, Object>> rows = service.convJobHistory(nvl(p.get__job_id()), nvl(p.get__job_nm()), nvl(p.get__info_file_nm()), nvl(p.get__job_tm_from()), nvl(p.get__job_tm_to()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	// ===== monitor =====
	/* 로드 잡 모니터링 조회 API(A/B/C) */
	@PostMapping(value = "/GetJobMonitor", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getJobMonitor(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobMonitorParamDto p = binder.bind(param, JobMonitorParamDto.class);
		List<Map<String, Object>> rows = service.loadMonitor(nvl(p.get__stat()), nvl(p.get__job_tm_from()), nvl(p.get__job_tm_to()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* TB_JOB_HISTORY 기반 모니터링 조회 API(A/B/C) */
	@PostMapping(value = "/GetJobMonitor2back", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getJobMonitor2back(@RequestParam(value = "param", required = false) String param) throws Exception {
		JobMonitorParamDto p = binder.bind(param, JobMonitorParamDto.class);
		List<Map<String, Object>> rows = service.monitor2back(nvl(p.get__stat()), nvl(p.get__job_tm_from()), nvl(p.get__job_tm_to()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	// ===== save =====
	/* 로드 잡 저장 API(C/U/D) */
	@PostMapping(value = "/SetLoadJob", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setLoadJob(@RequestParam(value = "param", required = false) String param) throws Exception {
		LoadJobSaveParamDto p = binder.bind(param, LoadJobSaveParamDto.class);
		service.saveLoadJob(p);
		JSONObject out = new JSONObject();
		out.put("result", "OK");
		out.put("genKey", 0);
		return out;
	}

	/**
	 * Legacy compatibility endpoint used by public/jobInfo/src job pages.
	 * Current implementation routes to load-job save flow.
	 */
	/* (레거시) 잡 저장 API(SetLoadJob로 위임) */
	@PostMapping(value = "/SetJob", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setJob(@RequestParam(value = "param", required = false) String param) throws Exception {
		return setLoadJob(param);
	}

	/* 변환(Conv) 잡 저장 API(C/U/D) */
	@PostMapping(value = "/SetCnvJob", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setCnvJob(@RequestParam(value = "param", required = false) String param) throws Exception {
		ConvJobSaveParamDto p = binder.bind(param, ConvJobSaveParamDto.class);
		service.saveConvJob(p);
		JSONObject out = new JSONObject();
		out.put("result", "OK");
		out.put("genKey", 0);
		return out;
	}

	/* Export(DB) 잡 저장 API(C/U/D) */
	@PostMapping(value = "/SetExportJob", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setExportJob(@RequestParam(value = "param", required = false) String param) throws Exception {
		DbJobSaveParamDto p = binder.bind(param, DbJobSaveParamDto.class);
		service.saveDbJob(p);
		JSONObject out = new JSONObject();
		out.put("result", "OK");
		out.put("genKey", 0);
		return out;
	}

	/* Reorg(DB) 잡 저장 API(C/U/D) */
	@PostMapping(value = "/SetReorgJob", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setReorgJob(@RequestParam(value = "param", required = false) String param) throws Exception {
		DbJobSaveParamDto p = binder.bind(param, DbJobSaveParamDto.class);
		service.saveDbJob(p);
		JSONObject out = new JSONObject();
		out.put("result", "OK");
		out.put("genKey", 0);
		return out;
	}

	/* Delete(DB) 잡 저장 API(C/U/D) */
	@PostMapping(value = "/SetDeleteJob", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setDeleteJob(@RequestParam(value = "param", required = false) String param) throws Exception {
		DbJobSaveParamDto p = binder.bind(param, DbJobSaveParamDto.class);
		service.saveDbJob(p);
		JSONObject out = new JSONObject();
		out.put("result", "OK");
		out.put("genKey", 0);
		return out;
	}

	/* null 문자열을 빈 문자열로 치환 */
	private static String nvl(String v) {
		return v == null ? "" : v;
	}
}

