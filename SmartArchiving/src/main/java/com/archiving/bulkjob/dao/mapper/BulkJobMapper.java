package com.archiving.bulkjob.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.bulkjob.dto.BulkDataReqRowDto;
import com.archiving.bulkjob.dto.BulkReqJobRowDto;

public interface BulkJobMapper {
	/* 결재요청 ID 기준 벌크잡 요청 목록 조회 */
	List<BulkReqJobRowDto> selectBulkReqJobs(@Param("approvalReqId") String approvalReqId);

	/* 벌크조회 요청 목록 조회(사용자/요청일/검색구분/영장번호 조건) */
	List<BulkDataReqRowDto> selectBulkInquiryReqList(@Param("userCd") String userCd,
													 @Param("reqYmd") String reqYmd,
													 @Param("searchDivCd") String searchDivCd,
													 @Param("warrantNum") String warrantNum);
}

