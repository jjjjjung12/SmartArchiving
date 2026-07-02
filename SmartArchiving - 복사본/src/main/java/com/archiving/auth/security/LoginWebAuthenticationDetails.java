package com.archiving.auth.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.archiving.util.ClientIpResolver;

public class LoginWebAuthenticationDetails extends WebAuthenticationDetails {

    private final String clientIp;
    private final boolean ssoPresent;

    public LoginWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.clientIp = ClientIpResolver.resolve(request);
        this.ssoPresent = request.getSession(true).getAttribute("sso_id") != null;
    }

    public String getClientIp() {
        return clientIp;
    }

    public boolean isSsoPresent() {
        return ssoPresent;
    }
}
