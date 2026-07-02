package com.archiving.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonitorPageController {

    @GetMapping({"/dashboard", "/dashboard.jsp", "/monitor/dashboard", "/views/monitor/dashboard", "/views/monitor/dashboard.jsp"})
    public String dashboard() { return "monitor/dashboard.tiles"; }

    @GetMapping({"/dashboard_first", "/dashboard_first.jsp", "/monitor/dashboard_first", "/views/monitor/dashboard_first", "/views/monitor/dashboard_first.jsp"})
    public String dashboardFirst() { return "monitor/dashboard_first.tiles"; }

    @GetMapping({"/dashboard_second", "/dashboard_second.jsp", "/monitor/dashboard_second", "/views/monitor/dashboard_second", "/views/monitor/dashboard_second.jsp"})
    public String dashboardSecond() { return "monitor/dashboard_second.tiles"; }

    @GetMapping({"/dashboard_third", "/dashboard_third.jsp", "/monitor/dashboard_third", "/views/monitor/dashboard_third", "/views/monitor/dashboard_third.jsp"})
    public String dashboardThird() { return "monitor/dashboard_third.tiles"; }
}
