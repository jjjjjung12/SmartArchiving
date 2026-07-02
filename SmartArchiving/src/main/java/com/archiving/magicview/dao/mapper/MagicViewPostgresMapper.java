package com.archiving.magicview.dao.mapper;

import org.apache.ibatis.annotations.Param;

public interface MagicViewPostgresMapper {

	/* 당일 마스킹 처리 이력 건수 조회 */
	int countMaskHistoryToday(@Param("userCd") String userCd, @Param("procDate") String procDate);
}
