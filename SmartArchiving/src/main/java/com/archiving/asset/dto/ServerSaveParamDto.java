package com.archiving.asset.dto;

public class ServerSaveParamDto {
	private String server_id;
	private String server_nm;
	private String server_class_cd;
	private String server_ip;
	private String server_desc;
	private String use_yn;
	private String crud;

	public String getServer_id() { return server_id; }
	public void setServer_id(String server_id) { this.server_id = server_id; }
	public String getServer_nm() { return server_nm; }
	public void setServer_nm(String server_nm) { this.server_nm = server_nm; }
	public String getServer_class_cd() { return server_class_cd; }
	public void setServer_class_cd(String server_class_cd) { this.server_class_cd = server_class_cd; }
	public String getServer_ip() { return server_ip; }
	public void setServer_ip(String server_ip) { this.server_ip = server_ip; }
	public String getServer_desc() { return server_desc; }
	public void setServer_desc(String server_desc) { this.server_desc = server_desc; }
	public String getUse_yn() { return use_yn; }
	public void setUse_yn(String use_yn) { this.use_yn = use_yn; }
	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
}

