package com.archiving.comm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TableListParamDto {
	@JsonProperty("__gb")
	private String __gb;
	@JsonProperty("__table_id")
	private String __table_id;
	@JsonProperty("__table_nm")
	private String __table_nm;
	@JsonProperty("__use_yn")
	private String __use_yn;
	@JsonProperty("__rows")
	private String __rows;
	@JsonProperty("__page")
	private String __page;
	@JsonProperty("__serverId")
	private String __serverId;

	public String get__gb() { return __gb; }
	public void set__gb(String __gb) { this.__gb = __gb; }
	public String get__table_id() { return __table_id; }
	public void set__table_id(String __table_id) { this.__table_id = __table_id; }
	public String get__table_nm() { return __table_nm; }
	public void set__table_nm(String __table_nm) { this.__table_nm = __table_nm; }
	public String get__use_yn() { return __use_yn; }
	public void set__use_yn(String __use_yn) { this.__use_yn = __use_yn; }
	public String get__rows() { return __rows; }
	public void set__rows(String __rows) { this.__rows = __rows; }
	public String get__page() { return __page; }
	public void set__page(String __page) { this.__page = __page; }
	public String get__serverId() { return __serverId; }
	public void set__serverId(String __serverId) { this.__serverId = __serverId; }
}
