package com.archiving.auth.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * API 엔드포인트 -> 필요 메뉴 화면 경로 조각(소문자, MenuAuthService 매칭용).
 */
public final class ApiMenuAuthorization {

    private ApiMenuAuthorization() {
    }

    /** 비로그인 공개 화면에서 호출하는 API (세션·메뉴 권한 없이 허용). */
    private static final Set<String> PUBLIC_APIS = Set.of(
            "GetUserAuthApply",
            "SetUserAuthApply",
            "GetUserApproveProcList",
            "SetUserApproveProc"
    );

    /** 로그인만 필요하고 메뉴 권한은 보지 않는 API (공통 팝업·메뉴 로드 등). */
    private static final Set<String> LOGIN_ONLY_APIS = Set.of(
            "GetMenuList",
            "GetMenuList2",
            "GetLogin",
            "GetDeptList",
            "GetEmpList",
            "GetUserListPop",
            "GetCodeList",
            "GetNoticeManageList",
            "SetNoticeChkDate",
            "GetDownloadReq",
            "SetDownloadReq",
            "GetUserApproveStatSearchListPop",
            "GetServerCombo",
            "GetCertiKey"
    );

    /** 로그인만 필요한 보조 화면(JSP) 경로 조각. */
    private static final Set<String> LOGIN_ONLY_PAGE_FRAGMENTS = Set.of(
            "/",
            "/index",
            "/jsondetail",
            "/jsondetailasis",
            "/jsondetailykh",
            "/downloadreqpop",
            "/userauthapplypop",
            "/noticemanagesetpop",
            "/userauthapplydetailpop",
            "/bulkreqlistpop",
            "/transactionmaskpop",
            "/updateserviceinfopop",
            "/modaldept",
            "/modalsabun",
            "/down_excel",
            "/footerinfo",
            "/headerinfo",
            "/menuinfo"
    );

    private static final Map<String, String> API_MENU_FRAGMENT;

