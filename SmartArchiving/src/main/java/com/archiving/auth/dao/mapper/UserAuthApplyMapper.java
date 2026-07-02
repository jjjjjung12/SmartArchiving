package com.archiving.auth.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.user.dto.UserInfoDto;

public interface UserAuthApplyMapper {

	/* 사용자별 최신 미결재(대기) 결재요청 1건 조회 */
	LatestReqRow selectLatestPendingReq(@Param("userCd") String userCd);

	/* 결재요청 ID 기준 결재여부(승인/반려 등) 조회 */
	List<String> selectApprovalYnByReq(@Param("approvalReqId") String approvalReqId, @Param("userCd") String userCd);

	/* 결재요청/결재라인 상태 조회(레거시 GetUserAuthApply sQuery3 동작) */
	List<ApprovalLineStateRow> selectApprovalLineState(@Param("userCd") String userCd, @Param("approvalReqId") String approvalReqId);

	/* 요청일자별 결재요청 건수 조회 */
	Integer countReqByDate(@Param("reqDate") String reqDate);

	/* 요청일자 기준 다음 시퀀스(3자리) 계산 */
	String selectNextSeqByDate(@Param("reqDate") String reqDate);

	/* 요청일자 기준 최대 시퀀스 조회 */
	String selectMaxSeqByDate(@Param("reqDate") String reqDate);

	/* 인사마스터(사번) 기본정보 조회 */
	InsaRow selectInsaByEno(@Param("eno") String eno);

	/* 페이지명(URL 일부)로 메뉴명 조회 */
	String selectMenuNmByPageName(@Param("pageName") String pageName);

	/* 결재요청 ID 최대값 조회 */
	String selectMaxApprovalReqId();

	/* 결재요청 등록 */
	int insertApprovalReq(ApprovalReqInsertRow row);

	/* 결재라인 등록 */
	int insertApprovalLine(ApprovalLineInsertRow row);
	
	/* 사용자 조회 */
	List<UserInfoDto> selectUserList(@Param("userCd") String userCd);

	class LatestReqRow {
		private String approvalReqId;
		private String reqDate;
		private String ipAddress;
		private String ipAddressUser;
		private String expireDate;

		public String getApprovalReqId() { return approvalReqId; }
		public void setApprovalReqId(String approvalReqId) { this.approvalReqId = approvalReqId; }
		public String getReqDate() { return reqDate; }
		public void setReqDate(String reqDate) { this.reqDate = reqDate; }
		public String getIpAddress() { return ipAddress; }
		public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
		public String getIpAddressUser() { return ipAddressUser; }
		public void setIpAddressUser(String ipAddressUser) { this.ipAddressUser = ipAddressUser; }
		public String getExpireDate() { return expireDate; }
		public void setExpireDate(String expireDate) { this.expireDate = expireDate; }
	}

	class ApprovalLineStateRow {
		private String approvalDate;
		private String approvalReqReason;
		private String approvalYn;
		private String lineApprovalDate;
		private String approvalLineUserId;
		private String ipAddress;
		private String auth;
		private String comCd;

		public String getApprovalDate() { return approvalDate; }
		public void setApprovalDate(String approvalDate) { this.approvalDate = approvalDate; }
		public String getApprovalReqReason() { return approvalReqReason; }
		public void setApprovalReqReason(String approvalReqReason) { this.approvalReqReason = approvalReqReason; }
		public String getApprovalYn() { return approvalYn; }
		public void setApprovalYn(String approvalYn) { this.approvalYn = approvalYn; }
		public String getLineApprovalDate() { return lineApprovalDate; }
		public void setLineApprovalDate(String lineApprovalDate) { this.lineApprovalDate = lineApprovalDate; }
		public String getApprovalLineUserId() { return approvalLineUserId; }
		public void setApprovalLineUserId(String approvalLineUserId) { this.approvalLineUserId = approvalLineUserId; }
		public String getIpAddress() { return ipAddress; }
		public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
		public String getAuth() { return auth; }
		public void setAuth(String auth) { this.auth = auth; }
		public String getComCd() { return comCd; }
		public void setComCd(String comCd) { this.comCd = comCd; }
	}

