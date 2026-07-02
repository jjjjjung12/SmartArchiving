package com.archiving.bulkjob.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceInfoParamDto {
	/** JSON key must match JS ({@code F_SP_...}); Jackson otherwise maps getter to {@code f_SP_...}. */
	@JsonProperty("F_SP_APPLICATION_GROUP_ID")
	private String F_SP_APPLICATION_GROUP_ID;
	@JsonProperty("__rows")
	private String __rows;
	@JsonProperty("__page")
	private String __page;

	public String getF_SP_APPLICATION_GROUP_ID() { return F_SP_APPLICATION_GROUP_ID; }
	public void setF_SP_APPLICATION_GROUP_ID(String f_SP_APPLICATION_GROUP_ID) { F_SP_APPLICATION_GROUP_ID = f_SP_APPLICATION_GROUP_ID; }
	public String get__rows() { return __rows; }
	public void set__rows(String __rows) { this.__rows = __rows; }
	public String get__page() { return __page; }
	public void set__page(String __page) { this.__page = __page; }
}

