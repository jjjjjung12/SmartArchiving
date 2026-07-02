package com.archiving.bulkjob.dto;

public class BulkDataReqRowDto {
	private String userCd;
	private String userNm;
	private String reqDate;
	private String searchDivCd;
	private String searchDivVal;
	private String warrantNum;
	private String startDate;
	private String endDate;
	private String fileNm;
	private String fileUrl;
	private String batchYn;
	private String applyYn;
	private String approvalReqId;

	public String getUserCd() { return userCd; }
	public void setUserCd(String userCd) { this.userCd = userCd; }
	public String getUserNm() { return userNm; }
	public void setUserNm(String userNm) { this.userNm = userNm; }
	public String getReqDate() { return reqDate; }
	public void setReqDate(String reqDate) { this.reqDate = reqDate; }
	public String getSearchDivCd() { return searchDivCd; }
	public void setSearchDivCd(String searchDivCd) { this.searchDivCd = searchDivCd; }
	public String getSearchDivVal() { return searchDivVal; }
	public void setSearchDivVal(String searchDivVal) { this.searchDivVal = searchDivVal; }
	public String getWarrantNum() { return warrantNum; }
	public void setWarrantNum(String warrantNum) { this.warrantNum = warrantNum; }
	public String getStartDate() { return startDate; }
	public void setStartDate(String startDate) { this.startDate = startDate; }
	public String getEndDate() { return endDate; }
	public void setEndDate(String endDate) { this.endDate = endDate; }
	public String getFileNm() { return fileNm; }
	public void setFileNm(String fileNm) { this.fileNm = fileNm; }
	public String getFileUrl() { return fileUrl; }
	public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
	public String getBatchYn() { return batchYn; }
	public void setBatchYn(String batchYn) { this.batchYn = batchYn; }
	public String getApplyYn() { return applyYn; }
	public void setApplyYn(String applyYn) { this.applyYn = applyYn; }
	public String getApprovalReqId() { return approvalReqId; }
	public void setApprovalReqId(String approvalReqId) { this.approvalReqId = approvalReqId; }
}

