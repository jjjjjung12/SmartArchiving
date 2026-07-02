package com.archiving.monitor.service;

import java.util.List;
import java.util.Map;

public interface MonitorService {

	/* 모니터링 조회(__gb에 따라 사용자/권한메뉴 목록 반환) */
	List<Map<String, Object>> list(String gb, String userGb, String userId);
}
