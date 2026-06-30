package com.archiving.util.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.util.dto.InsaOptionRowDto;
import com.archiving.util.dto.ScheduleColRowDto;
import com.archiving.util.dto.ScheduleIniRowDto;
import com.archiving.util.dto.ScheduleJobListRowDto;

public interface UtilLegacyMapper {

	/* Postgres 에러 코드(detailCd) 메시지 조회 */
	String selectPostgresErrorMessage(@Param("detailCd") String detailCd);

	/* 스케줄 초기값 조회(잡코드 기준) */
	List<ScheduleIniRowDto> selectScheduleIniByJobCd(@Param("jobCd") String jobCd);

	/* 크론 등록 대상 스케줄 잡 목록 조회 */
	List<ScheduleJobListRowDto> selectScheduleJobsForCron();

	/* 스케줄 잡 컬럼 목록 조회(잡코드 기준) */
	List<ScheduleColRowDto> selectScheduleColsByJobCd(@Param("jobCd") String jobCd);

	/* URL 일부로 메뉴명 조회 */
	String selectMenuNmByUrlLike(@Param("programId") String programId);

	/* 사용자 로그 등록 */
	int insertUserLog(
		@Param("groupId") Integer groupId,
		@Param("userId") Integer userId,
		@Param("userCd") String userCd,
		@Param("programWhere") String programWhere,
		@Param("userName") String userName,
		@Param("addr") String addr,
		@Param("programId") String programId,
		@Param("programNm") String programNm,
		@Param("method") String method);

	/* 인사 라인(팀장급) 결재선 후보 조회 */
	List<InsaOptionRowDto> selectInsaTeamLeadLine(@Param("userCd") String userCd);

	/* 인사 1차 결재선 후보 조회 */
	List<InsaOptionRowDto> selectInsaFirstApprovalLine(@Param("userCd") String userCd);

	/* 인사 2차 결재선 후보 조회 */
	List<InsaOptionRowDto> selectInsaSecondApprovalLine(@Param("userCd") String userCd);

	/* 현재 시각 -1일 문자열 조회 */
	String selectNowMinusOneDay();
}
