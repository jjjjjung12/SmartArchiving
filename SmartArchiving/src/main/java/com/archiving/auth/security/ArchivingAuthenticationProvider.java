package com.archiving.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.archiving.auth.dto.LoginResult;
import com.archiving.auth.exception.LoginFailedException;
import com.archiving.auth.service.LoginService;

@Component
public class ArchivingAuthenticationProvider implements AuthenticationProvider {

    private final LoginService loginService;

    public ArchivingAuthenticationProvider(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials() == null
                ? ""
                : authentication.getCredentials().toString();

        String clientIp = "";
        boolean ssoPresent = false;
        if (authentication.getDetails() instanceof LoginWebAuthenticationDetails) {
            LoginWebAuthenticationDetails details =
                    (LoginWebAuthenticationDetails) authentication.getDetails();
            clientIp = details.getClientIp();
            ssoPresent = details.isSsoPresent();
        }

        LoginResult result = loginService.authenticate(username, password, clientIp, ssoPresent);

        if (!result.isSuccess()) {
            throw new LoginFailedException(
                    result.getResultCode(),
                    result.getMessage(),
                    result.getRedirectPath(),
                    result.getSessionAttributes());
        }

        LoginUser loginUser = result.getLoginUser();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginUser, null, loginUser.getAuthorities());
        token.setDetails(result);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
