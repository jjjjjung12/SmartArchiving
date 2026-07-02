package com.archiving.user.dto;

public class UserGroupSaveParamDto {
	private String user_grp_id;
	private String user_grp_nm;
	private String description;
	private String use_yn;
	private String crud;

	public String getUser_grp_id() { return user_grp_id; }
	public void setUser_grp_id(String user_grp_id) { this.user_grp_id = user_grp_id; }
	public String getUser_grp_nm() { return user_grp_nm; }
	public void setUser_grp_nm(String user_grp_nm) { this.user_grp_nm = user_grp_nm; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getUse_yn() { return use_yn; }
	public void setUse_yn(String use_yn) { this.use_yn = use_yn; }
	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
}
