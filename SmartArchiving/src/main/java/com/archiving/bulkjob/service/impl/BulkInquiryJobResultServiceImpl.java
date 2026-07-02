package com.archiving.bulkjob.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.archiving.bulkjob.dao.mapper.BulkInquiryJobResultMapper;
import com.archiving.bulkjob.dao.mapper.BulkInquiryJobResultMapper.MenuRow;
import com.archiving.bulkjob.dto.BulkInquiryJobResultParamDto;
import com.archiving.bulkjob.service.BulkInquiryJobResultService;

@Service
public class BulkInquiryJobResultServiceImpl implements BulkInquiryJobResultService {
	private final BulkInquiryJobResultMapper mapper;

	public BulkInquiryJobResultServiceImpl(BulkInquiryJobResultMapper mapper) {
		this.mapper = mapper;
	}

	/* 메뉴 목록 조회(전체 또는 사용자 권한 기준) */
	@Override
	public List<MenuRow> list(BulkInquiryJobResultParamDto p) {
		String userCd = StringUtils.defaultString(p == null ? null : p.getUser_cd());
		String selCd = StringUtils.defaultString(p == null ? null : p.getSel_cd());

		boolean isAll = "ALL__".equalsIgnoreCase(userCd) || "admin".equalsIgnoreCase(userCd);
		if (isAll) {
			boolean onlyUse = "M".equalsIgnoreCase(selCd);
			return mapper.selectAllMenus(onlyUse);
		}

		return mapper.selectUserMenus(StringUtils.defaultString(p.getUser_id()));
	}
}
