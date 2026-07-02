package com.archiving.asset.dto;

public class AgentSaveParamDto {
	private String server_id;
	private String agent_id;
	private String agent_nm;
	private String agent_port;
	private String account_cd;
	private String password;
	private String path;
	private String description;
	private String run_cd;
	private String use_yn;
	private String crud;

	public String getServer_id() { return server_id; }
	public void setServer_id(String server_id) { this.server_id = server_id; }
	public String getAgent_id() { return agent_id; }
	public void setAgent_id(String agent_id) { this.agent_id = agent_id; }
	public String getAgent_nm() { return agent_nm; }
	public void setAgent_nm(String agent_nm) { this.agent_nm = agent_nm; }
	public String getAgent_port() { return agent_port; }
	public void setAgent_port(String agent_port) { this.agent_port = agent_port; }
	public String getAccount_cd() { return account_cd; }
	public void setAccount_cd(String account_cd) { this.account_cd = account_cd; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getRun_cd() { return run_cd; }
	public void setRun_cd(String run_cd) { this.run_cd = run_cd; }
	public String getUse_yn() { return use_yn; }
	public void setUse_yn(String use_yn) { this.use_yn = use_yn; }
	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
}

