package com.archiving.bulkjob.service;

import java.util.List;

import com.archiving.bulkjob.dto.BulkDataReqRowDto;
import com.archiving.bulkjob.dto.BulkInquiryReqListParamDto;
import com.archiving.bulkjob.dto.BulkReqJobParamDto;
import com.archiving.bulkjob.dto.BulkReqJobRowDto;

public interface BulkJobService {

	/* 결재요청 ID 기준 벌크잡 요청 목록 조회 */
	List<BulkReqJobRowDto> getBulkReqJobs(BulkReqJobParamDto p);

	/* 벌크조회 요청 목록 조회(사용자/요청일/검색구분/영장번호 조건) */
	List<BulkDataReqRowDto> getBulkInquiryReqList(BulkInquiryReqListParamDto p);
}
