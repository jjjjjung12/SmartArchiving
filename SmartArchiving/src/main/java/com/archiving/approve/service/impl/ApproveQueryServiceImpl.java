package com.archiving.approve.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.archiving.approve.dao.mapper.ApproveQueryMapper;
import com.archiving.approve.dao.mapper.ApproveQueryMapper.UserPopRow;
import com.archiving.approve.dto.ApproveProcListParamDto;
import com.archiving.approve.dto.ApproveProcRowDto;
import com.archiving.approve.dto.ApproveStatRowDto;
import com.archiving.approve.dto.ApproveStatSearchParamDto;
import com.archiving.approve.dto.DownloadReqListParamDto;
import com.archiving.approve.dto.DownloadReqRowDto;
import com.archiving.approve.dto.MaskHistoryListParamDto;
import com.archiving.approve.dto.MaskHistoryRowDto;
import com.archiving.approve.dto.UserAuthApplyDetailParamDto;
import com.archiving.approve.dto.UserAuthApplyDetailRowDto;
import com.archiving.approve.service.ApproveQueryService;

@Service
public class ApproveQueryServiceImpl implements ApproveQueryService {
	private final ApproveQueryMapper approveQueryMapper;

	public ApproveQueryServiceImpl(ApproveQueryMapper approveQueryMapper) {
		this.approveQueryMapper = approveQueryMapper;
	}

	/* 결재 현황 목록 조회(기간/사용자코드 조건) */
	@Override
	public List<ApproveStatRowDto> statList(ApproveStatSearchParamDto p) {
		return approveQueryMapper.selectApproveStatList(
				StringUtils.defaultString(p.getStart_tr_ymd()),
				StringUtils.defaultString(p.getEnd_tr_ymd()),
				StringUtils.defaultString(p.getUser_cd())
		);
	}

	/* 결재 현황 사용자 팝업 목록 조회 */
	@Override
	public List<UserPopRow> statUserPop(ApproveStatSearchParamDto p) {
		return approveQueryMapper.selectApproveStatUserPop(StringUtils.defaultString(p.get__user_cd()));
	}

	/* 결재 처리(결재자) 목록 조회 */
	@Override
	public List<ApproveProcRowDto> procList(ApproveProcListParamDto p) {
		return approveQueryMapper.selectApproveProcList(StringUtils.defaultString(p.getUser_cd()));
	}

	/* 결재 처리(관리자) 목록 조회(결과 없으면 폴백 쿼리 사용) */
	@Override
	public List<ApproveProcRowDto> procAdminList(ApproveProcListParamDto p) {
		List<ApproveProcRowDto> list = approveQueryMapper.selectApproveProcAdminList();
		if (list == null || list.isEmpty()) {
			return approveQueryMapper.selectApproveProcAdminListFallback();
		}
		return list;
	}

	/* 다운로드 요청 목록 조회 */
	@Override
	public List<DownloadReqRowDto> downloadReqList(DownloadReqListParamDto p) {
		return approveQueryMapper.selectDownloadReqList(
				StringUtils.defaultString(p.getStart_dt()),
				StringUtils.defaultString(p.getEnd_dt()),
				StringUtils.defaultString(p.getUser_nm()),
				StringUtils.defaultString(p.getDown_cd()),
				StringUtils.defaultString(p.getReq_num()),
				StringUtils.defaultString(p.getBulk_yn())
		);
	}

	/* 마스킹 처리 이력 목록 조회 */
	@Override
	public List<MaskHistoryRowDto> maskHistoryList(MaskHistoryListParamDto p) {
		return approveQueryMapper.selectMaskHistoryList(
				StringUtils.defaultString(p.getStart_dt()),
				StringUtils.defaultString(p.getEnd_dt())
		);
	}

	/* 권한 신청 내용 조회 API */
	@Override
	public List<UserAuthApplyDetailRowDto> getUserAuthApplyDetail(UserAuthApplyDetailParamDto p) {
		return approveQueryMapper.selectUserAuthApplyDetail(StringUtils.defaultString(p.getAPPROVAL_REQ_ID()));
	}
}
