package com.archiving.asset.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.archiving.asset.dto.TranMaskDto;
import com.archiving.asset.dto.TranMaskSaveParamDto;

public interface TranMaskMapper {
	/* 전문 마스킹 규칙 목록 조회(영문명 조건) */
	List<TranMaskDto> selectTranMaskList(@Param("tgrmNmEng") String tgrmNmEng);

	/* 전문 마스킹 규칙 신규 등록 */
	int insertTranMask(TranMaskSaveParamDto param);

	/* 전문 마스킹 규칙 수정 */
	int updateTranMask(TranMaskSaveParamDto param);

	/* 전문 마스킹 규칙 삭제 */
	int deleteTranMask(@Param("tgrmNmEng") String tgrmNmEng);
}

