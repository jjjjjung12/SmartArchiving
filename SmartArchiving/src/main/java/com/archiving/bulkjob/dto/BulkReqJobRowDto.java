package com.archiving.bulkjob.dto;

public class BulkReqJobRowDto {
	private String startDate;
	private String endDate;
	private String accountNo;
	private String userId;
	private String custRegNo;
	private String telNo;
	private String userIp;
	private String orgFileNm;
	private String uFileNm;
	private String fileUrl;
	private String batchYn;
	private String applyYn;
	private String approvalReqId;

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
	public String getFileUrl() { return fileUrl; }
	public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
	public String getBatchYn() { return batchYn; }
	public void setBatchYn(String batchYn) { this.batchYn = batchYn; }
	public String getApplyYn() { return applyYn; }
	public void setApplyYn(String applyYn) { this.applyYn = applyYn; }
	public String getApprovalReqId() { return approvalReqId; }
	public void setApprovalReqId(String approvalReqId) { this.approvalReqId = approvalReqId; }
}

