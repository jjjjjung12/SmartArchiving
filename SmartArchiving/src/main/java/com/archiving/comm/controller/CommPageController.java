package com.archiving.comm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommPageController {

    @GetMapping({"/JsonData", "/JsonData.jsp", "/comm/JsonData", "/views/comm/JsonData", "/views/comm/JsonData.jsp"})
    public String jsonData() { return "comm/JsonData.tiles"; }

    @GetMapping({"/codeManager", "/codeManager.jsp", "/comm/codeManager", "/views/comm/codeManager", "/views/comm/codeManager.jsp"})
    public String codeManager() { return "comm/codeManager.tiles"; }

    @GetMapping({"/down_excel", "/down_excel.jsp", "/comm/down_excel", "/views/comm/down_excel", "/views/comm/down_excel.jsp"})
    public String downExcel() { return "comm/down_excel.tiles"; }

    @GetMapping({"/downloadReqHistory", "/downloadReqHistory.jsp", "/comm/downloadReqHistory", "/views/comm/downloadReqHistory", "/views/comm/downloadReqHistory.jsp"})
    public String downloadReqHistory() { return "comm/downloadReqHistory.tiles"; }

    @GetMapping({"/downloadReqPop", "/downloadReqPop.jsp", "/comm/downloadReqPop", "/views/comm/downloadReqPop", "/views/comm/downloadReqPop.jsp"})
    public String downloadReqPop() { return "comm/downloadReqPop.tiles"; }

    @GetMapping({"/fileRead", "/fileRead.jsp", "/comm/fileRead", "/views/comm/fileRead", "/views/comm/fileRead.jsp"})
    public String fileRead() { return "comm/fileRead.tiles"; }

    @GetMapping({"/fileReadSelect", "/fileReadSelect.jsp", "/comm/fileReadSelect", "/views/comm/fileReadSelect", "/views/comm/fileReadSelect.jsp"})
    public String fileReadSelect() { return "comm/fileReadSelect.tiles"; }

    @GetMapping({"/maskingHistory", "/maskingHistory.jsp", "/comm/maskingHistory", "/views/comm/maskingHistory", "/views/comm/maskingHistory.jsp"})
    public String maskingHistory() { return "comm/maskingHistory.tiles"; }

    @GetMapping({"/noticeManage", "/noticeManage.jsp", "/comm/noticeManage", "/views/comm/noticeManage", "/views/comm/noticeManage.jsp"})
    public String noticeManage() { return "comm/noticeManage.tiles"; }

    @GetMapping({"/noticeManagePop", "/noticeManagePop.jsp", "/comm/noticeManagePop", "/views/comm/noticeManagePop", "/views/comm/noticeManagePop.jsp"})
    public String noticeManagePop() { return "comm/noticeManagePop.tiles"; }

    @GetMapping({"/noticeManageSetPop", "/noticeManageSetPop.jsp", "/comm/noticeManageSetPop", "/views/comm/noticeManageSetPop", "/views/comm/noticeManageSetPop.jsp"})
    public String noticeManageSetPop() { return "comm/noticeManageSetPop.tiles"; }

    @GetMapping({"/noticeManageDetailPop", "/noticeManageDetailPop.jsp", "/comm/noticeManageDetailPop", "/views/comm/noticeManageDetailPop", "/views/comm/noticeManageDetailPop.jsp"})
    public String noticeManageDetailPop() { return "comm/noticeManageDetailPop.tiles"; }

    @GetMapping({"/reportsInfo", "/reportsInfo.jsp", "/comm/reportsInfo", "/views/comm/reportsInfo", "/views/comm/reportsInfo.jsp"})
    public String reportsInfo() { return "comm/reportsInfo.tiles"; }

    @GetMapping({"/tableInfo", "/tableInfo.jsp", "/comm/tableInfo", "/views/comm/tableInfo", "/views/comm/tableInfo.jsp"})
    public String tableInfo() { return "comm/tableInfo.tiles"; }

    @GetMapping({"/tableInfoPop", "/tableInfoPop.jsp", "/comm/tableInfoPop", "/views/comm/tableInfoPop", "/views/comm/tableInfoPop.jsp"})
    public String tableInfoPop() { return "comm/tableInfoPop.tiles"; }

    @GetMapping({"/tableAttrPop", "/tableAttrPop.jsp", "/comm/tableAttrPop", "/views/comm/tableAttrPop", "/views/comm/tableAttrPop.jsp"})
    public String tableAttrPop() { return "comm/tableAttrPop.tiles"; }

    @GetMapping({"/transactionMask", "/transactionMask.jsp", "/comm/transactionMask", "/views/comm/transactionMask", "/views/comm/transactionMask.jsp"})
    public String transactionMask() { return "comm/transactionMask.tiles"; }

    @GetMapping({"/transactionMaskPop", "/transactionMaskPop.jsp", "/comm/transactionMaskPop", "/views/comm/transactionMaskPop", "/views/comm/transactionMaskPop.jsp"})
    public String transactionMaskPop() { return "comm/transactionMaskPop.tiles"; }

    @GetMapping({"/workInfo", "/workInfo.jsp", "/comm/workInfo", "/views/comm/workInfo", "/views/comm/workInfo.jsp"})
    public String workInfo() { return "comm/workInfo.tiles"; }
}
