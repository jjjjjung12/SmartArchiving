package com.archiving.comm.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Minimal org tree for legacy dynatree ({@code modalDept.js}).
 */
public interface DeptMapper {

	/* 부서(지점) 트리 노드 목록 조회 */
	List<Map<String, Object>> selectInsaDeptNodes();

	/* 부서/사번/이름 조건으로 직원 목록 조회 */
	List<Map<String, Object>> selectEmpListByDept(@Param("name") String name,
		@Param("sabun") String sabun,
		@Param("orgcd") String orgcd);
}
