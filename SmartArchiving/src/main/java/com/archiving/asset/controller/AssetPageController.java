package com.archiving.asset.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssetPageController {

    @GetMapping({"/MCIDBConn", "/MCIDBConn.jsp", "/asset/MCIDBConn", "/views/asset/MCIDBConn", "/views/asset/MCIDBConn.jsp"})
    public String mciDbConn() { return "asset/MCIDBConn.tiles"; }

    @GetMapping({"/MCIDBport", "/MCIDBport.jsp", "/asset/MCIDBport", "/views/asset/MCIDBport", "/views/asset/MCIDBport.jsp"})
    public String mciDbPort() { return "asset/MCIDBport.tiles"; }

    @GetMapping({"/MCIDBport2", "/MCIDBport2.jsp", "/asset/MCIDBport2", "/views/asset/MCIDBport2", "/views/asset/MCIDBport2.jsp"})
    public String mciDbPort2() { return "asset/MCIDBport2.tiles"; }

    @GetMapping({"/agentInfo", "/agentInfo.jsp", "/asset/agentInfo", "/views/asset/agentInfo", "/views/asset/agentInfo.jsp"})
    public String agentInfo() { return "asset/agentInfo.tiles"; }

    @GetMapping({"/agentMonitor", "/agentMonitor.jsp", "/asset/agentMonitor", "/views/asset/agentMonitor", "/views/asset/agentMonitor.jsp"})
    public String agentMonitor() { return "asset/agentMonitor.tiles"; }

    @GetMapping({"/daemonInfo", "/daemonInfo.jsp", "/asset/daemonInfo", "/views/asset/daemonInfo", "/views/asset/daemonInfo.jsp"})
    public String daemonInfo() { return "asset/daemonInfo.tiles"; }

    @GetMapping({"/daemonInfoPop", "/daemonInfoPop.jsp", "/asset/daemonInfoPop", "/views/asset/daemonInfoPop", "/views/asset/daemonInfoPop.jsp"})
    public String daemonInfoPop() { return "asset/daemonInfoPop.tiles"; }

    @GetMapping({"/serverInfo", "/serverInfo.jsp", "/asset/serverInfo", "/views/asset/serverInfo", "/views/asset/serverInfo.jsp"})
    public String serverInfo() { return "asset/serverInfo.tiles"; }

    @GetMapping({"/serverInfoPop", "/serverInfoPop.jsp", "/asset/serverInfoPop", "/views/asset/serverInfoPop", "/views/asset/serverInfoPop.jsp"})
    public String serverInfoPop() { return "asset/serverInfoPop.tiles"; }

    @GetMapping({"/serviceInfo", "/serviceInfo.jsp", "/asset/serviceInfo", "/views/asset/serviceInfo", "/views/asset/serviceInfo.jsp"})
    public String serviceInfo() { return "asset/serviceInfo.tiles"; }

    @GetMapping({"/serviceInfoAdmin", "/serviceInfoAdmin.jsp", "/asset/serviceInfoAdmin", "/views/asset/serviceInfoAdmin", "/views/asset/serviceInfoAdmin.jsp"})
    public String serviceInfoAdmin() { return "asset/serviceInfoAdmin.tiles"; }

    @GetMapping({"/updateServiceInfoPop", "/updateServiceInfoPop.jsp", "/asset/updateServiceInfoPop", "/views/asset/updateServiceInfoPop", "/views/asset/updateServiceInfoPop.jsp"})
    public String updateServiceInfoPop() { return "asset/updateServiceInfoPop.tiles"; }
}
