package com.archiving.auth.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.archiving.auth.service.LoginService;

@Component
public class AppLogoutSuccessHandler implements LogoutSuccessHandler {

    private final LoginService loginService;

    public AppLogoutSuccessHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        HttpSession session = request.getSession(false);
        loginService.clearLoginSession(session);

        HttpSession noticeSession = request.getSession(true);
        noticeSession.setAttribute("loginResultCode", "success");
        noticeSession.setAttribute("loginMessage", "로그아웃되었습니다.");

        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/login");
    }
}
