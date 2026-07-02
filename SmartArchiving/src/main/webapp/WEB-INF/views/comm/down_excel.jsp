<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "down_excel"); %>
<div class="popup_wrap_inner">
	<div class="pop_header"><h3 class="tit_pop">down_excel</h3></div>
	<div class="pop_body"><%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder, java.net.URLEncoder" %>

<%
      try {   

          String docName  = "";
          String data = request.getParameter("csvBuffer");       //table스크립트 전체
          String fileName = request.getParameter("fileName");    //파일이름

          //헤더 선택
          String header = request.getHeader("User-Agent");
          if (header.contains("MSIE"))       { header = "MSIE";   }   
          else if(header.contains("Chrome")) { header = "Chrome"; } 
          else if(header.contains("Opera"))  { header  = "Opera"; }

          if (data != null && fileName != null ) {
               data = URLDecoder.decode(data, "UTF-8");
               fileName = URLDecoder.decode(fileName, "UTF-8");
               response.setContentType("application/vnd.ms-excel");

               if (header.contains("MSIE")) {
                    docName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
                    response.setHeader("Content-Disposition", "attachment;filename=" + docName + ".xls;");
               } else if (header.contains("Firefox")) {
                    docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1"); 
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + ".xls\"");
               } else if (header.contains("Opera")) {
                    docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1"); 
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + ".xls\"");
               } else if (header.contains("Chrome")) {
                    docName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1"); 
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + docName + ".xls\"");
               }

               response.setHeader("Pragma", "no-cache;");
               response.setHeader("Expires", "-1;");
               out.println(data);
          }//end of if
      } catch ( Exception e ) {
           e.printStackTrace();
           out.println(e.toString());
      }
%>
</div>
</div>
