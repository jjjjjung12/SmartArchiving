package com.archiving.sql.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.sql.dao.mapper.SqlMapper;
import com.archiving.sql.dao.sqream.SqlSqreamMapper;
import com.archiving.sql.dto.BatchListSaveParamDto;
import com.archiving.sql.dto.ScheduleSaveParamDto;
import com.archiving.sql.service.SqlService;
import com.archiving.util.UtilClass;

@Service
public class SqlServiceImpl implements SqlService {
	private final SqlMapper mapper;
	private final SqlSqreamMapper sqreamMapper;
	private final UtilClass utilClass = new UtilClass();

	public SqlServiceImpl(SqlMapper mapper, SqlSqreamMapper sqreamMapper) {
		this.mapper = mapper;
		this.sqreamMapper = sqreamMapper;
	}

	/* Sqream 연결 확인(Ping) */
	@Override
	public void checkSqreamConnectivity() {
		sqreamMapper.selectPing();
	}

	/* Sqream SQL 테스트 실행(결과 컬럼/행을 그리드용으로 구성) */
	@Override
	public SqlTestResult runSqreamSqlTest(String sql) throws Exception {
		String realSql = (sql == null ? "" : sql.trim());
		if (!realSql.toLowerCase().contains("limit")) {
			realSql = realSql + " limit 5";
		}
		final String execSql = realSql;

		List<Map<String, Object>> rows = sqreamMapper.selectDynamicSql(execSql);
		List<Map<String, Object>> safeRows = rows != null ? rows : Collections.emptyList();
		List<Map<String, Object>> cols = new ArrayList<>();
		if (!safeRows.isEmpty()) {
			for (String key : safeRows.get(0).keySet()) {
				Map<String, Object> col = new LinkedHashMap<>();
				col.put("title", key);
				col.put("dataIndx", key);
				col.put("width", "100");
				cols.add(col);
			}
		}
		return new SqlTestResult(cols, cols.size(), safeRows);
	}

	/* 스케줄 잡 목록 조회 */
	@Override
	public List<Map<String, Object>> scheduleList(String jobId, String jobNm) {
		return mapper.selectScheduleList(jobId, jobNm);
	}

	/* 스케줄 잡 저장(C/U/D) */
	@Override
	@Transactional
	public int saveSchedule(ScheduleSaveParamDto p) {
		if ("C".equals(p.getCrud())) {
			mapper.insertScheduleJob(p);
			return 0;
		}
		if ("D".equals(p.getCrud())) {
			mapper.deleteScheduleJob(p.getJob_id());
			mapper.deleteScheduleCols(p.getJob_id());
			return 0;
		}
		mapper.updateScheduleJob(p);
		return 0;
	}

	/* 배치 목록 조회 */
	@Override
	public List<Map<String, Object>> batchList(String jobId, String jobNm) {
		return mapper.selectBatchList(jobId, jobNm);
	}

	/* (레거시) 로드잡-테이블 목록 조회 */
	@Override
	public List<Map<String, Object>> jobTableList(String serverId, String jobNm, String useYn) {
		return mapper.selectJobTableList(nvlStar(serverId), nvlStar(jobNm), nvlStar(useYn));
	}

	/* null 문자열을 공백으로 정리 후 trim */
	private static String nvlStar(String s) {
		return s == null ? "" : s.trim();
	}

	/* 배치 목록 저장(C/U/D) */
	@Override
	@Transactional
	public int saveBatchList(BatchListSaveParamDto p) {
		if ("C".equals(p.getCrud())) {
			mapper.insertBatchList(p);
			return 0;
		}
		if ("D".equals(p.getCrud())) {
			mapper.deleteBatchList(p.getJob_id());
			mapper.deleteScheduleCols(p.getJob_id());
			return 0;
		}
		mapper.updateBatchList(p);
		return 0;
	}

	/* 배치 리포트 SQL 설정(잡ID 있으면 업데이트, 없으면 신규 추가) */
	@Override
	@Transactional
	public int setBatchReportSql(String jobId, String jobUserId, String jobStatusCd, String srcSql) {
		String jid = jobId == null ? "" : jobId.trim();
		if (!jid.isEmpty()) {
			mapper.updateBatchReportSql(Integer.parseInt(jid), jobStatusCd, srcSql);
			return 0;
		}

		int userId = Integer.parseInt(jobUserId == null ? "0" : jobUserId.trim());
		String curTime = utilClass.getCurrentTime_Publish();
		mapper.insertBatchReportSql("05", userId, srcSql, "Y", curTime, 999);
		return 0;
	}
}
