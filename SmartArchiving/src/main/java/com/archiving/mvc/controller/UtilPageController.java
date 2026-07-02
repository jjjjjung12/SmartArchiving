package com.archiving.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilPageController {

    @GetMapping({"/EncDec", "/EncDec.jsp", "/util/EncDec", "/views/util/EncDec", "/views/util/EncDec.jsp"})
    public String encDec() { return "util/EncDec.tiles"; }

    @GetMapping({"/dataDecrypt", "/dataDecrypt.jsp", "/util/dataDecrypt", "/views/util/dataDecrypt", "/views/util/dataDecrypt.jsp"})
    public String dataDecrypt() { return "util/dataDecrypt.tiles"; }

    @GetMapping({"/listSample", "/listSample.jsp", "/util/listSample", "/views/util/listSample", "/views/util/listSample.jsp"})
    public String listSample() { return "util/listSample.tiles"; }
}
