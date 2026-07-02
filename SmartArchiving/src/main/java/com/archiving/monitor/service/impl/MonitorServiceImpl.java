package com.archiving.monitor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.archiving.monitor.dao.mapper.MonitorMapper;
import com.archiving.monitor.service.MonitorService;

@Service
public class MonitorServiceImpl implements MonitorService {
	private final MonitorMapper mapper;

	public MonitorServiceImpl(MonitorMapper mapper) {
		this.mapper = mapper;
	}

	/* 모니터링 조회(__gb에 따라 사용자/권한메뉴 목록 반환) */
	@Override
	public List<Map<String, Object>> list(String gb, String userGb, String userId) {
		if ("A".equals(gb)) {
			return mapper.selectUserOrGroupList(userGb);
		}
		if ("B".equals(gb)) {
			return mapper.selectMenusNotGranted(userGb, userId);
		}
		if ("C".equals(gb)) {
			return mapper.selectGrantedMenusForUser(userGb, userId);
		}
		if ("G".equals(gb)) {
			return mapper.selectGrantedMenusForGroup(userGb, userId);
		}
		return List.of();
	}
}
