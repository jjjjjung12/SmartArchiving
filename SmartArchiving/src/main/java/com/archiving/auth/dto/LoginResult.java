package com.archiving.auth.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.archiving.auth.security.LoginUser;

public class LoginResult {

    private final boolean success;
    private final String resultCode;
    private final String message;
    private final String redirectPath;
    private final LoginUser loginUser;
    private final Map<String, Object> sessionAttributes;
    private final boolean expireWarn;
    private final String expireDate;
    private final Long remainDays;

    private LoginResult(
            boolean success,
            String resultCode,
            String message,
            String redirectPath,
            LoginUser loginUser,
            Map<String, Object> sessionAttributes,
            boolean expireWarn,
            String expireDate,
            Long remainDays) {
        this.success = success;
        this.resultCode = resultCode;
        this.message = message;
        this.redirectPath = redirectPath;
        this.loginUser = loginUser;
        this.sessionAttributes = sessionAttributes == null
                ? Collections.emptyMap()
                : Collections.unmodifiableMap(sessionAttributes);
        this.expireWarn = expireWarn;
        this.expireDate = expireDate;
        this.remainDays = remainDays;
    }

    public static LoginResult success(
            LoginUser loginUser,
            Map<String, Object> sessionAttributes,
            boolean expireWarn,
            String expireDate,
            Long remainDays) {
        return new LoginResult(
                true, "success", null, null, loginUser, sessionAttributes,
                expireWarn, expireDate, remainDays);
    }

    public static LoginResult failure(
            String resultCode,
            String message,
            String redirectPath,
            Map<String, Object> sessionAttributes) {
        return new LoginResult(
                false, resultCode, message, redirectPath, null, sessionAttributes,
                false, null, null);
    }

    public static Map<String, Object> sessionMap() {
        return new HashMap<>();
    }

    public boolean isSuccess() { return success; }
    public String getResultCode() { return resultCode; }
    public String getMessage() { return message; }
    public String getRedirectPath() { return redirectPath; }
    public LoginUser getLoginUser() { return loginUser; }
    public Map<String, Object> getSessionAttributes() { return sessionAttributes; }
    public boolean isExpireWarn() { return expireWarn; }
    public String getExpireDate() { return expireDate; }
    public Long getRemainDays() { return remainDays; }
}
