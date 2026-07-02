package com.archiving.auth.service;

import java.util.Set;

import javax.servlet.http.HttpSession;

public interface MenuAuthService {

	String SESSION_ALLOWED_MENU_PATHS = "allowedMenuPaths";

	boolean isAdmin(String userCd, String groupId);

	Set<String> getAllowedMenuPaths(HttpSession session, String contextPath);

	void clearAllowedMenuPathsCache(HttpSession session);

	boolean canAccessPage(HttpSession session, String contextPath, String normalizedRequestPath);

	boolean canAccessApi(HttpSession session, String contextPath, String servletPath);
}
