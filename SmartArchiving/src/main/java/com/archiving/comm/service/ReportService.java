package com.archiving.comm.service;

import java.util.List;

import com.archiving.comm.dto.ReportAttrRowDto;
import com.archiving.comm.dto.ReportInfoDto;
import com.archiving.comm.dto.ReportListParamDto;
import com.archiving.comm.dto.ReportSaveParamDto;

public interface ReportService {

	/* 리포트 정보 목록 조회(조건은 파라미터 DTO 기준) */
	List<ReportInfoDto> listInfo(ReportListParamDto p);

	/* 리포트 테이블 속성 목록 조회 */
	List<ReportAttrRowDto> listTableAttrs(ReportListParamDto p);

	/* 리포트/속성 저장 처리(crud에 따라 C/U/D 및 속성 CRUD) */
	int save(ReportSaveParamDto p);
}