	class InsaRow {
		private String eno;
		private String name;
		private String brc;
		private String brnm;
		private String oftC;
		private String oft;

		public String getEno() { return eno; }
		public void setEno(String eno) { this.eno = eno; }
		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		public String getBrc() { return brc; }
		public void setBrc(String brc) { this.brc = brc; }
		public String getBrnm() { return brnm; }
		public void setBrnm(String brnm) { this.brnm = brnm; }
		public String getOftC() { return oftC; }
		public void setOftC(String oftC) { this.oftC = oftC; }
		public String getOft() { return oft; }
		public void setOft(String oft) { this.oft = oft; }
	}

	class ApprovalReqInsertRow {
		private String userCd;
		private String approvalDivCd;
		private String approvalReqId;
		private String name;
		private String approvalReqReason;
		private String reqDate;
		private String expireDate;
		private String brc;
		private String brnm;
		private String oftC;
		private String oft;
		private String auth;
		private String comCd;
		private String ipAddress;
		private String programId;
		private String programNm;

		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getApprovalDivCd() { return approvalDivCd; }
		public void setApprovalDivCd(String approvalDivCd) { this.approvalDivCd = approvalDivCd; }
		public String getApprovalReqId() { return approvalReqId; }
		public void setApprovalReqId(String approvalReqId) { this.approvalReqId = approvalReqId; }
		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		public String getApprovalReqReason() { return approvalReqReason; }
		public void setApprovalReqReason(String approvalReqReason) { this.approvalReqReason = approvalReqReason; }
		public String getReqDate() { return reqDate; }
		public void setReqDate(String reqDate) { this.reqDate = reqDate; }
		public String getExpireDate() { return expireDate; }
		public void setExpireDate(String expireDate) { this.expireDate = expireDate; }
		public String getBrc() { return brc; }
		public void setBrc(String brc) { this.brc = brc; }
		public String getBrnm() { return brnm; }
		public void setBrnm(String brnm) { this.brnm = brnm; }
		public String getOftC() { return oftC; }
		public void setOftC(String oftC) { this.oftC = oftC; }
		public String getOft() { return oft; }
		public void setOft(String oft) { this.oft = oft; }
		public String getAuth() { return auth; }
		public void setAuth(String auth) { this.auth = auth; }
		public String getComCd() { return comCd; }
		public void setComCd(String comCd) { this.comCd = comCd; }
		public String getIpAddress() { return ipAddress; }
		public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
		public String getProgramId() { return programId; }
		public void setProgramId(String programId) { this.programId = programId; }
		public String getProgramNm() { return programNm; }
		public void setProgramNm(String programNm) { this.programNm = programNm; }
	}

	class ApprovalLineInsertRow {
		private String userCd;
		private String name;
		private String approvalDivCd;
		private String approvalReqId;
		private String approvalLineIndex;
		private String approvalLineUserId;
		private String approvalLineUserNm;
		private String beforeApplyYn;

		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		public String getApprovalDivCd() { return approvalDivCd; }
		public void setApprovalDivCd(String approvalDivCd) { this.approvalDivCd = approvalDivCd; }
		public String getApprovalReqId() { return approvalReqId; }
		public void setApprovalReqId(String approvalReqId) { this.approvalReqId = approvalReqId; }
		public String getApprovalLineIndex() { return approvalLineIndex; }
		public void setApprovalLineIndex(String approvalLineIndex) { this.approvalLineIndex = approvalLineIndex; }
		public String getApprovalLineUserId() { return approvalLineUserId; }
		public void setApprovalLineUserId(String approvalLineUserId) { this.approvalLineUserId = approvalLineUserId; }
		public String getApprovalLineUserNm() { return approvalLineUserNm; }
		public void setApprovalLineUserNm(String approvalLineUserNm) { this.approvalLineUserNm = approvalLineUserNm; }
		public String getBeforeApplyYn() { return beforeApplyYn; }
		public void setBeforeApplyYn(String beforeApplyYn) { this.beforeApplyYn = beforeApplyYn; }
	}
}

