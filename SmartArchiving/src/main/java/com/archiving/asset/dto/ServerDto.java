package com.archiving.asset.dto;

public class ServerDto {
	private String serverId;
	private String serverNm;
	private String serverClassCd;
	private String serverIp;
	private String serverDesc;
	private String useYn;
	private String serverClassNm;

	public String getServerId() { return serverId; }
	public void setServerId(String serverId) { this.serverId = serverId; }
	public String getServerNm() { return serverNm; }
	public void setServerNm(String serverNm) { this.serverNm = serverNm; }
	public String getServerClassCd() { return serverClassCd; }
	public void setServerClassCd(String serverClassCd) { this.serverClassCd = serverClassCd; }
	public String getServerIp() { return serverIp; }
	public void setServerIp(String serverIp) { this.serverIp = serverIp; }
	public String getServerDesc() { return serverDesc; }
	public void setServerDesc(String serverDesc) { this.serverDesc = serverDesc; }
	public String getUseYn() { return useYn; }
	public void setUseYn(String useYn) { this.useYn = useYn; }
	public String getServerClassNm() { return serverClassNm; }
	public void setServerClassNm(String serverClassNm) { this.serverClassNm = serverClassNm; }
}

