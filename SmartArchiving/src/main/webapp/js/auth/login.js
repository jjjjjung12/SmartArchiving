(function ($) {
    "use strict";

    var EXPIRE_WARN_DISMISS_PREFIX = "smartArchiving.authExpireWarn.dismiss.";

    function getTodayYmd() {
        var d = new Date();
        var m = ("0" + (d.getMonth() + 1)).slice(-2);
        var day = ("0" + d.getDate()).slice(-2);
        return "" + d.getFullYear() + m + day;
    }

    function isExpireWarnDismissedToday(userCd) {
        if (!userCd || !window.localStorage) {
            return false;
        }
        return localStorage.getItem(EXPIRE_WARN_DISMISS_PREFIX + userCd) === getTodayYmd();
    }

    function showExpireWarnPopup(expireDate, remainDays, userCd, onDone) {
        var expirePretty = expireDate && expireDate.length === 8
            ? (expireDate.substr(0, 4) + "-" + expireDate.substr(4, 2) + "-" + expireDate.substr(6, 2))
            : (expireDate || "");
        var remainText = (remainDays === 0 || remainDays === "0") ? "오늘" : (remainDays + "일");
        var dismissToday = window.confirm(
            "권한 만료 예정일: " + expirePretty + " (잔여 " + remainText + ")\n"
            + "확인을 누르면 메인으로 이동합니다.\n"
            + "취소를 누르면 권한 연장 신청 화면으로 이동합니다."
        );
        if (dismissToday) {
            if ($("#chk-expire-warn-dismiss-today").length && $("#chk-expire-warn-dismiss-today").is(":checked")) {
                localStorage.setItem(EXPIRE_WARN_DISMISS_PREFIX + userCd, getTodayYmd());
            }
            onDone();
        } else {
            window.location.href = ctx + "/userAuthApplyLogin";
        }
    }

    function handleExpireWarnFromQuery() {
        var params = new URLSearchParams(window.location.search);
        if (params.get("expireWarn") !== "true") {
            return;
        }
        var userCd = sessionSsoId || "";
        if (isExpireWarnDismissedToday(userCd)) {
            return;
        }
        showExpireWarnPopup(
            params.get("expireDate") || "",
            params.get("remainDays") || "0",
            userCd,
            function () {}
        );
    }

    $(document).ready(function () {
        $("#sso_login").on("click", function () {
            window.location.href = ctx + "/sso/index.jsp";
        });

        if (sessionSsoId && sessionSsoId !== "null" && sessionSsoId !== "") {
            $("#password").removeAttr("required");
            $("#username").val(sessionSsoId);
            $("#form_login").trigger("submit");
        }

        handleExpireWarnFromQuery();
    });
})(jQuery);
