package com.archiving.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.archiving.auth.dao.mapper.AuthMenuMapper;
import com.archiving.auth.dto.MenuItemDto;
import com.archiving.auth.service.MenuAuthService;
import com.archiving.auth.support.ApiMenuAuthorization;
import com.archiving.auth.support.MenuUrlNormalizer;

@Service
public class MenuAuthServiceImpl implements MenuAuthService {

    private final AuthMenuMapper authMenuMapper;

    public MenuAuthServiceImpl(AuthMenuMapper authMenuMapper) {
        this.authMenuMapper = authMenuMapper;
    }

    @Override
    public boolean isAdmin(String userCd, String groupId) {
        if ("admin".equalsIgnoreCase(userCd)) {
            return true;
        }
        return "100".equals(groupId) || "101".equals(groupId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<String> getAllowedMenuPaths(HttpSession session, String contextPath) {
        Object cached = session.getAttribute(SESSION_ALLOWED_MENU_PATHS);
        if (cached instanceof Set) {
            return (Set<String>) cached;
        }

        String userCd = (String) session.getAttribute("usercd");
        String userId = (String) session.getAttribute("userid");
        String groupId = (String) session.getAttribute("groupid");

        Set<String> paths = new HashSet<>();
        List<MenuItemDto> menus;
        if (isAdmin(userCd, groupId)) {
            menus = authMenuMapper.selectMenusForAll("M");
        } else if (userId != null && !userId.isBlank()) {
            menus = authMenuMapper.selectMenusForUser(userId);
        } else {
            menus = List.of();
        }

        for (MenuItemDto m : menus) {
            String key = MenuUrlNormalizer.normalizeMenuUrlPath(contextPath, m.getMenuUrl());
            if (key != null && !"#".equals(key)) {
                paths.add(key);
            }
        }

        session.setAttribute(SESSION_ALLOWED_MENU_PATHS, paths);
        return paths;
    }

    @Override
    public void clearAllowedMenuPathsCache(HttpSession session) {
        if (session != null) {
            session.removeAttribute(SESSION_ALLOWED_MENU_PATHS);
        }
    }

    @Override
    public boolean canAccessPage(HttpSession session, String contextPath, String normalizedRequestPath) {
        if (ApiMenuAuthorization.isLoginOnlyPage(normalizedRequestPath)) {
            return true;
        }
        if (isAdmin((String) session.getAttribute("usercd"), (String) session.getAttribute("groupid"))) {
            return true;
        }
        Set<String> allowed = getAllowedMenuPaths(session, contextPath);
        for (String allowedPath : allowed) {
            if (MenuUrlNormalizer.pathMatches(normalizedRequestPath, allowedPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canAccessApi(HttpSession session, String contextPath, String servletPath) {
        if (ApiMenuAuthorization.isLoginOnlyApi(servletPath)) {
            return true;
        }
        if (isAdmin((String) session.getAttribute("usercd"), (String) session.getAttribute("groupid"))) {
            return true;
        }

        String fragment = ApiMenuAuthorization.requiredMenuFragment(servletPath);
        if (fragment == null) {
            // 매핑 없음: 화면 URL과 동일 규칙으로 servlet path 자체를 메뉴와 비교
            String pathKey = MenuUrlNormalizer.normalizePathOnly(servletPath);
            return canAccessPage(session, contextPath, pathKey);
        }

        Set<String> allowed = getAllowedMenuPaths(session, contextPath);
        String need = fragment.toLowerCase(Locale.ROOT);
        for (String allowedPath : allowed) {
            if (allowedPath.toLowerCase(Locale.ROOT).contains(need)) {
                return true;
            }
        }
        return false;
    }
}
