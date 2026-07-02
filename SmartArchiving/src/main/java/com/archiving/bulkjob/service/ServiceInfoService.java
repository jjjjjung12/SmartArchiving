package com.archiving.bulkjob.service;

import java.util.List;

import com.archiving.bulkjob.dto.ServiceInfoParamDto;
import com.archiving.bulkjob.dto.ServiceInfoRowDto;
import com.archiving.bulkjob.dto.ServiceInfoSaveParamDto;

public interface ServiceInfoService {

	/* 서비스 정보 목록 조회(EFBL/DFSL 자동 선택) */
	List<ServiceInfoRowDto> list(ServiceInfoParamDto p);

	/* 서비스 정보 저장(존재 여부에 따라 insert/update) */
	void save(ServiceInfoSaveParamDto p) throws Exception;

	/* 서비스 정보 전체 교체(TRUNCATE 후 일괄 insert) */
	void adminReplaceAll(ServiceInfoSaveParamDto p) throws Exception;
}
