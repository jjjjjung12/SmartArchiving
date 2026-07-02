package com.archiving.sql.controller;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.mvc.util.JsonParamBinder;
import com.archiving.mvc.util.LegacyGridJson;
import com.archiving.sql.dto.BatchListSaveParamDto;
import com.archiving.sql.dto.BatchSetParamDto;
import com.archiving.sql.dto.ScheduleSaveParamDto;
import com.archiving.sql.dto.SqlListParamDto;
import com.archiving.sql.dto.SqlTestParamDto;
import com.archiving.sql.service.SqlService;

@RestController
public class SqlController {
	private final SqlService service;
	private final JsonParamBinder binder;

	public SqlController(SqlService service, JsonParamBinder binder) {
		this.service = service;
		this.binder = binder;
	}

	/* Sqream DB 연결 확인 API */
	@PostMapping(value = "/GetDBConnectivity", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getDbConnectivity(@RequestParam(value = "param", required = false) String param) {
		JSONObject out = new JSONObject();
		out.put("result", "ERROR");
		try {
			service.checkSqreamConnectivity();
			out.put("result", "OK");
			out.put("message", "");
		} catch (Exception e) {
			out.put("message", e.getMessage());
		}
		return out;
	}

	/* Sqream SQL 테스트 실행 API(컬럼/행 결과 반환) */
	@PostMapping(value = "/GetSQLTest", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getSqlTest(@RequestParam(value = "param", required = false) String param) throws Exception {
		SqlTestParamDto p = binder.bind(param, SqlTestParamDto.class);
		JSONObject out = new JSONObject();
		out.put("result", "ERROR");
		try {
			SqlService.SqlTestResult r = service.runSqreamSqlTest(nvl(p.get__sql()));
			out.put("cols", LegacyGridJson.toJsonArray(r.getCols()));
			out.put("colCnt", r.getColCnt());
			out.put("rows", LegacyGridJson.toJsonArray(r.getRows()));
			out.put("result", "OK");
			out.put("message", "");
		} catch (Exception e) {
			out.put("result", "NOTOK");
			out.put("message", e.getMessage());
		}
		return out;
	}

	/* 스케줄 잡 목록 조회 API */
	@PostMapping(value = "/GetScheduleList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getScheduleList(@RequestParam(value = "param", required = false) String param) throws Exception {
		SqlListParamDto p = binder.bind(param, SqlListParamDto.class);
		List<Map<String, Object>> rows = service.scheduleList(nvl(p.get__job_id()), nvl(p.get__job_nm()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* 스케줄 잡 저장 API(C/U/D) */
	@PostMapping(value = "/SetScheduleList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setScheduleList(@RequestParam(value = "param", required = false) String param) throws Exception {
		ScheduleSaveParamDto p = binder.bind(param, ScheduleSaveParamDto.class);
		service.saveSchedule(p);
		JSONObject out = new JSONObject();
		out.put("result", "OK");
		out.put("genKey", 0);
		return out;
	}

	/* 배치 목록 조회 API */
	@PostMapping(value = "/GetBatchList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getBatchList(@RequestParam(value = "param", required = false) String param) throws Exception {
		SqlListParamDto p = binder.bind(param, SqlListParamDto.class);
		List<Map<String, Object>> rows = service.batchList(nvl(p.get__job_id()), nvl(p.get__job_nm()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* (레거시) 로드잡 목록 조회 API */
	@PostMapping(value = "/GetJobList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getJobList(@RequestParam(value = "param", required = false) String param) throws Exception {
		SqlListParamDto p = binder.bind(param, SqlListParamDto.class);
		List<Map<String, Object>> rows = service.jobTableList(nvl(p.get__server_id()), nvl(p.get__job_nm()), nvl(p.get__use_yn()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* (레거시) ETL 소스 잡 목록 조회 API(GetJobList 호환) */
	@PostMapping(value = "/GetEttSrcJobList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getEttSrcJobList(@RequestParam(value = "param", required = false) String param) throws Exception {
		SqlListParamDto p = binder.bind(param, SqlListParamDto.class);
		List<Map<String, Object>> rows = service.jobTableList(nvl(p.get__server_id()), nvl(p.get__job_nm()), nvl(p.get__use_yn()));
		return LegacyGridJson.wrapRows(rows, p.get__rows(), p.get__page());
	}

	/* 배치 목록 저장 API(C/U/D) */
	@PostMapping(value = "/SetBatchList", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setBatchList(@RequestParam(value = "param", required = false) String param) throws Exception {
		BatchListSaveParamDto p = binder.bind(param, BatchListSaveParamDto.class);
		service.saveBatchList(p);
		JSONObject out = new JSONObject();
		out.put("result", "OK");
		out.put("genKey", 0);
		return out;
	}

	/* 배치 리포트 SQL 설정 API(업데이트/추가) */
	@PostMapping(value = "/SetBatch", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject setBatch(@RequestParam(value = "param", required = false) String param) throws Exception {
		BatchSetParamDto p = binder.bind(param, BatchSetParamDto.class);
		service.setBatchReportSql(nvl(p.get__job_id()), nvl(p.get__job_user_id()), nvl(p.get__job_status_cd()), nvl(p.get__src_sql()));
		JSONObject out = new JSONObject();
		out.put("result", "OK");
		return out;
	}

	/* null 문자열을 빈 문자열로 치환 */
	private static String nvl(String v) {
		return v == null ? "" : v;
	}
}

