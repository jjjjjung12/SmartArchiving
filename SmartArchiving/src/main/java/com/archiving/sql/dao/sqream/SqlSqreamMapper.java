package com.archiving.sql.dao.sqream;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SqlSqreamMapper {
	/* Sqream 연결 확인(Ping) */
	Integer selectPing();

	/* 동적 SQL 실행(Sqream, 주의: ${sql} 직접 실행) */
	List<Map<String, Object>> selectDynamicSql(@Param("sql") String sql);
}

