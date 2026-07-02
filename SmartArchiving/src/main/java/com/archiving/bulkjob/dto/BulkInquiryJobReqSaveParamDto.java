package com.archiving.bulkjob.dto;

public class BulkInquiryJobReqSaveParamDto {
	private String user_cd;
	private String user_nm;
	private String brc;
	private String brmm;
	private String approval_req_id;
	private String oft_c;
	private String oft;
	private String apply_div_cd;
	private String approval_req_reason;
	private String first_user_id;
	private String first_user_nm;
	private String second_user_id;
	private String second_user_nm;
	private String req_date;
	private String page_name;
	private String crud;

	// bulk rows json (stringified array)
	private String bulkReqList;

	public String getUser_cd() { return user_cd; }
	public void setUser_cd(String user_cd) { this.user_cd = user_cd; }
	public String getUser_nm() { return user_nm; }
	public void setUser_nm(String user_nm) { this.user_nm = user_nm; }
	public String getBrc() { return brc; }
	public void setBrc(String brc) { this.brc = brc; }
	public String getBrmm() { return brmm; }
	public void setBrmm(String brmm) { this.brmm = brmm; }
	public String getApproval_req_id() { return approval_req_id; }
	public void setApproval_req_id(String approval_req_id) { this.approval_req_id = approval_req_id; }
	public String getOft_c() { return oft_c; }
	public void setOft_c(String oft_c) { this.oft_c = oft_c; }
	public String getOft() { return oft; }
	public void setOft(String oft) { this.oft = oft; }
	public String getApply_div_cd() { return apply_div_cd; }
	public void setApply_div_cd(String apply_div_cd) { this.apply_div_cd = apply_div_cd; }
	public String getApproval_req_reason() { return approval_req_reason; }
	public void setApproval_req_reason(String approval_req_reason) { this.approval_req_reason = approval_req_reason; }
	public String getFirst_user_id() { return first_user_id; }
	public void setFirst_user_id(String first_user_id) { this.first_user_id = first_user_id; }
	public String getFirst_user_nm() { return first_user_nm; }
	public void setFirst_user_nm(String first_user_nm) { this.first_user_nm = first_user_nm; }
	public String getSecond_user_id() { return second_user_id; }
	public void setSecond_user_id(String second_user_id) { this.second_user_id = second_user_id; }
	public String getSecond_user_nm() { return second_user_nm; }
	public void setSecond_user_nm(String second_user_nm) { this.second_user_nm = second_user_nm; }
	public String getReq_date() { return req_date; }
	public void setReq_date(String req_date) { this.req_date = req_date; }
	public String getPage_name() { return page_name; }
	public void setPage_name(String page_name) { this.page_name = page_name; }
	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
	public String getBulkReqList() { return bulkReqList; }
	public void setBulkReqList(String bulkReqList) { this.bulkReqList = bulkReqList; }
}

