package com.archiving.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON keys use leading underscores ({@code __gb}); Jackson needs explicit names.
 */
public class UserGroupListParamDto {
	@JsonProperty("__gb")
	private String __gb;
	@JsonProperty("__userGroup_id")
	private String __userGroup_id;
	@JsonProperty("__userGroup_nm")
	private String __userGroup_nm;
	@JsonProperty("__rows")
	private String __rows;
	@JsonProperty("__page")
	private String __page;

	public String get__gb() {
		return __gb;
	}

	public void set__gb(String __gb) {
		this.__gb = __gb;
	}

	public String get__userGroup_id() {
		return __userGroup_id;
	}

	public void set__userGroup_id(String __userGroup_id) {
		this.__userGroup_id = __userGroup_id;
	}

	public String get__userGroup_nm() {
		return __userGroup_nm;
	}

	public void set__userGroup_nm(String __userGroup_nm) {
		this.__userGroup_nm = __userGroup_nm;
	}

	public String get__rows() {
		return __rows;
	}

	public void set__rows(String __rows) {
		this.__rows = __rows;
	}

	public String get__page() {
		return __page;
	}

	public void set__page(String __page) {
		this.__page = __page;
	}
}
