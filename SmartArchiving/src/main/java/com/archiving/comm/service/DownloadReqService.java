package com.archiving.comm.service;

import com.archiving.comm.dto.DownloadReqCheckParamDto;
import com.archiving.comm.dto.DownloadReqSaveParamDto;

public interface DownloadReqService {

	/* 당일 다운로드 요청 여부 확인(미요청 시 지점정보 반환) */
	Result checkToday(DownloadReqCheckParamDto p);

	/* 다운로드 요청 등록 */
	void save(DownloadReqSaveParamDto p);

	class Result {
		private final String result;
		private final String brc;
		private final String brnm;

		private Result(String result, String brc, String brnm) {
			this.result = result;
			this.brc = brc;
			this.brnm = brnm;
		}

		/* 요청 가능(이미 요청함) 결과 생성 */
		public static Result okFound() { return new Result("OK", null, null); }

		/* 미요청(NotFound) 결과 생성(지점정보 포함) */
		public static Result notFound(String brc, String brnm) { return new Result("NotFound", brc, brnm); }

		/* 결과 코드 반환 */
		public String getResult() { return result; }

		/* 지점코드 반환 */
		public String getBrc() { return brc; }

		/* 지점명 반환 */
		public String getBrnm() { return brnm; }
	}
}
