package com.archiving.auth.security;

import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.archiving.auth.service.MenuAuthService;
import com.archiving.auth.support.ApiMenuAuthorization;
import com.archiving.auth.support.MenuUrlNormalizer;

/**
 * ArchivingWeb MenuAuthorizationInterceptor 동작을 Spring Security 권한 판단으로 이전.
 */
@Component
public class MenuAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final MenuAuthService menuAuthService;

    public MenuAuthorizationManager(MenuAuthService menuAuthService) {
        this.menuAuthService = menuAuthService;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
            RequestAuthorizationContext context) {

        HttpServletRequest request = context.getRequest();
        String contextPath = request.getContextPath() == null ? "" : request.getContextPath();
        String servletPath = request.getServletPath() == null ? "" : request.getServletPath();
        String normalizedPath = MenuUrlNormalizer.normalizeRequestPath(contextPath, request.getRequestURI());

        if (ApiMenuAuthorization.isPublicApi(servletPath)) {
            return new AuthorizationDecision(true);
        }

        Authentication auth = authentication.get();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return new AuthorizationDecision(false);
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return new AuthorizationDecision(false);
        }

        String userCd = (String) session.getAttribute("usercd");
        if (userCd == null || userCd.isBlank()) {
            return new AuthorizationDecision(false);
        }

        boolean allowed = isApiRequest(servletPath, request)
                ? menuAuthService.canAccessApi(session, contextPath, servletPath)
                : menuAuthService.canAccessPage(session, contextPath, normalizedPath);
        return new AuthorizationDecision(allowed);
    }

    public static boolean isApiRequest(String servletPath, HttpServletRequest request) {
        if (servletPath != null && servletPath.length() > 1) {
            String name = servletPath.startsWith("/") ? servletPath.substring(1) : servletPath;
            if (name.startsWith("Get") || name.startsWith("Set") || name.startsWith("Parse")
                    || name.startsWith("MagicView") || name.startsWith("Send")) {
                return true;
            }
        }
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return true;
        }
        return request.getHeader("X-Requested-With") != null;
    }
}
