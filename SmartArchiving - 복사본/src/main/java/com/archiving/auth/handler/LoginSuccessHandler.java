package com.archiving.auth.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.archiving.auth.dto.LoginResult;
import com.archiving.auth.service.LoginService;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginService loginService;

    public LoginSuccessHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(60 * 30);

        if (authentication.getDetails() instanceof LoginResult) {
            LoginResult result = (LoginResult) authentication.getDetails();
            loginService.applySessionAttributes(session, result.getSessionAttributes());

            if (result.isExpireWarn()) {
                session.setAttribute("expireWarn", true);
                session.setAttribute("expireDate", nullToEmpty(result.getExpireDate()));
                session.setAttribute("remainDays", result.getRemainDays() == null ? 0L : result.getRemainDays());
            }
        }

        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/");
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
