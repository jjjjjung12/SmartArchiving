package com.archiving.sql.dto;

public class BatchListSaveParamDto {
	private String job_id;
	private String job_nm;
	private String job_type_cd;
	private String job_path;
	private String job_order;
	private String job_status_cd;
	private String job_user_id;
	private String job_tm;
	private String job_worker;
	private String description;
	private String src_sql;
	private String use_yn;
	private String crud;

	public String getJob_id() { return job_id; }
	public void setJob_id(String job_id) { this.job_id = job_id; }
	public String getJob_nm() { return job_nm; }
	public void setJob_nm(String job_nm) { this.job_nm = job_nm; }
	public String getJob_type_cd() { return job_type_cd; }
	public void setJob_type_cd(String job_type_cd) { this.job_type_cd = job_type_cd; }
	public String getJob_path() { return job_path; }
	public void setJob_path(String job_path) { this.job_path = job_path; }
	public String getJob_order() { return job_order; }
	public void setJob_order(String job_order) { this.job_order = job_order; }
	public String getJob_status_cd() { return job_status_cd; }
	public void setJob_status_cd(String job_status_cd) { this.job_status_cd = job_status_cd; }
	public String getJob_user_id() { return job_user_id; }
	public void setJob_user_id(String job_user_id) { this.job_user_id = job_user_id; }
	public String getJob_tm() { return job_tm; }
	public void setJob_tm(String job_tm) { this.job_tm = job_tm; }
	public String getJob_worker() { return job_worker; }
	public void setJob_worker(String job_worker) { this.job_worker = job_worker; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getSrc_sql() { return src_sql; }
	public void setSrc_sql(String src_sql) { this.src_sql = src_sql; }
	public String getUse_yn() { return use_yn; }
	public void setUse_yn(String use_yn) { this.use_yn = use_yn; }
	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
}

