package com.archiving.magicview.dto;

public class SameTranIdQuery {
	private String serverValue = "bl";
	private String corpValue = "efblview";
	private String detailCorpValue = "1";
	private String trYmdStart;
	private String trYmdEnd;
	private String messageSerNo;

	public String getServerValue() { return serverValue; }
	public void setServerValue(String serverValue) { this.serverValue = serverValue; }
	public String getCorpValue() { return corpValue; }
	public void setCorpValue(String corpValue) { this.corpValue = corpValue; }
	public String getDetailCorpValue() { return detailCorpValue; }
	public void setDetailCorpValue(String detailCorpValue) { this.detailCorpValue = detailCorpValue; }
	public String getTrYmdStart() { return trYmdStart; }
	public void setTrYmdStart(String trYmdStart) { this.trYmdStart = trYmdStart; }
	public String getTrYmdEnd() { return trYmdEnd; }
	public void setTrYmdEnd(String trYmdEnd) { this.trYmdEnd = trYmdEnd; }
	public String getMessageSerNo() { return messageSerNo; }
	public void setMessageSerNo(String messageSerNo) { this.messageSerNo = messageSerNo; }
}

