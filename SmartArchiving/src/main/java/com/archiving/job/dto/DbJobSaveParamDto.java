package com.archiving.job.dto;

public class DbJobSaveParamDto {
	private String src_server_id;
	private String des_server_id;
	private String job_id;
	private String daemon_id;
	private String job_nm;
	private String src_table_id;
	private String des_table_id;
	private String src_table_nm;
	private String des_file_path;
	private String des_flag_path;
	private String base_date;
	private String delimiter;
	private String daemon_restart_yn;
	private String job_schedule;
	private String job_type;
	private String use_yn;
	private String crud;

	public String getSrc_server_id() { return src_server_id; }
	public void setSrc_server_id(String src_server_id) { this.src_server_id = src_server_id; }
	public String getDes_server_id() { return des_server_id; }
	public void setDes_server_id(String des_server_id) { this.des_server_id = des_server_id; }
	public String getJob_id() { return job_id; }
	public void setJob_id(String job_id) { this.job_id = job_id; }
	public String getDaemon_id() { return daemon_id; }
	public void setDaemon_id(String daemon_id) { this.daemon_id = daemon_id; }
	public String getJob_nm() { return job_nm; }
	public void setJob_nm(String job_nm) { this.job_nm = job_nm; }
	public String getSrc_table_id() { return src_table_id; }
	public void setSrc_table_id(String src_table_id) { this.src_table_id = src_table_id; }
	public String getDes_table_id() { return des_table_id; }
	public void setDes_table_id(String des_table_id) { this.des_table_id = des_table_id; }
	public String getSrc_table_nm() { return src_table_nm; }
	public void setSrc_table_nm(String src_table_nm) { this.src_table_nm = src_table_nm; }
	public String getDes_file_path() { return des_file_path; }
	public void setDes_file_path(String des_file_path) { this.des_file_path = des_file_path; }
	public String getDes_flag_path() { return des_flag_path; }
	public void setDes_flag_path(String des_flag_path) { this.des_flag_path = des_flag_path; }
	public String getBase_date() { return base_date; }
	public void setBase_date(String base_date) { this.base_date = base_date; }
	public String getDelimiter() { return delimiter; }
	public void setDelimiter(String delimiter) { this.delimiter = delimiter; }
	public String getDaemon_restart_yn() { return daemon_restart_yn; }
	public void setDaemon_restart_yn(String daemon_restart_yn) { this.daemon_restart_yn = daemon_restart_yn; }
	public String getJob_schedule() { return job_schedule; }
	public void setJob_schedule(String job_schedule) { this.job_schedule = job_schedule; }
	public String getJob_type() { return job_type; }
	public void setJob_type(String job_type) { this.job_type = job_type; }
	public String getUse_yn() { return use_yn; }
	public void setUse_yn(String use_yn) { this.use_yn = use_yn; }
	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
}

