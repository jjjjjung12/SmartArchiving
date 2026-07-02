package com.archiving.approve.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAuthApplyDetailParamDto {
	@JsonProperty("APPROVAL_REQ_ID")
	private String APPROVAL_REQ_ID;
	public String getAPPROVAL_REQ_ID() { return APPROVAL_REQ_ID; }
	public void setAPPROVAL_REQ_ID(String aPPROVAL_REQ_ID) { APPROVAL_REQ_ID = aPPROVAL_REQ_ID; }
	
}

