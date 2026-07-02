package com.archiving.comm.dto;

public class NoticeDto {
	private String serialNumber;
	private String regUserCd;
	private String userNm;
	private String subject;
	private String regDate;
	private String regStartDate;
	private String regEndDate;
	private String subjectDetail;
	private String fileNm;
	private String fileUrl;

	public String getSerialNumber() { return serialNumber; }
	public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
	public String getRegUserCd() { return regUserCd; }
	public void setRegUserCd(String regUserCd) { this.regUserCd = regUserCd; }
	public String getUserNm() { return userNm; }
	public void setUserNm(String userNm) { this.userNm = userNm; }
	public String getSubject() { return subject; }
	public void setSubject(String subject) { this.subject = subject; }
	public String getRegDate() { return regDate; }
	public void setRegDate(String regDate) { this.regDate = regDate; }
	public String getRegStartDate() { return regStartDate; }
	public void setRegStartDate(String regStartDate) { this.regStartDate = regStartDate; }
	public String getRegEndDate() { return regEndDate; }
	public void setRegEndDate(String regEndDate) { this.regEndDate = regEndDate; }
	public String getSubjectDetail() { return subjectDetail; }
	public void setSubjectDetail(String subjectDetail) { this.subjectDetail = subjectDetail; }
	public String getFileNm() { return fileNm; }
	public void setFileNm(String fileNm) { this.fileNm = fileNm; }
	public String getFileUrl() { return fileUrl; }
	public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
}

