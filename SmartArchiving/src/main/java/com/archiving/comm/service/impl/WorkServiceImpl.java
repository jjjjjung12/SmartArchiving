package com.archiving.comm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.archiving.comm.dao.mapper.WorkMapper;
import com.archiving.comm.dto.WorkListParamDto;
import com.archiving.comm.dto.WorkRowDto;
import com.archiving.comm.dto.WorkSaveParamDto;
import com.archiving.comm.service.WorkService;

@Service
public class WorkServiceImpl implements WorkService {
	private final WorkMapper workMapper;

	public WorkServiceImpl(WorkMapper workMapper) {
		this.workMapper = workMapper;
	}

	/* 업무 목록 조회(조건은 파라미터 DTO 기준) */
	@Override
	public List<WorkRowDto> list(WorkListParamDto p) {
		if (p == null) p = new WorkListParamDto();
		return workMapper.selectWorkList(p.get__server_id(), p.get__work_cd(), p.get__use_yn());
	}

	/* 업무 저장 처리(C:신규, U:수정, D:삭제) */
	@Override
	@Transactional
	public void save(WorkSaveParamDto p) {
		String crud = StringUtils.defaultString(p == null ? null : p.getCrud());
		if ("C".equals(crud)) {
			workMapper.insertWork(p);
			return;
		}
		if ("D".equals(crud)) {
			workMapper.deleteWork(p.getServer_id(), p.getWork_cd());
			return;
		}
		workMapper.updateWork(p);
	}
}
