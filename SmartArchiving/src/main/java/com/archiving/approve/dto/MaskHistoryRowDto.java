package com.archiving.approve.dto;

public class MaskHistoryRowDto {
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

