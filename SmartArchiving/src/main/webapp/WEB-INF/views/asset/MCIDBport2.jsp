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
<%@ page import="java.net.*" %>
<%@ page  import="org.json.simple.JSONObject" %>
<%@ page  import="org.json.simple.parser.JSONParser"%>
<%

try{
Socket aa = new Socket("16.88.249.58",3306);
out.println("socket ok : 16.88.249.58 : 3306 <br>" );
if( aa != null ){
	Socket bb = new Socket("16.88.249.59",3306);
	out.println("socket ok : 16.88.249.59 : 3306 <br>" );
	
	
}

Socket ddd = new Socket("16.88.249.66",3306);
out.println("socket ok : 16.88.249.66 : 3306 <br>" );
Socket eee =  new Socket("16.88.249.53",3306);
out.println("socket ok : 16.88.249.53 : 3306 <br>" );
 new Socket("16.88.249.54",3306);
 out.println("socket ok : 16.88.249.54 : 3306 <br>" );
//new Socket("16.88.249.65",3306);
// out.println("socket ok : 16.88.249.65 : 3306 <br>" );
// //new Socket("19.88.248.60",3306); 
// //out.println("socket ok : 19.88.248.60 : 3306 <br>" );
// // new Socket("19.88.248.61",3306);
//  //out.println("socket ok : 19.88.248.61 : 3306 <br>" );

// // new Socket("19.88.248.68",3306);
// // out.println("socket ok : 19.88.248.68 : 3306 <br>" );

}catch(IOException e){
	
	out.println("socket error" + e.getStackTrace().toString() );
	
}




%>


	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="description" content="MCI DB Port Test" />
	<meta name="author" content="" />

	<link rel="icon" href="${pageContext.request.contextPath}/assets/images/favicon.ico">

	<title>MCI DB Port Test 2</title>

<script>
// function maskingString( str, start, end, maskChar){
// 	if( !str || start < 0 || start >= str.length || end  < 0 || end > str.length || start >= end){
// 		return str ;
// 	}
// 	const maskLength = end - start ;
// 	const maskedStr = str.substring(0, start) + maskChar.repeat(maskLength) + str.substring(end);
	
// 	return maskedStr ;
	
// }

// const inputStr = "9876543210";
// const maskedStr = maskingString(inputStr , 2, 8 ,"*");

// console.log(  maskedStr );

</script>
