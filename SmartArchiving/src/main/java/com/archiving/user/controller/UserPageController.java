package com.archiving.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping({"/menuManager", "/menuManager.jsp", "/user/menuManager", "/views/user/menuManager", "/views/user/menuManager.jsp"})
    public String menuManager() {
        return "user/menuManager.tiles";
    }

    @GetMapping({"/modalDept", "/modalDept.jsp", "/user/modalDept", "/views/user/modalDept", "/views/user/modalDept.jsp"})
    public String modalDept() {
        return "user/modalDept.tiles";
    }

    @GetMapping({"/modalSabun", "/modalSabun.jsp", "/user/modalSabun", "/views/user/modalSabun", "/views/user/modalSabun.jsp"})
    public String modalSabun() {
        return "user/modalSabun.tiles";
    }

    @GetMapping({"/userAuth", "/userAuth.jsp", "/user/userAuth", "/views/user/userAuth", "/views/user/userAuth.jsp"})
    public String userAuth() {
        return "user/userAuth.tiles";
    }

    @GetMapping({"/userGroup", "/userGroup.jsp", "/user/userGroup", "/views/user/userGroup", "/views/user/userGroup.jsp"})
    public String userGroup() {
        return "user/userGroup.tiles";
    }

    @GetMapping({"/userInfo", "/userInfo.jsp", "/user/userInfo", "/views/user/userInfo", "/views/user/userInfo.jsp"})
    public String userInfo() {
        return "user/userInfo.tiles";
    }

    @GetMapping({"/userLogList", "/userLogList.jsp", "/user/userLogList", "/views/user/userLogList", "/views/user/userLogList.jsp"})
    public String userLogList() {
        return "user/userLogList.tiles";
    }
}
