package com.archiving.auth.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        clearLoginSessionAttributes(session);
        return "auth/login";
    }

    @GetMapping("/userAuthApplyLogin")
    public String userAuthApplyLogin() {
        return "auth/userAuthApplyLogin";
    }

    @GetMapping("/userApproveProcNonLogin")
    public String userApproveProcNonLogin() {
        return "auth/userApproveProcNonLogin";
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
