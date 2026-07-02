package com.archiving.comm.dto;

public class TableSaveParamDto {
	private String crud;
	private String serverId;

	private String table_id;
	private String table_cd;
	private String table_nm;
	private String table_join_nm;
	private String save_preq_cd;
	private String save_preq;
	private String exp_preq_cd;
	private String exp_preq;
	private String description;
	private String use_yn;

	// attr fields (for AC/AU/AD/AAU)
	private String attr_cd;
	private String attr_nm;
	private String attr_size;
	private String attr_order;
	private String decimal_size;
	private String attr_type_cd;
	private String attr_use_yn;
	private String attr_null_yn;
	private String sort_index;
	private String where_index;
	private String output_index;
	private String date_type_yn;
	private String time_type_yn;

	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
	public String getServerId() { return serverId; }
	public void setServerId(String serverId) { this.serverId = serverId; }
	public String getTable_id() { return table_id; }
	public void setTable_id(String table_id) { this.table_id = table_id; }
	public String getTable_cd() { return table_cd; }
	public void setTable_cd(String table_cd) { this.table_cd = table_cd; }
	public String getTable_nm() { return table_nm; }
	public void setTable_nm(String table_nm) { this.table_nm = table_nm; }
	public String getTable_join_nm() { return table_join_nm; }
	public void setTable_join_nm(String table_join_nm) { this.table_join_nm = table_join_nm; }
	public String getSave_preq_cd() { return save_preq_cd; }
	public void setSave_preq_cd(String save_preq_cd) { this.save_preq_cd = save_preq_cd; }
	public String getSave_preq() { return save_preq; }
	public void setSave_preq(String save_preq) { this.save_preq = save_preq; }
	public String getExp_preq_cd() { return exp_preq_cd; }
	public void setExp_preq_cd(String exp_preq_cd) { this.exp_preq_cd = exp_preq_cd; }
	public String getExp_preq() { return exp_preq; }
	public void setExp_preq(String exp_preq) { this.exp_preq = exp_preq; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getUse_yn() { return use_yn; }
	public void setUse_yn(String use_yn) { this.use_yn = use_yn; }
	public String getAttr_cd() { return attr_cd; }
	public void setAttr_cd(String attr_cd) { this.attr_cd = attr_cd; }
	public String getAttr_nm() { return attr_nm; }
	public void setAttr_nm(String attr_nm) { this.attr_nm = attr_nm; }
	public String getAttr_size() { return attr_size; }
	public void setAttr_size(String attr_size) { this.attr_size = attr_size; }
	public String getAttr_order() { return attr_order; }
	public void setAttr_order(String attr_order) { this.attr_order = attr_order; }
	public String getDecimal_size() { return decimal_size; }
	public void setDecimal_size(String decimal_size) { this.decimal_size = decimal_size; }
	public String getAttr_type_cd() { return attr_type_cd; }
	public void setAttr_type_cd(String attr_type_cd) { this.attr_type_cd = attr_type_cd; }
	public String getAttr_use_yn() { return attr_use_yn; }
	public void setAttr_use_yn(String attr_use_yn) { this.attr_use_yn = attr_use_yn; }
	public String getAttr_null_yn() { return attr_null_yn; }
	public void setAttr_null_yn(String attr_null_yn) { this.attr_null_yn = attr_null_yn; }
	public String getSort_index() { return sort_index; }
	public void setSort_index(String sort_index) { this.sort_index = sort_index; }
	public String getWhere_index() { return where_index; }
	public void setWhere_index(String where_index) { this.where_index = where_index; }
	public String getOutput_index() { return output_index; }
	public void setOutput_index(String output_index) { this.output_index = output_index; }
	public String getDate_type_yn() { return date_type_yn; }
	public void setDate_type_yn(String date_type_yn) { this.date_type_yn = date_type_yn; }
	public String getTime_type_yn() { return time_type_yn; }
	public void setTime_type_yn(String time_type_yn) { this.time_type_yn = time_type_yn; }
}

