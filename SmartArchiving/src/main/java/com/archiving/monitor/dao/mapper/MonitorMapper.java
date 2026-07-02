package com.archiving.monitor.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MonitorMapper {
	/* 사용자/그룹 목록 조회(구분값 userGb 기준) */
	List<Map<String, Object>> selectUserOrGroupList(@Param("userGb") String userGb);

	/* 사용자/그룹에 미부여된 메뉴 목록 조회 */
	List<Map<String, Object>> selectMenusNotGranted(@Param("userGb") String userGb, @Param("userId") String userId);

	/* 사용자 기준 부여된 메뉴 목록 조회 */
	List<Map<String, Object>> selectGrantedMenusForUser(@Param("userGb") String userGb, @Param("userId") String userId);

	/* 그룹 기준 부여된 메뉴 목록 조회 */
	List<Map<String, Object>> selectGrantedMenusForGroup(@Param("userGb") String userGb, @Param("userId") String userId);
}

