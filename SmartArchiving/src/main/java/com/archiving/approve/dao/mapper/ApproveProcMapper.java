package com.archiving.approve.dao.mapper;

import org.apache.ibatis.annotations.Param;

public interface ApproveProcMapper {
	/* 결재라인의 결재여부(승인/반려/대기) 조회 */
	String selectApprovalYn(@Param("userCd") String userCd,
							@Param("approvalReqId") String approvalReqId,
							@Param("approvalDivCd") String approvalDivCd,
							@Param("lineIndex") int lineIndex);

	/* 결재라인 결재 처리(결재여부/사유/문서/일시 등 업데이트) */
	int updateApprovalLine(UpdateApprovalLineParam p);

	/* 결재라인 적용대상(BEFORE_APPLY_YN) 플래그 설정 */
	int setBeforeApplyYn(@Param("approvalReqId") String approvalReqId, @Param("lineIndex") int lineIndex);

	/* 결재요청 결재일시 및 권한(auth) 업데이트 */
	int updateApprovalReqApprovalDateAndAuth(UpdateApprovalReqParam p);

	/* 결재라인 결재자명 업데이트 */
	int updateApprovalLineUserNm(@Param("approvalReqId") String approvalReqId, @Param("lineIndex") int lineIndex, @Param("userNm") String userNm);

	/* 사용자 정보 존재 여부 카운트 */
	int countUserInfo(@Param("userCd") String userCd);

	/* 사용자 정보 신규 등록 */
	int insertUserInfo(InsertUserInfoParam p);

	/* 사용자 로그인/조직/만료 등 정보 갱신 */
	int updateUserInfoLogin(UpdateUserInfoLoginParam p);

	/* 사용자 멤버(그룹) 정보 삭제 */
	int deleteUserMember(@Param("userCd") String userCd);

	/* 사용자 멤버(그룹) 정보 추가 */
	int insertUserMember(@Param("userCd") String userCd, @Param("userGrpId") String userGrpId);

	/* 마스킹 처리 이력 등록 */
	int insertMaskHistory(InsertMaskHistoryParam p);

	/* 벌크잡 요청 적용여부(APPLY_YN) 일괄 업데이트 */
	int updateBulkJobApplyYn(@Param("approvalReqId") String approvalReqId);

	class UpdateApprovalLineParam {
		private String approvalYn;
		private String approvalRejectDocu;
		private String approvalReqDocu;
		private String approvalDate;
		private String userCd;
		private String approvalReqId;
		private String approvalDivCd;
		private String approvalLineUserId;
		private String whereApprovalLineUserId;
		private int approvalLineIndex;
		private String approvalLineUserNm;
		public String getApprovalYn() { return approvalYn; }
		public void setApprovalYn(String approvalYn) { this.approvalYn = approvalYn; }
		public String getApprovalRejectDocu() { return approvalRejectDocu; }
		public void setApprovalRejectDocu(String approvalRejectDocu) { this.approvalRejectDocu = approvalRejectDocu; }
		public String getApprovalReqDocu() { return approvalReqDocu; }
		public void setApprovalReqDocu(String approvalReqDocu) { this.approvalReqDocu = approvalReqDocu; }
		public String getApprovalDate() { return approvalDate; }
		public void setApprovalDate(String approvalDate) { this.approvalDate = approvalDate; }
		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getApprovalReqId() { return approvalReqId; }
		public void setApprovalReqId(String approvalReqId) { this.approvalReqId = approvalReqId; }
		public String getApprovalDivCd() { return approvalDivCd; }
		public void setApprovalDivCd(String approvalDivCd) { this.approvalDivCd = approvalDivCd; }
		public String getApprovalLineUserId() { return approvalLineUserId; }
		public void setApprovalLineUserId(String approvalLineUserId) { this.approvalLineUserId = approvalLineUserId; }
		public String getWhereApprovalLineUserId() { return whereApprovalLineUserId; }
		public void setWhereApprovalLineUserId(String whereApprovalLineUserId) { this.whereApprovalLineUserId = whereApprovalLineUserId; }
		public int getApprovalLineIndex() { return approvalLineIndex; }
		public void setApprovalLineIndex(int approvalLineIndex) { this.approvalLineIndex = approvalLineIndex; }
		public String getApprovalLineUserNm() { return approvalLineUserNm; }
		public void setApprovalLineUserNm(String approvalLineUserNm) { this.approvalLineUserNm = approvalLineUserNm; }
	}

	class UpdateApprovalReqParam {
		private String approvalDate;
		private String auth;
		private String userCd;
		private String approvalDivCd;
		private String approvalReqId;
		public String getApprovalDate() { return approvalDate; }
		public void setApprovalDate(String approvalDate) { this.approvalDate = approvalDate; }
		public String getAuth() { return auth; }
		public void setAuth(String auth) { this.auth = auth; }
		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getApprovalDivCd() { return approvalDivCd; }
		public void setApprovalDivCd(String approvalDivCd) { this.approvalDivCd = approvalDivCd; }
		public String getApprovalReqId() { return approvalReqId; }
		public void setApprovalReqId(String approvalReqId) { this.approvalReqId = approvalReqId; }
	}

	class InsertUserInfoParam {
		private String userCd;
		private String userNm;
		private String password;
		private String loginDate;
		private String brc;
		private String brnm;
		private String ipAddress;
		private String expireDate;
		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getUserNm() { return userNm; }
		public void setUserNm(String userNm) { this.userNm = userNm; }
		public String getPassword() { return password; }
		public void setPassword(String password) { this.password = password; }
		public String getLoginDate() { return loginDate; }
		public void setLoginDate(String loginDate) { this.loginDate = loginDate; }
		public String getBrc() { return brc; }
		public void setBrc(String brc) { this.brc = brc; }
		public String getBrnm() { return brnm; }
		public void setBrnm(String brnm) { this.brnm = brnm; }
		public String getIpAddress() { return ipAddress; }
		public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
		public String getExpireDate() { return expireDate; }
		public void setExpireDate(String expireDate) { this.expireDate = expireDate; }
	}

	class UpdateUserInfoLoginParam {
		private String userCd;
		private String loginDate;
		private String brc;
		private String brnm;
		private String ipAddress;
		private String expireDate;
		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getLoginDate() { return loginDate; }
		public void setLoginDate(String loginDate) { this.loginDate = loginDate; }
		public String getBrc() { return brc; }
		public void setBrc(String brc) { this.brc = brc; }
		public String getBrnm() { return brnm; }
		public void setBrnm(String brnm) { this.brnm = brnm; }
		public String getIpAddress() { return ipAddress; }
		public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
		public String getExpireDate() { return expireDate; }
		public void setExpireDate(String expireDate) { this.expireDate = expireDate; }
	}

	class InsertMaskHistoryParam {
		private String userCd;
		private String reqDate;
		private String approvalReqId;
		private String userNm;
		private String programId;
		private String programNm;
		private String procDate;
		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getReqDate() { return reqDate; }
		public void setReqDate(String reqDate) { this.reqDate = reqDate; }
		public String getApprovalReqId() { return approvalReqId; }
		public void setApprovalReqId(String approvalReqId) { this.approvalReqId = approvalReqId; }
		public String getUserNm() { return userNm; }
		public void setUserNm(String userNm) { this.userNm = userNm; }
		public String getProgramId() { return programId; }
		public void setProgramId(String programId) { this.programId = programId; }
		public String getProgramNm() { return programNm; }
		public void setProgramNm(String programNm) { this.programNm = programNm; }
		public String getProcDate() { return procDate; }
		public void setProcDate(String procDate) { this.procDate = procDate; }
	}
}

