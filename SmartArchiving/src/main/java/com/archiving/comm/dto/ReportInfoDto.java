package com.archiving.comm.dto;

public class ReportInfoDto {
	private String reportId;
	private String reportCd;
	private String reportNm;
	private String description;
	private String reportType;
	private String detailPage;

	public String getReportId() { return reportId; }
	public void setReportId(String reportId) { this.reportId = reportId; }
	public String getReportCd() { return reportCd; }
	public void setReportCd(String reportCd) { this.reportCd = reportCd; }
	public String getReportNm() { return reportNm; }
	public void setReportNm(String reportNm) { this.reportNm = reportNm; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getReportType() { return reportType; }
	public void setReportType(String reportType) { this.reportType = reportType; }
	public String getDetailPage() { return detailPage; }
	public void setDetailPage(String detailPage) { this.detailPage = detailPage; }
}

