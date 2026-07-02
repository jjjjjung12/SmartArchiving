package com.archiving.sql.service;

import java.util.List;
import java.util.Map;

import com.archiving.sql.dto.BatchListSaveParamDto;
import com.archiving.sql.dto.ScheduleSaveParamDto;

public interface SqlService {

	/* Sqream 연결 확인(Ping) */
	void checkSqreamConnectivity();

	/* Sqream SQL 테스트 실행(결과 컬럼/행을 그리드용으로 구성) */
	SqlTestResult runSqreamSqlTest(String sql) throws Exception;

	/* 스케줄 잡 목록 조회 */
	List<Map<String, Object>> scheduleList(String jobId, String jobNm);

	/* 스케줄 잡 저장(C/U/D) */
	int saveSchedule(ScheduleSaveParamDto p);

	/* 배치 목록 조회 */
	List<Map<String, Object>> batchList(String jobId, String jobNm);

	/* (레거시) 로드잡-테이블 목록 조회 */
	List<Map<String, Object>> jobTableList(String serverId, String jobNm, String useYn);

	/* 배치 목록 저장(C/U/D) */
	int saveBatchList(BatchListSaveParamDto p);

	/* 배치 리포트 SQL 설정(잡ID 있으면 업데이트, 없으면 신규 추가) */
	int setBatchReportSql(String jobId, String jobUserId, String jobStatusCd, String srcSql);

	class SqlTestResult {
		private final List<Map<String, Object>> cols;
		private final int colCnt;
		private final List<Map<String, Object>> rows;

		public SqlTestResult(List<Map<String, Object>> cols, int colCnt, List<Map<String, Object>> rows) {
			this.cols = cols;
			this.colCnt = colCnt;
			this.rows = rows;
		}

		/* 컬럼 메타 목록 반환 */
		public List<Map<String, Object>> getCols() {
			return cols;
		}

		/* 컬럼 개수 반환 */
		public int getColCnt() {
			return colCnt;
		}

		/* 결과 행 목록 반환 */
		public List<Map<String, Object>> getRows() {
			return rows;
		}
	}
}
