package com.archiving.bulkjob.service;

import com.archiving.bulkjob.dto.BulkInquiryJobReqSaveParamDto;

public interface BulkInquiryJobReqService {

	/* 벌크조회 요청 저장(벌크잡 요청/결재요청/결재라인 생성) */
	void save(BulkInquiryJobReqSaveParamDto p) throws Exception;
}
