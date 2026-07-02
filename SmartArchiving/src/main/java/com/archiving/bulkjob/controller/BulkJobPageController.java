package com.archiving.bulkjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BulkJobPageController {

    @GetMapping({"/bulkInquiryJobReq", "/bulkInquiryJobReq.jsp", "/bulkjob/bulkInquiryJobReq", "/views/bulkjob/bulkInquiryJobReq", "/views/bulkjob/bulkInquiryJobReq.jsp"})
    public String bulkInquiryJobReq() { return "bulkjob/bulkInquiryJobReq.tiles"; }

    @GetMapping({"/bulkInquiryJobResult", "/bulkInquiryJobResult.jsp", "/bulkjob/bulkInquiryJobResult", "/views/bulkjob/bulkInquiryJobResult", "/views/bulkjob/bulkInquiryJobResult.jsp"})
    public String bulkInquiryJobResult() { return "bulkjob/bulkInquiryJobResult.tiles"; }

    @GetMapping({"/bulkReqListPop", "/bulkReqListPop.jsp", "/bulkjob/bulkReqListPop", "/views/bulkjob/bulkReqListPop", "/views/bulkjob/bulkReqListPop.jsp"})
    public String bulkReqListPop() { return "bulkjob/bulkReqListPop.tiles"; }

    @GetMapping({"/bulkReqListPop_ykh", "/bulkReqListPop_ykh.jsp", "/bulkjob/bulkReqListPop_ykh", "/views/bulkjob/bulkReqListPop_ykh", "/views/bulkjob/bulkReqListPop_ykh.jsp"})
    public String bulkReqListPopYkh() { return "bulkjob/bulkReqListPop_ykh.tiles"; }

    @GetMapping({"/exportDateHistory", "/exportDateHistory.jsp", "/bulkjob/exportDateHistory", "/views/bulkjob/exportDateHistory", "/views/bulkjob/exportDateHistory.jsp"})
    public String exportDateHistory() { return "bulkjob/exportDateHistory.tiles"; }

    @GetMapping({"/searchBulkInquiryJobReq", "/searchBulkInquiryJobReq.jsp", "/bulkjob/searchBulkInquiryJobReq", "/views/bulkjob/searchBulkInquiryJobReq", "/views/bulkjob/searchBulkInquiryJobReq.jsp"})
    public String searchBulkInquiryJobReq() { return "bulkjob/searchBulkInquiryJobReq.tiles"; }
}
