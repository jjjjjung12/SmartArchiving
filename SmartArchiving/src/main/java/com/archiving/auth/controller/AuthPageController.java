package com.archiving.auth.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {

    @GetMapping({"/", "/index", "/index.jsp", "/auth/index", "/views/auth/index", "/views/auth/index.jsp"})
    public String index() {
        return "auth/index.tiles";
    }

    @GetMapping({"/login", "/login.jsp", "/auth/login", "/views/auth/login", "/views/auth/login.jsp"})
    public String login(HttpSession session) {
        clearLoginSessionAttributes(session);
        return "auth/login.tiles";
    }

    @GetMapping({"/forgot-password", "/forgot-password.jsp", "/auth/forgot-password", "/views/auth/forgot-password", "/views/auth/forgot-password.jsp"})
    public String forgotPassword() {
        return "auth/forgot-password.tiles";
    }

    @GetMapping({"/noAuth", "/noAuth.jsp", "/auth/noAuth", "/views/auth/noAuth", "/views/auth/noAuth.jsp"})
    public String noAuth() {
        return "auth/noAuth.tiles";
    }

    @GetMapping({"/userAuthApplyLogin", "/userAuthApplyLogin.jsp", "/auth/userAuthApplyLogin", "/views/auth/userAuthApplyLogin", "/views/auth/userAuthApplyLogin.jsp"})
    public String userAuthApplyLogin() {
        return "auth/userAuthApplyLogin.tiles";
    }

    private void clearLoginSessionAttributes(HttpSession session) {
        if (session == null) {
            return;
        }
        session.removeAttribute("username");
        session.removeAttribute("userid");
        session.removeAttribute("usercd");
        session.removeAttribute("groupid");
        session.removeAttribute("picture");
        session.removeAttribute("approwait");
        session.removeAttribute("brc");
        session.removeAttribute("brnm");
    }
}
