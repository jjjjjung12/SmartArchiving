package com.archiving.sql.dto;

public class SqlListParamDto {
	private String __job_id;
	private String __job_nm;
	/** Used by {@code GetJobList} / {@code GetEttSrcJobList} (load job master filtering). */
	private String __server_id;
	private String __use_yn;
	private String __rows;
	private String __page;

	public String get__job_id() { return __job_id; }
	public void set__job_id(String __job_id) { this.__job_id = __job_id; }
	public String get__job_nm() { return __job_nm; }
	public void set__job_nm(String __job_nm) { this.__job_nm = __job_nm; }
	public String get__server_id() { return __server_id; }
	public void set__server_id(String __server_id) { this.__server_id = __server_id; }
	public String get__use_yn() { return __use_yn; }
	public void set__use_yn(String __use_yn) { this.__use_yn = __use_yn; }
	public String get__rows() { return __rows; }
	public void set__rows(String __rows) { this.__rows = __rows; }
	public String get__page() { return __page; }
	public void set__page(String __page) { this.__page = __page; }
}

