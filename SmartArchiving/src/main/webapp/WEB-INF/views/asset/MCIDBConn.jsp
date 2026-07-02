<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
session.removeAttribute("username");
session.removeAttribute("userid"   );
session.removeAttribute("usercd"   );
session.removeAttribute("groupid"   );
session.removeAttribute("picture"  );
session.removeAttribute("approwait");

%>
<%@ page import="java.io.*" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.lang.Long" %>
<%@ page import="java.sql.*" %>
<%@ page  import="org.json.simple.JSONObject" %>
<%@ page  import="org.json.simple.parser.JSONParser"%>
<%


Connection conn = null ;
String driver = "com.mysql.jdbc.Driver";
String url = "jdbc:mysql://16.88.249.66:3306/NBMCIDT?autoReconnect=true&amp;serverTimezone=UTC&amp;characterEncoding=utf8&amp;characterSetResults=utf8&amp;connectionAttributes=program_name:[MCI]&amp;useSSL=false";
try{
	ResultSet rest= null;
Class.forName(driver);
conn = DriverManager.getConnection(url, "EFAB_PKG","EFAB_PKG");

out.println("DB connection success ===> <br>" );

 String selStmt  = "select sysdate() as ddd from dual";
 PreparedStatement ps = null;
 ps = conn.prepareStatement(selStmt);

 rest = ps.executeQuery();
 int cnt=0;
 while( rest.next()){
	 out.println("select return ===>" + rest.getString("ddd")  +"<br>" );
	 cnt++;
 }
 out.println("select return cnt ===>" + cnt +"<br>" );
 if(cnt == 0 ){// insert 구문입니다.
	 System.out.println(selStmt+";");
 }else{
	 
 }

conn.close();
}catch(Exception e){
	e.printStackTrace();
}




%>


	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="description" content="MCI DB Connection Test" />
	<meta name="author" content="" />

	<link rel="icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico">

	<title>MCI DB Connection Test</title>

