package com.archiving.approve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApprovePageController {

    @GetMapping({"/userAuthApplyDetailPop", "/userAuthApplyDetailPop.jsp", "/approve/userAuthApplyDetailPop", "/views/approve/userAuthApplyDetailPop", "/views/approve/userAuthApplyDetailPop.jsp"})
    public String userAuthApplyDetailPop() { return "approve/userAuthApplyDetailPop.tiles"; }

    @GetMapping({"/userAuthApplyPop", "/userAuthApplyPop.jsp", "/approve/userAuthApplyPop", "/views/approve/userAuthApplyPop", "/views/approve/userAuthApplyPop.jsp"})
    public String userAuthApplyPop() { return "approve/userAuthApplyPop.tiles"; }

    @GetMapping({"/userApproveProc", "/userApproveProc.jsp", "/approve/userApproveProc", "/views/approve/userApproveProc", "/views/approve/userApproveProc.jsp"})
    public String userApproveProc() { return "approve/userApproveProc.tiles"; }

    @GetMapping({"/userApproveProcAdmin", "/userApproveProcAdmin.jsp", "/approve/userApproveProcAdmin", "/views/approve/userApproveProcAdmin", "/views/approve/userApproveProcAdmin.jsp"})
    public String userApproveProcAdmin() { return "approve/userApproveProcAdmin.tiles"; }

    @GetMapping({"/userApproveProcNonLogin", "/userApproveProcNonLogin.jsp", "/approve/userApproveProcNonLogin", "/views/approve/userApproveProcNonLogin", "/views/approve/userApproveProcNonLogin.jsp"})
    public String userApproveProcNonLogin() { return "approve/userApproveProcNonLogin.tiles"; }

    @GetMapping({"/userApproveStatSearch", "/userApproveStatSearch.jsp", "/approve/userApproveStatSearch", "/views/approve/userApproveStatSearch", "/views/approve/userApproveStatSearch.jsp"})
    public String userApproveStatSearch() { return "approve/userApproveStatSearch.tiles"; }

    @GetMapping({"/userApproveStatSearchBulk", "/userApproveStatSearchBulk.jsp"})
    public String userApproveStatSearchBulk() { return "approve/userApproveStatSearch.tiles"; }
}
