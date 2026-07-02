package com.archiving.asset.service;

import java.util.List;

import com.archiving.asset.dto.TranMaskDto;
import com.archiving.asset.dto.TranMaskSaveParamDto;

public interface TranMaskService {

	/* 전문 마스킹 규칙 목록 조회(영문명 조건) */
	List<TranMaskDto> list(String tgrmNmEng);

	/* 전문 마스킹 규칙 저장 처리(C/U/D, Sqream 컬럼 제약 검증 포함) */
	void save(TranMaskSaveParamDto param);
}
