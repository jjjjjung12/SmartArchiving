package com.archiving.bulkjob.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Params from bulk inquiry list JS; {@code F_SP_*} JSON keys need explicit {@code @JsonProperty} (Jackson bean name {@code f_SP_*}).
 */
public class BulkInquiryReqListParamDto {
	@JsonProperty("F_SP_REQ_YMD")
	private String F_SP_REQ_YMD;
	@JsonProperty("F_SP_WARRANT_NUM")
	private String F_SP_WARRANT_NUM;
	@JsonProperty("F_SP_START_TR_YMD")
	private String F_SP_START_TR_YMD;
	@JsonProperty("F_SP_END_TR_YMD")
	private String F_SP_END_TR_YMD;
	@JsonProperty("F_SP_SEARCH_DIV_CD")
	private String F_SP_SEARCH_DIV_CD;
	@JsonProperty("F_SP_USER_CD")
	private String F_SP_USER_CD;
	@JsonProperty("__rows")
	private String __rows;
	@JsonProperty("__page")
	private String __page;

	public String getF_SP_REQ_YMD() { return F_SP_REQ_YMD; }
	public void setF_SP_REQ_YMD(String f_SP_REQ_YMD) { F_SP_REQ_YMD = f_SP_REQ_YMD; }
	public String getF_SP_WARRANT_NUM() { return F_SP_WARRANT_NUM; }
	public void setF_SP_WARRANT_NUM(String f_SP_WARRANT_NUM) { F_SP_WARRANT_NUM = f_SP_WARRANT_NUM; }
	public String getF_SP_START_TR_YMD() { return F_SP_START_TR_YMD; }
	public void setF_SP_START_TR_YMD(String f_SP_START_TR_YMD) { F_SP_START_TR_YMD = f_SP_START_TR_YMD; }
	public String getF_SP_END_TR_YMD() { return F_SP_END_TR_YMD; }
	public void setF_SP_END_TR_YMD(String f_SP_END_TR_YMD) { F_SP_END_TR_YMD = f_SP_END_TR_YMD; }
	public String getF_SP_SEARCH_DIV_CD() { return F_SP_SEARCH_DIV_CD; }
	public void setF_SP_SEARCH_DIV_CD(String f_SP_SEARCH_DIV_CD) { F_SP_SEARCH_DIV_CD = f_SP_SEARCH_DIV_CD; }
	public String getF_SP_USER_CD() { return F_SP_USER_CD; }
	public void setF_SP_USER_CD(String f_SP_USER_CD) { F_SP_USER_CD = f_SP_USER_CD; }
	public String get__rows() { return __rows; }
	public void set__rows(String __rows) { this.__rows = __rows; }
	public String get__page() { return __page; }
	public void set__page(String __page) { this.__page = __page; }
}

