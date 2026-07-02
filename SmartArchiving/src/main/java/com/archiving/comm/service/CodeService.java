package com.archiving.comm.service;

import java.util.List;

import com.archiving.comm.dto.CodeDetailDto;
import com.archiving.comm.dto.CodeGroupDto;
import com.archiving.comm.dto.CodeManagerParamDto;

public interface CodeService {

	/* 레거시 코드/서버 캐시(CodeClass) 갱신 */
	void refreshLegacyCodeCaches();

	/* 코드상세 목록 조회(그룹/상세코드 조건) */
	List<CodeDetailDto> listDetails(String groupCd, String detailCd);

	/* 코드그룹 목록 조회 */
	List<CodeGroupDto> listGroups(String groupNm, String useYn);

	/* 그룹코드 기준 코드상세 목록 조회 */
	List<CodeDetailDto> listDetailsByGroup(String groupCd, String useYn);

	/* 코드관리 CRUD 적용 후 캐시 갱신 */
	void apply(CodeManagerParamDto p);
}
