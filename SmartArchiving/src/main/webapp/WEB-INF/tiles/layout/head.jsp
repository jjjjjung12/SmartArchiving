<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta charset="utf-8"/>
<meta name="_contextPath" content="${ctx}"/>
<meta name="_csrfToken" content="${_csrf.token}"/>
<meta name="_csrfName" content="${_csrf.parameterName}"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<link rel="stylesheet" href="${ctx}/css/css.css"/>
<link rel="stylesheet" href="${ctx}/css/archive-grid.css"/>
<link rel="stylesheet" href="${ctx}/css/fontawesome6.4.0/fontawesomecss/all.min.css"/>
<link rel="stylesheet" href="${ctx}/assets/css/font-icons/entypo/css/entypo-embedded.css"/>
<link rel="stylesheet" href="${ctx}/js/jquery-ui-1.13.2/jquery-ui.min.css"/>
<link rel="stylesheet" href="${ctx}/assets/css/jquery.timepicker.css"/>
<link rel="stylesheet" href="${ctx}/assets/css/w2ui-1.5.css"/>
<script src="${ctx}/js/jquery-3.7.1.min.js"></script>
<script src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/design.js"></script>
<script src="${ctx}/js/jquery-ui-1.13.2/jquery-ui.js"></script>
<script src="${ctx}/assets/js/jquery.form.js"></script>
<script src="${ctx}/assets/js/jquery.timepicker.js"></script>
<script src="${ctx}/assets/js/w2ui-1.5.js"></script>
<script src="${ctx}/js/lib/jszip.min.js"></script>
<script src="${ctx}/js/common/common_time.js"></script>
<script src="${ctx}/js/archive/archiveGrid.js"></script>
<script src="${ctx}/js/archive/archiveMenu.js"></script>
<script>var ctx = '${ctx}';</script>
<c:choose>
	<c:when test="${not empty pageTitle}"><title>${pageTitle}</title></c:when>
	<c:otherwise><title>SmartArchiving</title></c:otherwise>
</c:choose>