<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setAttribute("pageTitle", "fileRead"); %>
<div class="popup_wrap_inner">
	<div class="pop_header"><h3 class="tit_pop">fileRead</h3></div>
	<div class="pop_body"><!DOCTYPE html>

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
<%@ page import="java.lang.Long" %>\
<%@ page import="java.sql.*" %>
<%@ page  import="org.json.simple.JSONObject" %>
<%@ page  import="org.json.simple.parser.JSONParser"%>
<%


String filePath = "D:\\pgm\\efblap\\data\\incomming\\convert\\slog";
File dir = new File(filePath );
if( dir.exists() && dir.isDirectory()){
	
	File[] files = dir.listFiles();
	if( files != null ){
		for( File file : files){
			
			out.println("file ===> " + file.getName() + "<br>");
			String tFileName = filePath + "\\" + file.getName() ;
			//BufferedReader br = new BufferedReader( new FileReader(tFileName));
			BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(tFileName),StandardCharsets.UTF_8));
			
			 String lineData;
			 while ((lineData = br.readLine()) != null) {
				// out.println("line Data ===> " +lineData + "<br>");
				 JSONParser jsonParser = new JSONParser();	
				  JSONObject jsonObj    = (JSONObject)  jsonParser.parse( lineData );
				  
				  String[] colName = {"service_id","service_name","creadted_time","application_group_id","success_yn","success_count","fail_count","os1","os2","os3","os4","os5","os6"};
				  String colNameString = String.join(",", colName);
				  String inStmt = "INSERT INTO EFBL.tb_ef_bl_svc( "+ colNameString + ")  values(" ;
				  String insertColumns ="";
				  for(int i=0; i< colName.length; i++){
					  if( i != 0 ) insertColumns += "," ;
					  out.println("json Data=[" + colName[i] + "]#### Data###"+  jsonObj.get(colName[i]) + "<br>" );
					 // insertColumns += ",\"" + jsonObj.get(colName[i]) + "\"";
					  
					  if( i == 2 ){  //date time
						  insertColumns += "\'" + jsonObj.get(colName[i]) + "\'";
					  }else if ( i == 0 ||  i == 1 || i == 3){  //text
						  insertColumns += "\'" + jsonObj.get(colName[i]).toString() + "\'";
					  }else{ //bigint
						  insertColumns += Long.parseLong( jsonObj.get(colName[i]).toString() ) ;
					  }
					  
				  }
				  
				  inStmt += insertColumns + ")";
					  
					
				
					
// 					 create table
// 					  "master"."efbl"."tb_ef_bl_svc" (
// 					    "service_id" text null,
// 					    "service_name" text null,
// 					    "creadted_time" datetime null,
// 					    "application_group_id" text null,
// 					    "success_yn" bigint null,
// 					    "success_count" bigint null,
// 					    "fail_count" bigint null,
// 					    "os1" bigint null,
// 					    "os2" bigint null,
// 					    "os3" bigint null,
// 					    "os4" bigint null,
// 					    "os5" bigint null,
// 					    "os6" bigint null
// 					  );
					 
  	
				    	System.out.println("inStmt############################## :" + inStmt);
				    	
				    	Connection conn = null ;
				    	String driver = "com.sqream.jdbc.SQDriver";
				    	String url = "jdbc:Sqream://16.120.0.44:3108/master;user=sqream;cluster=true;";
				    	try{

				    	Class.forName(driver);
				    	conn = DriverManager.getConnection(url, "sqream","sqream");
 
				    	out.println("DB connection success ===> <br>" );
				    	
				    	 PreparedStatement ps = null;
			    	//	 ps = conn.prepareStatement(inStmt);
			    	//	 ps.execute();
				    	conn.close();
				    	}catch(Exception e){
				    		e.printStackTrace();
				    	}
			    		
					   //insert
				  }
			 br.close();
			 }
			 out.println("line Data end @@@@@@@@@@@@@@@@@@@@@@@@@@@ " + "<br>");
		
		}
	}



%>


	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="description" content="Neon Admin Panel" />
	<meta name="author" content="" />

	<title>SmartArchiving</title>

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->


</head>


</body>
</html></div>
</div>
