package com.archiving.auth.handler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.archiving.auth.exception.LoginFailedException;
import com.archiving.auth.service.LoginService;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final LoginService loginService;

    public LoginFailureHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        if (exception instanceof LoginFailedException) {
            handleBusinessFailure(request, response, (LoginFailedException) exception);
            return;
        }

        String result = resolveResultCode(exception);
        redirect(request, response, result, resolveMessage(result), null);
    }

    private void handleBusinessFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            LoginFailedException exception) throws IOException {

        HttpSession session = request.getSession(true);
        loginService.applySessionAttributes(session, exception.getSessionAttributes());

        redirect(request, response,
                exception.getResultCode(),
                exception.getMessage(),
                exception.getRedirectPath());
    }

    private void redirect(
            HttpServletRequest request,
            HttpServletResponse response,
            String result,
            String message,
            String redirectPath) throws IOException {

        String contextPath = request.getContextPath();
        String target = redirectPath != null ? redirectPath : "/login";
        String redirectUrl = contextPath + target
                + "?result=" + URLEncoder.encode(result, StandardCharsets.UTF_8)
                + "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);

        response.sendRedirect(redirectUrl);
    }

    private String resolveResultCode(AuthenticationException exception) {
        if (exception instanceof UsernameNotFoundException) {
            return "NotFound";
        }
        if (exception instanceof DisabledException) {
            return "useFailed";
        }
        if (exception instanceof LockedException) {
            return "locked";
        }
        if (exception instanceof BadCredentialsException) {
            return "invalid";
        }
        return "ERROR";
    }

    private String resolveMessage(String result) {
        switch (result) {
            case "NotFound":
                return "존재하지 않는 사용자입니다.";
            case "useFailed":
                return "사용 중지된 계정입니다.";
            case "locked":
                return "잠긴 계정입니다.";
            case "invalid":
                return "아이디 또는 비밀번호가 올바르지 않습니다.";
            default:
                return "로그인 처리 중 오류가 발생했습니다.";
        }
    }
}
