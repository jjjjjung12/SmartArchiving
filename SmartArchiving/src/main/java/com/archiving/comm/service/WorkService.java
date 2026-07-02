package com.archiving.comm.service;

import java.util.List;

import com.archiving.comm.dto.WorkListParamDto;
import com.archiving.comm.dto.WorkRowDto;
import com.archiving.comm.dto.WorkSaveParamDto;

public interface WorkService {

	/* 업무 목록 조회(조건은 파라미터 DTO 기준) */
	List<WorkRowDto> list(WorkListParamDto p);

	/* 업무 저장 처리(C:신규, U:수정, D:삭제) */
	void save(WorkSaveParamDto p);
}
