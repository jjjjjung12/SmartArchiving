package com.archiving.comm.dao.mapper;

import org.apache.ibatis.annotations.Param;

public interface DownloadReqMapper {
	/* 당일(등록일) 다운로드 요청 건수 카운트(사용자 기준) */
	int countTodayReq(@Param("regDate") String regDate, @Param("userCd") String userCd);

	/* 인사마스터(사번) 지점정보 조회 */
	InsaBranchRow selectInsaBranch(@Param("eno") String eno);

	/* 다운로드 요청 등록 */
	int insertDownloadReq(InsertDownloadReqParam p);

	class InsaBranchRow {
		private String brc;
		private String brnm;
		public String getBrc() { return brc; }
		public void setBrc(String brc) { this.brc = brc; }
		public String getBrnm() { return brnm; }
		public void setBrnm(String brnm) { this.brnm = brnm; }
	}

	class InsertDownloadReqParam {
		private String userCd;
		private String userNm;
		private String reqDiv;
		private String reqNm;
		private String reqNum;
		private String reqReason;
		private String programId;
		private String regDate;
		private String programWhere;

		public String getUserCd() { return userCd; }
		public void setUserCd(String userCd) { this.userCd = userCd; }
		public String getUserNm() { return userNm; }
		public void setUserNm(String userNm) { this.userNm = userNm; }
		public String getReqDiv() { return reqDiv; }
		public void setReqDiv(String reqDiv) { this.reqDiv = reqDiv; }
		public String getReqNm() { return reqNm; }
		public void setReqNm(String reqNm) { this.reqNm = reqNm; }
		public String getReqNum() { return reqNum; }
		public void setReqNum(String reqNum) { this.reqNum = reqNum; }
		public String getReqReason() { return reqReason; }
		public void setReqReason(String reqReason) { this.reqReason = reqReason; }
		public String getProgramId() { return programId; }
		public void setProgramId(String programId) { this.programId = programId; }
		public String getRegDate() { return regDate; }
		public void setRegDate(String regDate) { this.regDate = regDate; }
		public String getProgramWhere() { return programWhere; }
		public void setProgramWhere(String programWhere) { this.programWhere = programWhere; }
	}
}

