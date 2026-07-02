package com.archiving.asset.dto;

public class DaemonDto {
	private String daemonId;
	private String daemonCd;
	private String daemonNm;
	private String daemonDesc;
	private String daemonStatCd;
	private String daemonRestartYn;
	private String daemonUseYn;
	private String daemonStatNm;
	private String daemonStartPath;
	private String daemonStopPath;
	private String daemonRestartPath;
	private String heartbeatTime;

	public String getDaemonId() { return daemonId; }
	public void setDaemonId(String daemonId) { this.daemonId = daemonId; }
	public String getDaemonCd() { return daemonCd; }
	public void setDaemonCd(String daemonCd) { this.daemonCd = daemonCd; }
	public String getDaemonNm() { return daemonNm; }
	public void setDaemonNm(String daemonNm) { this.daemonNm = daemonNm; }
	public String getDaemonDesc() { return daemonDesc; }
	public void setDaemonDesc(String daemonDesc) { this.daemonDesc = daemonDesc; }
	public String getDaemonStatCd() { return daemonStatCd; }
	public void setDaemonStatCd(String daemonStatCd) { this.daemonStatCd = daemonStatCd; }
	public String getDaemonRestartYn() { return daemonRestartYn; }
	public void setDaemonRestartYn(String daemonRestartYn) { this.daemonRestartYn = daemonRestartYn; }
	public String getDaemonUseYn() { return daemonUseYn; }
	public void setDaemonUseYn(String daemonUseYn) { this.daemonUseYn = daemonUseYn; }
	public String getDaemonStatNm() { return daemonStatNm; }
	public void setDaemonStatNm(String daemonStatNm) { this.daemonStatNm = daemonStatNm; }
	public String getDaemonStartPath() { return daemonStartPath; }
	public void setDaemonStartPath(String daemonStartPath) { this.daemonStartPath = daemonStartPath; }
	public String getDaemonStopPath() { return daemonStopPath; }
	public void setDaemonStopPath(String daemonStopPath) { this.daemonStopPath = daemonStopPath; }
	public String getDaemonRestartPath() { return daemonRestartPath; }
	public void setDaemonRestartPath(String daemonRestartPath) { this.daemonRestartPath = daemonRestartPath; }
	public String getHeartbeatTime() { return heartbeatTime; }
	public void setHeartbeatTime(String heartbeatTime) { this.heartbeatTime = heartbeatTime; }
}

