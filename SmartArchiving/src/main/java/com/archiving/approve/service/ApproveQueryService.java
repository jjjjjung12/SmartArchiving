package com.archiving.approve.service;

import java.util.List;

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

public interface ApproveQueryService {

	/* 결재 현황 목록 조회(기간/사용자코드 조건) */
	List<ApproveStatRowDto> statList(ApproveStatSearchParamDto p);

	/* 결재 현황 사용자 팝업 목록 조회 */
	List<UserPopRow> statUserPop(ApproveStatSearchParamDto p);

	/* 결재 처리(결재자) 목록 조회 */
	List<ApproveProcRowDto> procList(ApproveProcListParamDto p);

	/* 결재 처리(관리자) 목록 조회(결과 없으면 폴백 쿼리 사용) */
	List<ApproveProcRowDto> procAdminList(ApproveProcListParamDto p);

	/* 다운로드 요청 목록 조회 */
	List<DownloadReqRowDto> downloadReqList(DownloadReqListParamDto p);

	/* 마스킹 처리 이력 목록 조회 */
	List<MaskHistoryRowDto> maskHistoryList(MaskHistoryListParamDto p);

	/* 권한 신청 내용 조회 API */
	List<UserAuthApplyDetailRowDto> getUserAuthApplyDetail(UserAuthApplyDetailParamDto p);
}