    static {
        Map<String, String> m = new HashMap<>();
        // user
        m.put("GetUserList", "userinfo");
        m.put("SetUser", "userinfo");
        m.put("GetUserLogList", "userloglist");
        m.put("GetUserGroup", "usergroup");
        m.put("SetUserGroup", "usergroup");
        m.put("SetUserGroupMember", "usergroup");
        m.put("GetAuthList", "userauth");
        m.put("SetAuthList", "userauth");
        m.put("SetMenuList", "menumanager");
        // magicview
        m.put("GetIlogHeader", "transactionilog");
        m.put("GetIlogIpBandHeader", "transactionilogipband");
        m.put("GetSlogHeader", "statlog");
        m.put("GetASISIlogHeader", "transactionilogasis");
        m.put("GetASISSlogHeader", "statlogasis");
        m.put("GetServiceReport", "statreport");
        m.put("GetASISSameTranIDData", "transactionilogasis");
        m.put("GetLayoutMeta", "transactionilog");
        m.put("GetASISLayoutMeta", "transactionilogasis");
        m.put("GetSameTranIDData", "transactionilog");
        m.put("MagicView", "magicview");
        // job
        m.put("GetLoadJobList", "loadjobmst");
        m.put("SetLoadJob", "loadjobmst");
        m.put("GetLoadJobHistory", "loadjobhistory");
        m.put("GetCnvJobList", "cnvjobmst");
        m.put("SetCnvJob", "cnvjobmst");
        m.put("GetCnvJobHistory", "cnvjobhistory");
        m.put("GetExportJobList", "exportjobmst");
        m.put("SetExportJob", "exportjobmst");
        m.put("GetExportJobHistory", "exportjobhistory");
        m.put("GetReorgJobList", "reorgjobmst");
        m.put("SetReorgJob", "reorgjobmst");
        m.put("GetReorgJobHistory", "reorgjobhistory");
        m.put("GetDeleteJobList", "deletejobmst");
        m.put("SetDeleteJob", "deletejobmst");
        m.put("GetDeleteJobHistory", "deletejobhistory");
        m.put("GetJobMonitor", "jobmonitor");
        m.put("GetJobMonitor2back", "jobmonitor");
        m.put("GetJobHistory", "jobinfo");
        m.put("SetJob", "jobinfo");
        m.put("GetExportDateHistory", "exportdatehistory");
        m.put("GetScheduleList", "scheduleinfo");
        m.put("SetScheduleList", "scheduleinfo");
        m.put("GetBatchList", "scheduleinfo");
        m.put("SetBatchList", "scheduleinfo");
        m.put("GetEttSrcJobList", "srcjobmst");
        m.put("GetSQLTest", "scheduleinfo");
        m.put("GetDBConnectivity", "scheduleinfo");
        // asset
        m.put("GetServerList", "serverinfo");
        m.put("SetServer", "serverinfo");
        m.put("GetAgentList", "agentinfo");
        m.put("GetAgentMonitorList", "agent");
        m.put("SetAgent", "agentinfo");
        m.put("GetDaemonList", "daemoninfo");
        m.put("SetDaemon", "daemoninfo");
        m.put("GetServiceInfo", "serviceinfo");
        m.put("SetServiceInfo", "serviceinfo");
        m.put("SetServiceInfoAdmin", "serviceinfoadmin");
        m.put("SetServiceInfoOne", "serviceinfo");
        m.put("ParseServiceInfoReq", "serviceinfo");
        // bulk
        m.put("GetBulkReqJobList", "bulkinquiryjobreq");
        m.put("GetBulkIReqJobList", "bulkinquiryjobreq");
        m.put("GetBulkInquiryJobReqList", "bulkinquiryjobreq");
        m.put("SetBulkInquiryJobReq", "bulkinquiryjobreq");
        m.put("ParseBulkInquiryJobReq", "bulkinquiryjobreq");
        m.put("GetBulkInquiryJobResultList", "bulkinquiryjobresult");
        m.put("SearchBulkInquiryJobReq", "searchbulkinquiryjobreq");
        // comm
        m.put("GetTableList", "tableinfo");
        m.put("SetTable", "tableinfo");
        m.put("GetTableListPop", "tableinfo");
        m.put("GetReportList", "reportsinfo");
        m.put("GetReportsList", "reportsinfo");
        m.put("SetReport", "reportsinfo");
        m.put("GetWorkList", "workinfo");
        m.put("SetWork", "workinfo");
        m.put("SetNoticeManage", "noticemanage");
        m.put("GetCodeManager", "codemanager");
        m.put("SetCodeManager", "codemanager");
        // approve
        m.put("GetUserApproveProcAdminList", "userapproveprocadmin");
        m.put("GetUserApproveStatSearchList", "userapprovestatsearch");
        m.put("GetUserApproveStatSearchListPop", "userapprovestatsearch");
        m.put("GetDownloadReqList", "downloadreqhistory");
        m.put("GetMaskingHistoryList", "maskinghistory");
        m.put("SetTranMaskReq", "transactionmask");
        m.put("GetTranMaskList", "transactionmask");
        m.put("SetTranMask", "transactionmask");
        // monitor
        m.put("GetMonitor", "dashboard");
        m.put("GetEncDecRst", "encdec");
        m.put("GetDataDecRst", "dataDec");
        API_MENU_FRAGMENT = Collections.unmodifiableMap(m);
    }

    public static boolean isPublicApi(String servletPath) {
        String name = servletApiName(servletPath);
        return name != null && PUBLIC_APIS.contains(name);
    }

    public static boolean isLoginOnlyApi(String servletPath) {
        String name = servletApiName(servletPath);
        return name != null && LOGIN_ONLY_APIS.contains(name);
    }

    private static String servletApiName(String servletPath) {
        if (servletPath == null || servletPath.isEmpty()) {
            return null;
        }
        String name = servletPath;
        if (name.startsWith("/")) {
            name = name.substring(1);
        }
        int slash = name.indexOf('/');
        if (slash > 0) {
            name = name.substring(0, slash);
        }
        return name;
    }

    public static boolean isLoginOnlyPage(String normalizedPath) {
        if (normalizedPath == null) {
            return false;
        }
        String lower = normalizedPath.toLowerCase(Locale.ROOT);
        for (String frag : LOGIN_ONLY_PAGE_FRAGMENTS) {
            if (lower.equals(frag) || lower.endsWith(frag) || lower.contains(frag + "/")) {
                return true;
            }
        }
        return false;
    }

    /**
     * API가 요구하는 메뉴 조각. 매핑 없으면 {@code null} (화면 URL 직접 매칭으로 판단).
     */
    public static String requiredMenuFragment(String servletPath) {
        if (servletPath == null || servletPath.isEmpty()) {
            return null;
        }
        String name = servletPath;
        if (name.startsWith("/")) {
            name = name.substring(1);
        }
        int slash = name.indexOf('/');
        if (slash > 0) {
            name = name.substring(0, slash);
        }
        return API_MENU_FRAGMENT.get(name);
    }

    public static Set<String> loginOnlyApiNames() {
        return LOGIN_ONLY_APIS;
    }
}
