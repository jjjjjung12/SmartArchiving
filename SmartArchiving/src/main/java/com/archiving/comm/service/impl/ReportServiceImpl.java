package com.archiving.comm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.comm.dao.mapper.ReportMapper;
import com.archiving.comm.dto.ReportAttrRowDto;
import com.archiving.comm.dto.ReportInfoDto;
import com.archiving.comm.dto.ReportListParamDto;
import com.archiving.comm.dto.ReportSaveParamDto;
import com.archiving.comm.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	private final ReportMapper reportMapper;

	public ReportServiceImpl(ReportMapper reportMapper) {
		this.reportMapper = reportMapper;
	}

	/* 리포트 정보 목록 조회(조건은 파라미터 DTO 기준) */
	@Override
	public List<ReportInfoDto> listInfo(ReportListParamDto p) {
		return reportMapper.selectReportInfoList(p.get__serverId(), p.get__use_yn(), p.get__report_id(), p.get__report_nm());
	}

	/* 리포트 테이블 속성 목록 조회 */
	@Override
	public List<ReportAttrRowDto> listTableAttrs(ReportListParamDto p) {
		return reportMapper.selectReportTableAttrs(p.get__serverId(), p.get__report_id());
	}

	/* 리포트/속성 저장 처리(crud에 따라 C/U/D 및 속성 CRUD) */
	@Override
	@Transactional
	public int save(ReportSaveParamDto p) {
		String crud = StringUtils.defaultString(p.getCrud());
		if ("C".equals(crud)) {
			reportMapper.insertReportInfo(p);
			return 0;
		}
		if ("U".equals(crud)) {
			return reportMapper.updateReportInfo(p);
		}
		if ("D".equals(crud)) {
			reportMapper.deleteReportInfo(p.getReport_id());
			reportMapper.deleteReportTableAttrs(p.getReport_id());
			return 0;
		}
		if ("AD".equals(crud)) {
			return reportMapper.deleteReportAttr(p.getReport_id(), p.getAttr_cd());
		}
		if ("AC".equals(crud)) {
			return reportMapper.insertReportAttr(p);
		}
		if ("AU".equals(crud)) {
			return reportMapper.updateReportAttr(p);
		}
		if ("AAU".equals(crud)) {
			int cnt = reportMapper.countReportAttr(p.getReport_id(), p.getAttr_cd());
			if (cnt == 0) {
				return reportMapper.insertReportAttr(p);
			}
			return reportMapper.updateReportAttr(p);
		}
		return 0;
	}
}
