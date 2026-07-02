package com.archiving.magicview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MagicViewPageController {

    @GetMapping({"/MagicView", "/MagicView.jsp", "/magicview/MagicView", "/views/magicview/MagicView", "/views/magicview/MagicView.jsp"})
    public String magicView() { return "magicview/MagicView.tiles"; }

    @GetMapping({"/MagicView_back20210512", "/MagicView_back20210512.jsp", "/magicview/MagicView_back20210512", "/views/magicview/MagicView_back20210512", "/views/magicview/MagicView_back20210512.jsp"})
    public String magicViewBack20210512() { return "magicview/MagicView_back20210512.tiles"; }

    @GetMapping({"/asisnhbankIlog", "/asisnhbankIlog.jsp", "/magicview/asisnhbankIlog", "/views/magicview/asisnhbankIlog", "/views/magicview/asisnhbankIlog.jsp"})
    public String asisnhbankIlog() { return "magicview/asisnhbankIlog.tiles"; }

    @GetMapping({"/jsonDetail", "/jsonDetail.jsp", "/magicview/jsonDetail", "/views/magicview/jsonDetail", "/views/magicview/jsonDetail.jsp"})
    public String jsonDetail() { return "magicview/jsonDetail.tiles"; }

    @GetMapping({"/jsonDetailAsis", "/jsonDetailAsis.jsp", "/magicview/jsonDetailAsis", "/views/magicview/jsonDetailAsis", "/views/magicview/jsonDetailAsis.jsp"})
    public String jsonDetailAsis() { return "magicview/jsonDetailAsis.tiles"; }

    @GetMapping({"/jsonDetailYkh", "/jsonDetailYkh.jsp", "/magicview/jsonDetailYkh", "/views/magicview/jsonDetailYkh", "/views/magicview/jsonDetailYkh.jsp"})
    public String jsonDetailYkh() { return "magicview/jsonDetailYkh.tiles"; }

    @GetMapping({"/statLogAsis", "/statLogAsis.jsp", "/magicview/statLogAsis", "/views/magicview/statLogAsis", "/views/magicview/statLogAsis.jsp"})
    public String statLogAsis() { return "magicview/statLogAsis.tiles"; }

    @GetMapping({"/statlog", "/statlog.jsp", "/magicview/statlog", "/views/magicview/statlog", "/views/magicview/statlog.jsp"})
    public String statlog() { return "magicview/statlog.tiles"; }

    @GetMapping({"/statReport", "/statReport.jsp", "/magicview/statReport", "/views/magicview/statReport", "/views/magicview/statReport.jsp"})
    public String statReport() { return "magicview/statReport.tiles"; }

    @GetMapping({"/transactionIlog", "/transactionIlog.jsp", "/magicview/transactionIlog", "/views/magicview/transactionIlog", "/views/magicview/transactionIlog.jsp"})
    public String transactionIlog() { return "magicview/transactionIlog.tiles"; }

    @GetMapping({"/transactionIlogAsis", "/transactionIlogAsis.jsp", "/magicview/transactionIlogAsis", "/views/magicview/transactionIlogAsis", "/views/magicview/transactionIlogAsis.jsp"})
    public String transactionIlogAsis() { return "magicview/transactionIlogAsis.tiles"; }

    @GetMapping({"/transactionIlogIpBand", "/transactionIlogIpBand.jsp", "/magicview/transactionIlogIpBand", "/views/magicview/transactionIlogIpBand", "/views/magicview/transactionIlogIpBand.jsp"})
    public String transactionIlogIpBand() { return "magicview/transactionIlogIpBand.tiles"; }
}
