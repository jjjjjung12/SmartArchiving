package com.archiving.user.dto;

public class UserGroupRowDto {
	private String userId;
	private String userNm;
	private String userGrpId;
	private String userGrpNm;
	private String description;
	private String useYn;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getUserGrpId() {
		return userGrpId;
	}

	public void setUserGrpId(String userGrpId) {
		this.userGrpId = userGrpId;
	}

	public String getUserGrpNm() {
		return userGrpNm;
	}

	public void setUserGrpNm(String userGrpNm) {
		this.userGrpNm = userGrpNm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
}
