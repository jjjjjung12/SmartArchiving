package com.archiving.approve.dto;

public class TranMaskReqParamDto {
	private String user_id;
	private String user_cd;
	private String user_nm;
	private String team_nm;
	private String approval_req_id;
	private String grade_nm;
	private String apply_div_cd;
	private String apply_div_nm;
	private String approval_req_reason;

	public String getUser_id() { return user_id; }
	public void setUser_id(String user_id) { this.user_id = user_id; }
	public String getUser_cd() { return user_cd; }
	public void setUser_cd(String user_cd) { this.user_cd = user_cd; }
	public String getUser_nm() { return user_nm; }
	public void setUser_nm(String user_nm) { this.user_nm = user_nm; }
	public String getTeam_nm() { return team_nm; }
	public void setTeam_nm(String team_nm) { this.team_nm = team_nm; }
	public String getApproval_req_id() { return approval_req_id; }
	public void setApproval_req_id(String approval_req_id) { this.approval_req_id = approval_req_id; }
	public String getGrade_nm() { return grade_nm; }
	public void setGrade_nm(String grade_nm) { this.grade_nm = grade_nm; }
	public String getApply_div_cd() { return apply_div_cd; }
	public void setApply_div_cd(String apply_div_cd) { this.apply_div_cd = apply_div_cd; }
	public String getApply_div_nm() { return apply_div_nm; }
	public void setApply_div_nm(String apply_div_nm) { this.apply_div_nm = apply_div_nm; }
	public String getApproval_req_reason() { return approval_req_reason; }
	public void setApproval_req_reason(String approval_req_reason) { this.approval_req_reason = approval_req_reason; }
}

