package com.archiving.job.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.archiving.job.dto.ConvJobSaveParamDto;
import com.archiving.job.dto.DbJobSaveParamDto;
import com.archiving.job.dto.LoadJobSaveParamDto;

public interface JobMapper {
	// ===== list =====
	/* 로드 잡 목록 조회(서버/잡ID/잡명/사용여부 조건) */
	List<Map<String, Object>> selectLoadJobList(@Param("serverId") String serverId,
											   @Param("jobId") String jobId,
											   @Param("jobNm") String jobNm,
											   @Param("useYn") String useYn);

	/* 변환(Conv) 잡 목록 조회(잡ID/잡명/사용여부 조건) */
	List<Map<String, Object>> selectConvJobList(@Param("jobId") String jobId,
											   @Param("jobNm") String jobNm,
											   @Param("useYn") String useYn);

	/* DB 잡 목록 조회(데몬/잡ID/잡명/사용여부 조건) */
	List<Map<String, Object>> selectDbJobList(@Param("daemonId") int daemonId,
											 @Param("jobId") String jobId,
											 @Param("jobNm") String jobNm,
											 @Param("useYn") String useYn);

	// ===== history =====
	/* 로드 잡 이력 조회(기간/상태 조건) */
	List<Map<String, Object>> selectLoadJobHistory(@Param("serverId") String serverId,
												  @Param("jobId") String jobId,
												  @Param("jobNm") String jobNm,
												  @Param("jobTmFrom") String jobTmFrom,
												  @Param("jobTmTo") String jobTmTo,
												  @Param("stat") String stat);

	/* 잡 이력 조회(TB_JOB_HISTORY 기반, 기간/상태 조건) */
	List<Map<String, Object>> selectJobHistory(@Param("serverId") String serverId,
											  @Param("jobId") String jobId,
											  @Param("jobNm") String jobNm,
											  @Param("jobTmFrom") String jobTmFrom,
											  @Param("jobTmTo") String jobTmTo,
											  @Param("stat") String stat);

	/* DB 잡 이력 조회(데몬/기간 조건) */
	List<Map<String, Object>> selectDbJobHistory(@Param("daemonId") int daemonId,
												@Param("jobId") String jobId,
												@Param("jobNm") String jobNm,
												@Param("jobTmFrom") String jobTmFrom,
												@Param("jobTmTo") String jobTmTo);

	/* 특정 일자(jobDate) 기준 export 이력 조회 */
	List<Map<String, Object>> selectExportDateHistory(@Param("jobDate") String jobDate);

	/* 변환(Conv) 잡 이력 조회(기간/파일명 조건) */
	List<Map<String, Object>> selectConvJobHistory(@Param("jobId") String jobId,
												   @Param("jobNm") String jobNm,
												   @Param("infoFileNm") String infoFileNm,
												   @Param("jobTmFrom") String jobTmFrom,
												   @Param("jobTmTo") String jobTmTo);

	// ===== monitor =====
	/* 로드 잡 모니터링 요약(A) 조회 */
	List<Map<String, Object>> selectLoadJobMonitorA(@Param("jobTmFrom") String jobTmFrom, @Param("jobTmTo") String jobTmTo);

	/* 로드 잡 모니터링 요약(B: 라이선스/사용량) 조회 */
	List<Map<String, Object>> selectLoadJobMonitorB();

	/* 로드 잡 모니터링 상세(C) 조회 */
	List<Map<String, Object>> selectLoadJobMonitorC(@Param("jobTmFrom") String jobTmFrom, @Param("jobTmTo") String jobTmTo);

	/* (TB_JOB_HISTORY) 모니터링 요약(A) 조회 */
	List<Map<String, Object>> selectJobMonitor2A(@Param("jobTmFrom") String jobTmFrom, @Param("jobTmTo") String jobTmTo);

	/* (TB_JOB_HISTORY) 모니터링 요약(B: 라이선스/사용량) 조회 */
	List<Map<String, Object>> selectJobMonitor2B();

	/* (TB_JOB_HISTORY) 모니터링 상세(C) 조회 */
	List<Map<String, Object>> selectJobMonitor2C(@Param("jobTmFrom") String jobTmFrom, @Param("jobTmTo") String jobTmTo);

	// ===== save (C/U/D) =====
	/* 로드 잡 신규 등록 */
	int insertLoadJob(LoadJobSaveParamDto p);

	/* 로드 잡 수정 */
	int updateLoadJob(LoadJobSaveParamDto p);

	/* 로드 잡 삭제 */
	int deleteLoadJob(@Param("jobId") String jobId);

	/* 변환(Conv) 잡 신규 등록 */
	int insertConvJob(ConvJobSaveParamDto p);

	/* 변환(Conv) 잡 수정 */
	int updateConvJob(ConvJobSaveParamDto p);

	/* 변환(Conv) 잡 삭제 */
	int deleteConvJob(@Param("jobId") String jobId);

	/* DB 잡 신규 등록 */
	int insertDbJob(DbJobSaveParamDto p);

	/* DB 잡 수정 */
	int updateDbJob(DbJobSaveParamDto p);

	/* DB 잡 삭제 */
	int deleteDbJob(@Param("jobId") String jobId);
}

