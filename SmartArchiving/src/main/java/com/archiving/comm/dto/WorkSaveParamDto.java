package com.archiving.comm.dto;

public class WorkSaveParamDto {
	private String crud;
	private String server_id;
	private String work_cd;
	private String work_nm;
	private String account_cd;
	private String description;
	private String use_yn;

	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
	public String getServer_id() { return server_id; }
	public void setServer_id(String server_id) { this.server_id = server_id; }
	public String getWork_cd() { return work_cd; }
	public void setWork_cd(String work_cd) { this.work_cd = work_cd; }
	public String getWork_nm() { return work_nm; }
	public void setWork_nm(String work_nm) { this.work_nm = work_nm; }
	public String getAccount_cd() { return account_cd; }
	public void setAccount_cd(String account_cd) { this.account_cd = account_cd; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getUse_yn() { return use_yn; }
	public void setUse_yn(String use_yn) { this.use_yn = use_yn; }
}

