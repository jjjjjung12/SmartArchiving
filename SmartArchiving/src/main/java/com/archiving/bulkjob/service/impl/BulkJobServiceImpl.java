package com.archiving.bulkjob.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.archiving.bulkjob.dao.mapper.BulkJobMapper;
import com.archiving.bulkjob.dto.BulkDataReqRowDto;
import com.archiving.bulkjob.dto.BulkInquiryReqListParamDto;
import com.archiving.bulkjob.dto.BulkReqJobParamDto;
import com.archiving.bulkjob.dto.BulkReqJobRowDto;
import com.archiving.bulkjob.service.BulkJobService;

@Service
public class BulkJobServiceImpl implements BulkJobService {
	private final BulkJobMapper bulkJobMapper;

	public BulkJobServiceImpl(BulkJobMapper bulkJobMapper) {
		this.bulkJobMapper = bulkJobMapper;
	}

	/* 결재요청 ID 기준 벌크잡 요청 목록 조회 */
	@Override
	public List<BulkReqJobRowDto> getBulkReqJobs(BulkReqJobParamDto p) {
		return bulkJobMapper.selectBulkReqJobs(p == null ? null : p.getAPPROVAL_REQ_ID());
	}

	/* 벌크조회 요청 목록 조회(사용자/요청일/검색구분/영장번호 조건) */
	@Override
	public List<BulkDataReqRowDto> getBulkInquiryReqList(BulkInquiryReqListParamDto p) {
		if (p == null) p = new BulkInquiryReqListParamDto();
		return bulkJobMapper.selectBulkInquiryReqList(
				StringUtils.defaultString(p.getF_SP_USER_CD()),
				StringUtils.defaultString(p.getF_SP_REQ_YMD()),
				StringUtils.defaultString(p.getF_SP_SEARCH_DIV_CD()),
				StringUtils.defaultString(p.getF_SP_WARRANT_NUM())
		);
	}
}
