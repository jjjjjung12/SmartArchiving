package com.archiving.util.dto;

public class ScheduleJobListRowDto {
	private String jobCd;
	private String jobNm;
	private String jobSchedule;

	public String getJobCd() {
		return jobCd;
	}

	public void setJobCd(String jobCd) {
		this.jobCd = jobCd;
	}

	public String getJobNm() {
		return jobNm;
	}

	public void setJobNm(String jobNm) {
		this.jobNm = jobNm;
	}

	public String getJobSchedule() {
		return jobSchedule;
	}

	public void setJobSchedule(String jobSchedule) {
		this.jobSchedule = jobSchedule;
	}
}
