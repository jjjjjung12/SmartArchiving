package com.archiving.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobPageController {

    @GetMapping({"/cnvJobHistory", "/cnvJobHistory.jsp", "/job/cnvJobHistory", "/views/job/cnvJobHistory", "/views/job/cnvJobHistory.jsp"})
    public String cnvJobHistory() { return "job/cnvJobHistory.tiles"; }

    @GetMapping({"/cnvJobMst", "/cnvJobMst.jsp", "/job/cnvJobMst", "/views/job/cnvJobMst", "/views/job/cnvJobMst.jsp"})
    public String cnvJobMst() { return "job/cnvJobMst.tiles"; }

    @GetMapping({"/convJobHistory", "/convJobHistory.jsp", "/job/convJobHistory", "/views/job/convJobHistory", "/views/job/convJobHistory.jsp"})
    public String convJobHistory() { return "job/convJobHistory.tiles"; }

    @GetMapping({"/deleteJobHistory", "/deleteJobHistory.jsp", "/job/deleteJobHistory", "/views/job/deleteJobHistory", "/views/job/deleteJobHistory.jsp"})
    public String deleteJobHistory() { return "job/deleteJobHistory.tiles"; }

    @GetMapping({"/deleteJobMst", "/deleteJobMst.jsp", "/job/deleteJobMst", "/views/job/deleteJobMst", "/views/job/deleteJobMst.jsp"})
    public String deleteJobMst() { return "job/deleteJobMst.tiles"; }

    @GetMapping({"/exportJobHistory", "/exportJobHistory.jsp", "/job/exportJobHistory", "/views/job/exportJobHistory", "/views/job/exportJobHistory.jsp"})
    public String exportJobHistory() { return "job/exportJobHistory.tiles"; }

    @GetMapping({"/exportJobMst", "/exportJobMst.jsp", "/job/exportJobMst", "/views/job/exportJobMst", "/views/job/exportJobMst.jsp"})
    public String exportJobMst() { return "job/exportJobMst.tiles"; }

    @GetMapping({"/helpSchedule", "/helpSchedule.jsp", "/job/helpSchedule", "/views/job/helpSchedule", "/views/job/helpSchedule.jsp"})
    public String helpSchedule() { return "job/helpSchedule.tiles"; }

    @GetMapping({"/jobInfo", "/jobInfo.jsp", "/job/jobInfo", "/views/job/jobInfo", "/views/job/jobInfo.jsp"})
    public String jobInfo() { return "job/jobInfo.tiles"; }

    @GetMapping({"/jobMonitor", "/jobMonitor.jsp", "/job/jobMonitor", "/views/job/jobMonitor", "/views/job/jobMonitor.jsp"})
    public String jobMonitor() { return "job/jobMonitor.tiles"; }

    @GetMapping({"/loadJobHistory", "/loadJobHistory.jsp", "/job/loadJobHistory", "/views/job/loadJobHistory", "/views/job/loadJobHistory.jsp"})
    public String loadJobHistory() { return "job/loadJobHistory.tiles"; }

    @GetMapping({"/loadJobMst", "/loadJobMst.jsp", "/job/loadJobMst", "/views/job/loadJobMst", "/views/job/loadJobMst.jsp"})
    public String loadJobMst() { return "job/loadJobMst.tiles"; }

    @GetMapping({"/onDemand", "/onDemand.jsp", "/job/onDemand", "/views/job/onDemand", "/views/job/onDemand.jsp"})
    public String onDemand() { return "job/onDemand.tiles"; }

    @GetMapping({"/onDemandList", "/onDemandList.jsp", "/job/onDemandList", "/views/job/onDemandList", "/views/job/onDemandList.jsp"})
    public String onDemandList() { return "job/onDemandList.tiles"; }

    @GetMapping({"/publicJobMst", "/publicJobMst.jsp", "/job/publicJobMst", "/views/job/publicJobMst", "/views/job/publicJobMst.jsp"})
    public String publicJobMst() { return "job/publicJobMst.tiles"; }

    @GetMapping({"/reorgJobHistory", "/reorgJobHistory.jsp", "/job/reorgJobHistory", "/views/job/reorgJobHistory", "/views/job/reorgJobHistory.jsp"})
    public String reorgJobHistory() { return "job/reorgJobHistory.tiles"; }

    @GetMapping({"/reorgJobMst", "/reorgJobMst.jsp", "/job/reorgJobMst", "/views/job/reorgJobMst", "/views/job/reorgJobMst.jsp"})
    public String reorgJobMst() { return "job/reorgJobMst.tiles"; }

    @GetMapping({"/scheduleInfo", "/scheduleInfo.jsp", "/job/scheduleInfo", "/views/job/scheduleInfo", "/views/job/scheduleInfo.jsp"})
    public String scheduleInfo() { return "job/scheduleInfo.tiles"; }

    @GetMapping({"/srcJobMst", "/srcJobMst.jsp", "/job/srcJobMst", "/views/job/srcJobMst", "/views/job/srcJobMst.jsp"})
    public String srcJobMst() { return "job/srcJobMst.tiles"; }
}
