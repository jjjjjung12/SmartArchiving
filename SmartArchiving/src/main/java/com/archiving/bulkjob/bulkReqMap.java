package com.archiving.bulkjob;

import com.fasterxml.jackson.annotation.JsonProperty;
public class bulkReqMap {
	
//	public bulkReqMap() {}
	
	//@JsonProperty("fromDate")
	String fromDate;
	//@JsonProperty("toDate")
	String toDate;
	//@JsonProperty("accountNo")
	String accountNo;
	//@JsonProperty("userId")
	String userId;
	//@JsonProperty("custRegNo")
	String custRegNo;
	//@JsonProperty("telNo")
	String telNo;
	//@JsonProperty("userIp")
	String userIp;

	/* 벌크 요청 시작일(fromDate) 반환 */
	public String getFromDate() {
		return fromDate;
	}

	/* 벌크 요청 시작일(fromDate) 설정 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/* 벌크 요청 종료일(toDate) 반환 */
	public String getToDate() {
		return toDate;
	}

	/* 벌크 요청 종료일(toDate) 설정 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/* 계좌번호 반환 */
	public String getAccountNo() {
		return accountNo;
	}

	/* 계좌번호 설정 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/* 사용자 ID 반환 */
	public String getUserId() {
		return userId;
	}

	/* 사용자 ID 설정 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/* 고객등록번호 반환 */
	public String getCustRegNo() {
		return custRegNo;
	}

	/* 고객등록번호 설정 */
	public void setCustRegNo(String custRegNo) {
		this.custRegNo = custRegNo;
	}

	/* 전화번호 반환 */
	public String getTelNo() {
		return telNo;
	}

	/* 전화번호 설정 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/* 사용자 IP 반환 */
	public String getUserIp() {
		return userIp;
	}

	/* 사용자 IP 설정 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	
	/* 디버깅용 문자열 반환 */
	@Override
	public String toString() {
		return "bulkReqMap {fromDate=" + fromDate + ", toDate=" + toDate + ", accountNo=" + accountNo + ", userId="
				+ userId + ", custRegNo=" + custRegNo + ", telNo=" + telNo + ", userIp=" + userIp + "}";
	}

	

}
