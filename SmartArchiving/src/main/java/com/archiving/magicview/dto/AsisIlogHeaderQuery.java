package com.archiving.magicview.dto;

/**
 * {@code GetASISIlogHeader} 검색 조건.
 */
public class AsisIlogHeaderQuery {

	private String ymdStart;
	private String ymdEnd;
	private String channelCode;
	private String userId;
	private String trAcno;
	private String cusNo;
	private String inAcno;
	private String ip;

	public String getYmdStart() {
		return ymdStart;
	}

	public void setYmdStart(String ymdStart) {
		this.ymdStart = ymdStart;
	}

	public String getYmdEnd() {
		return ymdEnd;
	}

	public void setYmdEnd(String ymdEnd) {
		this.ymdEnd = ymdEnd;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTrAcno() {
		return trAcno;
	}

	public void setTrAcno(String trAcno) {
		this.trAcno = trAcno;
	}

	public String getCusNo() {
		return cusNo;
	}

	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}

	public String getInAcno() {
		return inAcno;
	}

	public void setInAcno(String inAcno) {
		this.inAcno = inAcno;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
