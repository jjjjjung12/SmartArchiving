package com.archiving.asset.dto;

public class AgentDto {
	private String serverId;
	private String agentId;
	private String agentNm;
	private String agentPort;
	private String serverNm;
	private String serverIp;
	private String accountCd;
	private String password;
	private String path;
	private String description;
	private String runCd;
	private String runNm;
	private String useYn;

	public String getServerId() { return serverId; }
	public void setServerId(String serverId) { this.serverId = serverId; }
	public String getAgentId() { return agentId; }
	public void setAgentId(String agentId) { this.agentId = agentId; }
	public String getAgentNm() { return agentNm; }
	public void setAgentNm(String agentNm) { this.agentNm = agentNm; }
	public String getAgentPort() { return agentPort; }
	public void setAgentPort(String agentPort) { this.agentPort = agentPort; }
	public String getServerNm() { return serverNm; }
	public void setServerNm(String serverNm) { this.serverNm = serverNm; }
	public String getServerIp() { return serverIp; }
	public void setServerIp(String serverIp) { this.serverIp = serverIp; }
	public String getAccountCd() { return accountCd; }
	public void setAccountCd(String accountCd) { this.accountCd = accountCd; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getRunCd() { return runCd; }
	public void setRunCd(String runCd) { this.runCd = runCd; }
	public String getRunNm() { return runNm; }
	public void setRunNm(String runNm) { this.runNm = runNm; }
	public String getUseYn() { return useYn; }
	public void setUseYn(String useYn) { this.useYn = useYn; }
}

