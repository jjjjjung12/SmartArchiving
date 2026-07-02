package com.archiving.auth.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.archiving.auth.dto.LoginResult;

/**
 * (인사: 결재+만료 선처리 → 만료 30일 전 → IP → 퇴직 → 지점/90일/세션, 비인사+SSO는 NotInsaMaster)
 */
public interface LoginService {

	/* 로그인 인증 처리(인사/비인사/SSO 분기 및 검증) */
	LoginResult authenticate(String userCd, String rawPassword, String clientIp, boolean ssoYn);

	/* 인증 결과 세션 속성 반영(메뉴 캐시 초기화 포함) */
	void applySessionAttributes(HttpSession session, Map<String, Object> attributes);

	/* 로그인 세션 정리(SSO/메뉴 캐시 제거) */
	void clearLoginSession(HttpSession session);

	/* 레거시 GetLogin AJAX 응답 포맷(JSON 문자열) */
	String login(HttpServletRequest request);

	/* 레거시 GetLogin POST 로그아웃 응답 포맷(JSON 문자열) */
	String logout(HttpServletRequest request);
}
