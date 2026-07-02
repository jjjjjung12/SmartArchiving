<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8"/>
<meta name="_contextPath" content="${ctx}"/>
<link rel="stylesheet" href="${ctx}/css/css.css"/>
<link rel="stylesheet" href="${ctx}/css/archive-grid.css"/>
<link rel="stylesheet" href="${ctx}/css/fontawesome6.4.0/fontawesomecss/all.min.css"/>
<link rel="stylesheet" href="${ctx}/js/jquery-ui-1.13.2/jquery-ui.min.css"/>
<link rel="stylesheet" href="${ctx}/assets/css/jquery.timepicker.css"/>
<script src="${ctx}/js/jquery-3.7.1.min.js"></script>
<script src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/jquery-ui-1.13.2/jquery-ui.js"></script>
<script src="${ctx}/assets/js/jquery.timepicker.js"></script>
<script src="${ctx}/js/archive/archiveGrid.js"></script>
<script>var ctx='${ctx}';</script>
<title><%=request.getAttribute("pageTitle")!=null?request.getAttribute("pageTitle"):""%></title>
</head>
<body class="popup-body">
