package com.archiving.auth.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;

public class LoginFailedException extends AuthenticationException {

    private final String resultCode;
    private final String redirectPath;
    private final Map<String, Object> sessionAttributes;

    public LoginFailedException(
            String resultCode,
            String message,
            String redirectPath,
            Map<String, Object> sessionAttributes) {
        super(message);
        this.resultCode = resultCode;
        this.redirectPath = redirectPath;
        this.sessionAttributes = sessionAttributes == null
                ? Collections.emptyMap()
                : sessionAttributes;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
}
