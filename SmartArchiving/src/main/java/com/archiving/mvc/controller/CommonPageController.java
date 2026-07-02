package com.archiving.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonPageController {

    @GetMapping({"/footerInfo", "/footerInfo.jsp", "/common/includes/footerInfo", "/views/common/includes/footerInfo", "/views/common/includes/footerInfo.jsp"})
    public String footerInfo() { return "common/includes/footerInfo.tiles"; }

    @GetMapping({"/headerInfo", "/headerInfo.jsp", "/common/includes/headerInfo", "/views/common/includes/headerInfo", "/views/common/includes/headerInfo.jsp"})
    public String headerInfo() { return "common/includes/headerInfo.tiles"; }

    @GetMapping({"/menuInfo", "/menuInfo.jsp", "/common/includes/menuInfo", "/views/common/includes/menuInfo", "/views/common/includes/menuInfo.jsp"})
    public String menuInfo() { return "common/includes/menuInfo.tiles"; }

    @GetMapping({"/menuInfoOld", "/menuInfoOld.jsp", "/common/includes/menuInfoOld", "/views/common/includes/menuInfoOld", "/views/common/includes/menuInfoOld.jsp"})
    public String menuInfoOld() { return "common/includes/menuInfoOld.tiles"; }

    @GetMapping({"/menuInfo_0827_org", "/menuInfo_0827_org.jsp", "/common/includes/menuInfo_0827_org", "/views/common/includes/menuInfo_0827_org", "/views/common/includes/menuInfo_0827_org.jsp"})
    public String menuInfo0827Org() { return "common/includes/menuInfo_0827_org.tiles"; }
}
