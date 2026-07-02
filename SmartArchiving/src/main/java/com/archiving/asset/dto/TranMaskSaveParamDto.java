package com.archiving.asset.dto;

public class TranMaskSaveParamDto {
	private String tgrm_nm_eng;
	private String tgrm_nm_kor;
	private String msk_pttrn;
	private String crud;

	public String getTgrm_nm_eng() { return tgrm_nm_eng; }
	public void setTgrm_nm_eng(String tgrm_nm_eng) { this.tgrm_nm_eng = tgrm_nm_eng; }
	public String getTgrm_nm_kor() { return tgrm_nm_kor; }
	public void setTgrm_nm_kor(String tgrm_nm_kor) { this.tgrm_nm_kor = tgrm_nm_kor; }
	public String getMsk_pttrn() { return msk_pttrn; }
	public void setMsk_pttrn(String msk_pttrn) { this.msk_pttrn = msk_pttrn; }
	public String getCrud() { return crud; }
	public void setCrud(String crud) { this.crud = crud; }
}

