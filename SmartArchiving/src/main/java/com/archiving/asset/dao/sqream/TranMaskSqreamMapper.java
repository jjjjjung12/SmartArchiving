package com.archiving.asset.dao.sqream;

import org.apache.ibatis.annotations.Param;

public interface TranMaskSqreamMapper {

	/* (Sqream) 마스킹 패턴이 설정된 전문 항목 건수 조회 */
	int countMaskPatternNotNullByTgrmNmEng(@Param("tgrmNmEng") String tgrmNmEng);
}
