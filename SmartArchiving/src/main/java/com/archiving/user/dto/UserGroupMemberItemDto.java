package com.archiving.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserGroupMemberItemDto {
	@JsonProperty("user_id")
	private String user_id;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
