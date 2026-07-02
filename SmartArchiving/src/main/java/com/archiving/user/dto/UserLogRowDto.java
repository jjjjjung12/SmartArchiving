package com.archiving.user.dto;

public class UserLogRowDto {
	private Long logId;
	private String actionTime;
	private String userName;
	private String programNm;
	private String actionDate;
	private Integer groupId;
	private Integer userId;
	private String userCd;
	private String programId;
	private String programWhere;

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getActionTime() {
		return actionTime;
	}

	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProgramNm() {
		return programNm;
	}

	public void setProgramNm(String programNm) {
		this.programNm = programNm;
	}

	public String getActionDate() {
		return actionDate;
	}

	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserCd() {
		return userCd;
	}

	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getProgramWhere() {
		return programWhere;
	}

	public void setProgramWhere(String programWhere) {
		this.programWhere = programWhere;
	}
}
