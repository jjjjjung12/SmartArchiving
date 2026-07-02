package com.archiving.archiving.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArchivingController {

    @GetMapping("/archivingPage")
    public String archivingPage(@RequestParam HashMap<String, Object> requestMap, Model model) {
        return "archiving/archivingPage.tiles";
    }
}
