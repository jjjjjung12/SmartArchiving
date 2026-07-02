package com.archiving.comm.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.comm.dto.ReportAttrRowDto;
import com.archiving.comm.dto.ReportInfoDto;
import com.archiving.comm.dto.ReportSaveParamDto;

public interface ReportMapper {
	/* 리포트 정보 목록 조회(서버/사용여부/리포트ID/리포트명 조건) */
	List<ReportInfoDto> selectReportInfoList(@Param("serverId") String serverId,
											 @Param("useYn") String useYn,
											 @Param("reportId") String reportId,
											 @Param("reportNm") String reportNm);

	/* 리포트 테이블 컬럼(속성) 목록 조회 */
	List<ReportAttrRowDto> selectReportTableAttrs(@Param("serverId") String serverId, @Param("reportId") String reportId);

	/* 리포트 정보 신규 등록 */
	int insertReportInfo(ReportSaveParamDto p);

	/* 리포트 정보 수정 */
	int updateReportInfo(ReportSaveParamDto p);

	/* 리포트 정보 삭제 */
	int deleteReportInfo(@Param("reportId") String reportId);

	/* 리포트-테이블 속성 매핑 전체 삭제 */
	int deleteReportTableAttrs(@Param("reportId") String reportId);

	/* 리포트 속성 신규 등록 */
	int insertReportAttr(ReportSaveParamDto p);

	/* 리포트 속성 수정 */
	int updateReportAttr(ReportSaveParamDto p);

	/* 리포트 속성 삭제 */
	int deleteReportAttr(@Param("reportId") String reportId, @Param("attrCd") String attrCd);

	/* 리포트 속성 존재 여부 카운트 */
	int countReportAttr(@Param("reportId") String reportId, @Param("attrCd") String attrCd);
}

