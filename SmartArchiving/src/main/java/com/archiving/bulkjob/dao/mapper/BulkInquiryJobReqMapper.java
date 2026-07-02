package com.archiving.bulkjob.dao.mapper;

import org.apache.ibatis.annotations.Param;

public interface BulkInquiryJobReqMapper {
	/* 요청일자 기준 다음 결재요청 ID 생성 */
	String selectNextApprovalReqId(@Param("reqDate") String reqDate);

	/* 벌크잡 요청 등록 */
	int insertBulkJobReq(InsertBulkJobReqParam p);

	/* 결재요청 등록(벌크요청 연동) */
	int insertApprovalReq(InsertApprovalReqParam p);

	/* 결재라인 등록(벌크요청 연동) */
	int insertApprovalLine(InsertApprovalLineParam p);

	class InsertBulkJobReqParam {
		private String approvalReqId;
		private String userCd;
		private String userNm;
		private String startDate;
		private String endDate;
		private String accountNo;
		private String userId;
		private String custRegNo;
		private String telNo;
		private String userIp;
		private String orgFileNm;
		private String uFileNm;
		public String getApprovalReqId() { return approvalReqId; }
		public void setApprovalReqId(String approvalReqId) { this.approvalReqId = approvalReqId; }
		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getUserNm() { return userNm; }
		public void setUserNm(String userNm) { this.userNm = userNm; }
		public String getStartDate() { return startDate; }
		public void setStartDate(String startDate) { this.startDate = startDate; }
		public String getEndDate() { return endDate; }
		public void setEndDate(String endDate) { this.endDate = endDate; }
		public String getAccountNo() { return accountNo; }
		public void setAccountNo(String accountNo) { this.accountNo = accountNo; }
		public String getUserId() { return userId; }
		public void setUserId(String userId) { this.userId = userId; }
		public String getCustRegNo() { return custRegNo; }
		public void setCustRegNo(String custRegNo) { this.custRegNo = custRegNo; }
		public String getTelNo() { return telNo; }
		public void setTelNo(String telNo) { this.telNo = telNo; }
		public String getUserIp() { return userIp; }
		public void setUserIp(String userIp) { this.userIp = userIp; }
		public String getOrgFileNm() { return orgFileNm; }
		public void setOrgFileNm(String orgFileNm) { this.orgFileNm = orgFileNm; }
		public String getUFileNm() { return uFileNm; }
		public void setUFileNm(String uFileNm) { this.uFileNm = uFileNm; }
	}

	class InsertApprovalReqParam {
		private String userCd;
		private String approvalDivCd;
		private String approvalReqId;
		private String name;
		private String approvalReqReason;
		private String reqDate;
		private String brc;
		private String brnm;
		private String oftC;
		private String oft;
		private String programId;
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
		public String getBrc() { return brc; }
		public void setBrc(String brc) { this.brc = brc; }
		public String getBrnm() { return brnm; }
		public void setBrnm(String brnm) { this.brnm = brnm; }
		public String getOftC() { return oftC; }
		public void setOftC(String oftC) { this.oftC = oftC; }
		public String getOft() { return oft; }
		public void setOft(String oft) { this.oft = oft; }
		public String getProgramId() { return programId; }
		public void setProgramId(String programId) { this.programId = programId; }
	}

	class InsertApprovalLineParam {
		private String userCd;
		private String name;
		private String approvalDivCd;
		private String approvalReqId;
		private int approvalLineIndex;
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
		public int getApprovalLineIndex() { return approvalLineIndex; }
		public void setApprovalLineIndex(int approvalLineIndex) { this.approvalLineIndex = approvalLineIndex; }
		public String getApprovalLineUserId() { return approvalLineUserId; }
		public void setApprovalLineUserId(String approvalLineUserId) { this.approvalLineUserId = approvalLineUserId; }
		public String getApprovalLineUserNm() { return approvalLineUserNm; }
		public void setApprovalLineUserNm(String approvalLineUserNm) { this.approvalLineUserNm = approvalLineUserNm; }
		public String getBeforeApplyYn() { return beforeApplyYn; }
		public void setBeforeApplyYn(String beforeApplyYn) { this.beforeApplyYn = beforeApplyYn; }
	}
}

