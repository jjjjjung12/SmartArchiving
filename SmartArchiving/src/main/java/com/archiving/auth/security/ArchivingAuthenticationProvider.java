package com.archiving.auth.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.archiving.auth.dto.LoginResult;
import com.archiving.auth.exception.LoginFailedException;
import com.archiving.auth.service.LoginService;

import javax.servlet.http.HttpServletRequest;

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

        HttpServletRequest request = currentRequest();
        LoginResult result = loginService.authenticate(username, password, request);

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

    private HttpServletRequest currentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new LoginFailedException("ERROR", "요청 정보를 확인할 수 없습니다.", null, null);
        }
        return attrs.getRequest();
    }
}
