package com.archiving.comm.dto;

public class WorkRowDto {
	private String serverId;
	private String serverNm;
	private String workCd;
	private String workNm;
	private String accountCd;
	private String description;
	private String useYn;

	public String getServerId() { return serverId; }
	public void setServerId(String serverId) { this.serverId = serverId; }
	public String getServerNm() { return serverNm; }
	public void setServerNm(String serverNm) { this.serverNm = serverNm; }
	public String getWorkCd() { return workCd; }
	public void setWorkCd(String workCd) { this.workCd = workCd; }
	public String getWorkNm() { return workNm; }
	public void setWorkNm(String workNm) { this.workNm = workNm; }
	public String getAccountCd() { return accountCd; }
	public void setAccountCd(String accountCd) { this.accountCd = accountCd; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getUseYn() { return useYn; }
	public void setUseYn(String useYn) { this.useYn = useYn; }
}

