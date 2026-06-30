package com.archiving.util.dto;

/**
 * Maps SCHEDULE + CODEDETAIL join rows used by {@code UtilClass#getIni} / {@code getConfIni}.
 */
public class ScheduleIniRowDto {
	private String jobId;
	private String destServer;
	private String jobSchedule;
	private String srcIp;
	private String srcPort;
	private String srcDbms;
	private String srcDbmsCd;
	private String srcDb;
	private String srcUser;
	private String srcPasswd;
	private String srcSql;
	private String useYn;
	private String destIp;
	private String destPort;
	private String destDbms;
	private String destDbmsCd;
	private String destDb;
	private String destUser;
	private String destPasswd;
	private String jobMethodCd;
	private String destTable;
	private String savePreqCd;
	private String savePreq;
	private String jobTypeCd;
	private String jobClass;
	private String jobPath;
	private String jobSelected;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getDestServer() {
		return destServer;
	}

	public void setDestServer(String destServer) {
		this.destServer = destServer;
	}

	public String getJobSchedule() {
		return jobSchedule;
	}

	public void setJobSchedule(String jobSchedule) {
		this.jobSchedule = jobSchedule;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}

	public String getSrcDbms() {
		return srcDbms;
	}

	public void setSrcDbms(String srcDbms) {
		this.srcDbms = srcDbms;
	}

	public String getSrcDbmsCd() {
		return srcDbmsCd;
	}

	public void setSrcDbmsCd(String srcDbmsCd) {
		this.srcDbmsCd = srcDbmsCd;
	}

	public String getSrcDb() {
		return srcDb;
	}

	public void setSrcDb(String srcDb) {
		this.srcDb = srcDb;
	}

	public String getSrcUser() {
		return srcUser;
	}

	public void setSrcUser(String srcUser) {
		this.srcUser = srcUser;
	}

	public String getSrcPasswd() {
		return srcPasswd;
	}

	public void setSrcPasswd(String srcPasswd) {
		this.srcPasswd = srcPasswd;
	}

	public String getSrcSql() {
		return srcSql;
	}

	public void setSrcSql(String srcSql) {
		this.srcSql = srcSql;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getDestIp() {
		return destIp;
	}

	public void setDestIp(String destIp) {
		this.destIp = destIp;
	}

	public String getDestPort() {
		return destPort;
	}

	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}

	public String getDestDbms() {
		return destDbms;
	}

	public void setDestDbms(String destDbms) {
		this.destDbms = destDbms;
	}

	public String getDestDbmsCd() {
		return destDbmsCd;
	}

	public void setDestDbmsCd(String destDbmsCd) {
		this.destDbmsCd = destDbmsCd;
	}

	public String getDestDb() {
		return destDb;
	}

	public void setDestDb(String destDb) {
		this.destDb = destDb;
	}

	public String getDestUser() {
		return destUser;
	}

	public void setDestUser(String destUser) {
		this.destUser = destUser;
	}

	public String getDestPasswd() {
		return destPasswd;
	}

	public void setDestPasswd(String destPasswd) {
		this.destPasswd = destPasswd;
	}

	public String getJobMethodCd() {
		return jobMethodCd;
	}

	public void setJobMethodCd(String jobMethodCd) {
		this.jobMethodCd = jobMethodCd;
	}

	public String getDestTable() {
		return destTable;
	}

	public void setDestTable(String destTable) {
		this.destTable = destTable;
	}

	public String getSavePreqCd() {
		return savePreqCd;
	}

	public void setSavePreqCd(String savePreqCd) {
		this.savePreqCd = savePreqCd;
	}

	public String getSavePreq() {
		return savePreq;
	}

	public void setSavePreq(String savePreq) {
		this.savePreq = savePreq;
	}

	public String getJobTypeCd() {
		return jobTypeCd;
	}

	public void setJobTypeCd(String jobTypeCd) {
		this.jobTypeCd = jobTypeCd;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobPath() {
		return jobPath;
	}

	public void setJobPath(String jobPath) {
		this.jobPath = jobPath;
	}

	public String getJobSelected() {
		return jobSelected;
	}

	public void setJobSelected(String jobSelected) {
		this.jobSelected = jobSelected;
	}
}
