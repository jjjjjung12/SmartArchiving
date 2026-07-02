package com.archiving.magicview.dto;

public class ServiceReportQuery {
	private String serverValue = "bl";
	private String mediaCategoryCd;
	private String trYmdStart;
	private String trYmdEnd;
	private String selGubun;
	private String delYn;

	public String getServerValue() { return serverValue; }
	public void setServerValue(String serverValue) { this.serverValue = serverValue; }
	public String getMediaCategoryCd() { return mediaCategoryCd; }
	public void setMediaCategoryCd(String mediaCategoryCd) { this.mediaCategoryCd = mediaCategoryCd; }
	public String getTrYmdStart() { return trYmdStart; }
	public void setTrYmdStart(String trYmdStart) { this.trYmdStart = trYmdStart; }
	public String getTrYmdEnd() { return trYmdEnd; }
	public void setTrYmdEnd(String trYmdEnd) { this.trYmdEnd = trYmdEnd; }
	public String getSelGubun() { return selGubun; }
	public void setSelGubun(String selGubun) { this.selGubun = selGubun; }
	public String getDelYn() { return delYn; }
	public void setDelYn(String delYn) { this.delYn = delYn; }
}
