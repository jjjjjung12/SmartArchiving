package com.archiving.asset.dto;

public class DaemonSaveParamDto {
	private String daemon_id;
	private String daemon_cd;
	private String daemon_nm;
	private String daemon_desc;
	private String daemon_stat_cd;
	private String daemon_restart_yn;
	private String use_yn;
	private String crud;

	private String daemon_start_path;
	private String daemon_stop_path;
	private String daemon_restart_path;

	// legacy fields ignored by current DB insert/update
	private String user_id;

	public String getDaemon_id() { return daemon_id; }
	public void setDaemon_id(String daemon_id) { this.daemon_id = daemon_id; }
	public String getDaemon_cd() { return daemon_cd; }
	public void setDaemon_cd(String daemon_cd) { this.daemon_cd = daemon_cd; }
	public String getDaemon_nm() { return daemon_nm; }
	public void setDaemon_nm(String daemon_nm) { this.daemon_nm = daemon_nm; }
	public String getDaemon_desc() { return daemon_desc; }
	public void setDaemon_desc(String daemon_desc) { this.daemon_desc = daemon_desc; }
	public String getDaemon_stat_cd() { return daemon_stat_cd; }
	public void setDaemon_stat_cd(String daemon_stat_cd) { this.daemon_stat_cd = daemon_stat_cd; }
	public String getDaemon_restart_yn() { return daemon_restart_yn; }
	public void setDaemon_restart_yn(String daemon_restart_yn) { this.daemon_restart_yn = daemon_restart_yn; }
	public String getUse_yn() { return use_yn; }
	public void setUse_yn(String use_yn) { this.use_yn = use_yn; }
	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
	public String getDaemon_start_path() { return daemon_start_path; }
	public void setDaemon_start_path(String daemon_start_path) { this.daemon_start_path = daemon_start_path; }
	public String getDaemon_stop_path() { return daemon_stop_path; }
	public void setDaemon_stop_path(String daemon_stop_path) { this.daemon_stop_path = daemon_stop_path; }
	public String getDaemon_restart_path() { return daemon_restart_path; }
	public void setDaemon_restart_path(String daemon_restart_path) { this.daemon_restart_path = daemon_restart_path; }
	public String getUser_id() { return user_id; }
	public void setUser_id(String user_id) { this.user_id = user_id; }
}

