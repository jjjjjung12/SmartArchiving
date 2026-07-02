package com.archiving.bulkjob.service;

import java.util.List;

import com.archiving.bulkjob.dao.mapper.BulkInquiryJobResultMapper.MenuRow;
import com.archiving.bulkjob.dto.BulkInquiryJobResultParamDto;

public interface BulkInquiryJobResultService {

	/* 메뉴 목록 조회(전체 또는 사용자 권한 기준) */
	List<MenuRow> list(BulkInquiryJobResultParamDto p);
}
