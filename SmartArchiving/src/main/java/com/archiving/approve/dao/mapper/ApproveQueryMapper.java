package com.archiving.approve.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.approve.dto.ApproveProcRowDto;
import com.archiving.approve.dto.ApproveStatRowDto;
import com.archiving.approve.dto.DownloadReqRowDto;
import com.archiving.approve.dto.MaskHistoryRowDto;
import com.archiving.approve.dto.UserAuthApplyDetailRowDto;

public interface ApproveQueryMapper {
	/* 결재 현황 목록 조회(기간/사용자코드 조건) */
	List<ApproveStatRowDto> selectApproveStatList(@Param("start") String start,
												 @Param("end") String end,
												 @Param("userCd") String userCd);

	/* 결재 현황 사용자 팝업(대상 사용자 목록) 조회 */
	List<UserPopRow> selectApproveStatUserPop(@Param("userCd") String userCd);

	/* 결재 처리(결재자 기준) 대상 목록 조회 */
	List<ApproveProcRowDto> selectApproveProcList(@Param("approverUserCd") String approverUserCd);

	/* 결재 처리(관리자) 대상 목록 조회 */
	List<ApproveProcRowDto> selectApproveProcAdminList();

	/* 결재 처리(관리자) 대상 목록 조회(폴백 쿼리) */
	List<ApproveProcRowDto> selectApproveProcAdminListFallback();

	/* 다운로드 요청 목록 조회(기간/사용자/구분/요청번호/벌크여부 조건) */
	List<DownloadReqRowDto> selectDownloadReqList(@Param("start") String start,
												  @Param("end") String end,
												  @Param("userNm") String userNm,
												  @Param("downCd") String downCd,
												  @Param("reqNum") String reqNum,
												  @Param("bulkYn") String bulkYn);

	/* 마스킹 처리 이력 조회(기간 조건) */
	List<MaskHistoryRowDto> selectMaskHistoryList(@Param("start") String start,
												 @Param("end") String end);

	/* 권한 신청 내용 조회 API */
	List<UserAuthApplyDetailRowDto> selectUserAuthApplyDetail(@Param("approvalReqId") String approvalReqId);
	
	class UserPopRow {
		private String userCd;
		private String userNm;
		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getUserNm() { return userNm; }
		public void setUserNm(String userNm) { this.userNm = userNm; }
	}
}

