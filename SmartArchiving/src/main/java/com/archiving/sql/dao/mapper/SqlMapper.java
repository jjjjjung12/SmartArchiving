package com.archiving.sql.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archiving.sql.dto.BatchListSaveParamDto;
import com.archiving.sql.dto.ScheduleSaveParamDto;

public interface SqlMapper {
	// schedule list
	/* 스케줄 잡 목록 조회(잡ID/잡명 조건) */
	List<Map<String, Object>> selectScheduleList(@Param("jobId") String jobId, @Param("jobNm") String jobNm);

	/* 스케줄 잡 신규 등록 */
	int insertScheduleJob(ScheduleSaveParamDto p);

	/* 스케줄 잡 수정 */
	int updateScheduleJob(ScheduleSaveParamDto p);

	/* 스케줄 잡 삭제 */
	int deleteScheduleJob(@Param("jobId") String jobId);

	/* 스케줄 잡 컬럼 설정 삭제 */
	int deleteScheduleCols(@Param("jobId") String jobId);

	/** Legacy `GetJobList` query backed by `mhdb.TB_LOAD_JOB_MST`. */
	/* (레거시) 로드잡-테이블 목록 조회 */
	List<Map<String, Object>> selectJobTableList(
		@Param("serverId") String serverId,
		@Param("jobNm") String jobNm,
		@Param("useYn") String useYn);

	// batch list
	/* 배치 목록 조회(잡ID/잡명 조건) */
	List<Map<String, Object>> selectBatchList(@Param("jobId") String jobId, @Param("jobNm") String jobNm);

	/* 배치 목록 신규 등록 */
	int insertBatchList(BatchListSaveParamDto p);

	/* 배치 목록 수정 */
	int updateBatchList(BatchListSaveParamDto p);

	/* 배치 목록 삭제 */
	int deleteBatchList(@Param("jobId") String jobId);

	// batch execute update/insert for report sql
	/* 배치 리포트 SQL 업데이트 */
	int updateBatchReportSql(@Param("jobId") int jobId, @Param("jobStatusCd") String jobStatusCd, @Param("srcSql") String srcSql);

	/* 배치 리포트 SQL 신규 등록 */
	int insertBatchReportSql(@Param("jobStatusCd") String jobStatusCd, @Param("jobUserId") int jobUserId, @Param("srcSql") String srcSql,
							 @Param("useYn") String useYn, @Param("lstUpdTm") String lstUpdTm, @Param("jobOrder") int jobOrder);
}

