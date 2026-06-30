package com.archiving.auth.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class MenuAuthService {

    public static final String SESSION_ALLOWED_MENU_PATHS = "allowedMenuPaths";

    public void clearAllowedMenuPathsCache(HttpSession session) {
        if (session != null) {
            session.removeAttribute(SESSION_ALLOWED_MENU_PATHS);
        }
    }
}
