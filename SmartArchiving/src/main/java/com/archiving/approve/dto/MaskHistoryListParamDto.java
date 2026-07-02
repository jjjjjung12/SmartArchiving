package com.archiving.approve.dto;

public class MaskHistoryListParamDto {
	private String start_dt;
	private String end_dt;
	private String __rows;
	private String __page;

	public String getStart_dt() { return start_dt; }
	public void setStart_dt(String start_dt) { this.start_dt = start_dt; }
	public String getEnd_dt() { return end_dt; }
	public void setEnd_dt(String end_dt) { this.end_dt = end_dt; }
	public String get__rows() { return __rows; }
	public void set__rows(String __rows) { this.__rows = __rows; }
	public String get__page() { return __page; }
	public void set__page(String __page) { this.__page = __page; }
}

