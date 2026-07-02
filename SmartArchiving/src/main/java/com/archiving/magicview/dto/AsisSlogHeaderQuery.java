package com.archiving.magicview.dto;

/**
 * {@code AsisSlogHeaderController} 검색 조건.
 */
public class AsisSlogHeaderQuery {

	/** bl(은행) / sl(상호) */
	private String serverValue = "bl";

	private String trYmdStart;
	private String trYmdEnd;
	private String medDs;
	private String svcId;
	private String os;
	private String locale;

	public String getTrYmdStart() {
		return trYmdStart;
	}

	public void setTrYmdStart(String trYmdStart) {
		this.trYmdStart = trYmdStart;
	}

	public String getTrYmdEnd() {
		return trYmdEnd;
	}

	public void setTrYmdEnd(String trYmdEnd) {
		this.trYmdEnd = trYmdEnd;
	}

	public String getMedDs() {
		return medDs;
	}

	public void setMedDs(String medDs) {
		this.medDs = medDs;
	}

	public String getSvcId() {
		return svcId;
	}

	public void setSvcId(String svcId) {
		this.svcId = svcId;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getServerValue() {
		return serverValue;
	}

	public void setServerValue(String serverValue) {
		this.serverValue = serverValue;
	}
}
