package com.archiving.sql.dto;

public class BatchSetParamDto {
	private String __job_id;
	private String __job_nm;
	private String __job_path;
	private String __job_user_id;
	private String __src_sql;
	private String __job_status_cd;

	public String get__job_id() { return __job_id; }
	public void set__job_id(String __job_id) { this.__job_id = __job_id; }
	public String get__job_nm() { return __job_nm; }
	public void set__job_nm(String __job_nm) { this.__job_nm = __job_nm; }
	public String get__job_path() { return __job_path; }
	public void set__job_path(String __job_path) { this.__job_path = __job_path; }
	public String get__job_user_id() { return __job_user_id; }
	public void set__job_user_id(String __job_user_id) { this.__job_user_id = __job_user_id; }
	public String get__src_sql() { return __src_sql; }
	public void set__src_sql(String __src_sql) { this.__src_sql = __src_sql; }
	public String get__job_status_cd() { return __job_status_cd; }
	public void set__job_status_cd(String __job_status_cd) { this.__job_status_cd = __job_status_cd; }
}

