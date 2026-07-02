package com.archiving.magicview.dto;

public class SlogHeaderQuery {
	private String serverValue = "bl";
	private String trYmdStart;
	private String trYmdEnd;
	private String serviceId;
	private String serviceName;
	private String mediaCategoryCd;

	public String getServerValue() { return serverValue; }
	public void setServerValue(String serverValue) { this.serverValue = serverValue; }
	public String getTrYmdStart() { return trYmdStart; }
	public void setTrYmdStart(String trYmdStart) { this.trYmdStart = trYmdStart; }
	public String getTrYmdEnd() { return trYmdEnd; }
	public void setTrYmdEnd(String trYmdEnd) { this.trYmdEnd = trYmdEnd; }
	public String getServiceId() { return serviceId; }
	public void setServiceId(String serviceId) { this.serviceId = serviceId; }
	public String getServiceName() { return serviceName; }
	public void setServiceName(String serviceName) { this.serviceName = serviceName; }
	public String getMediaCategoryCd() { return mediaCategoryCd; }
	public void setMediaCategoryCd(String mediaCategoryCd) { this.mediaCategoryCd = mediaCategoryCd; }
}

