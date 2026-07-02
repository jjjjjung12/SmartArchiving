package com.archiving.bulkjob.dto;

public class ServiceInfoRowDto {
	private String applicationGroupId;
	private String serviceId;
	private String selGubun;
	private String delYn;

	public String getApplicationGroupId() { return applicationGroupId; }
	public void setApplicationGroupId(String applicationGroupId) { this.applicationGroupId = applicationGroupId; }
	public String getServiceId() { return serviceId; }
	public void setServiceId(String serviceId) { this.serviceId = serviceId; }
	public String getSelGubun() { return selGubun; }
	public void setSelGubun(String selGubun) { this.selGubun = selGubun; }
	public String getDelYn() { return delYn; }
	public void setDelYn(String delYn) { this.delYn = delYn; }
}

