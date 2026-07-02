package com.archiving.magicview.dto;

public class IlogIpBandQuery {
	private String serverValue = "bl";
	private String trYmdStart;
	private String trYmdEnd;
	private String ipStart;
	private String ipEnd;

	public String getServerValue() { return serverValue; }
	public void setServerValue(String serverValue) { this.serverValue = serverValue; }
	public String getTrYmdStart() { return trYmdStart; }
	public void setTrYmdStart(String trYmdStart) { this.trYmdStart = trYmdStart; }
	public String getTrYmdEnd() { return trYmdEnd; }
	public void setTrYmdEnd(String trYmdEnd) { this.trYmdEnd = trYmdEnd; }
	public String getIpStart() { return ipStart; }
	public void setIpStart(String ipStart) { this.ipStart = ipStart; }
	public String getIpEnd() { return ipEnd; }
	public void setIpEnd(String ipEnd) { this.ipEnd = ipEnd; }
}

