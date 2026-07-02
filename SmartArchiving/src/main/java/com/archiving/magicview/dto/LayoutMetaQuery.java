package com.archiving.magicview.dto;

public class LayoutMetaQuery {
	private String serverValue = "bl";
	private String trxDtime;
	private String messageId;
	private String messageRes;
	private String reqResType;

	public String getServerValue() { return serverValue; }
	public void setServerValue(String serverValue) { this.serverValue = serverValue; }
	public String getTrxDtime() { return trxDtime; }
	public void setTrxDtime(String trxDtime) { this.trxDtime = trxDtime; }
	public String getMessageId() { return messageId; }
	public void setMessageId(String messageId) { this.messageId = messageId; }
	public String getMessageRes() { return messageRes; }
	public void setMessageRes(String messageRes) { this.messageRes = messageRes; }
	public String getReqResType() { return reqResType; }
	public void setReqResType(String reqResType) { this.reqResType = reqResType; }
}

