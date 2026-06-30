package com.archiving.auth.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class LoginWebAuthenticationDetailsSource
        implements AuthenticationDetailsSource<HttpServletRequest, LoginWebAuthenticationDetails> {

    @Override
    public LoginWebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new LoginWebAuthenticationDetails(request);
    }
}
