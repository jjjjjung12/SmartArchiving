<%@ page import="java.security.*"%>

<%!
	// 암호모듈 사용하게 하기위한 init 해당 부분을 하지않고 NCLoginENC.jsp 를 호출하면 최초 1회 에러가 발생한다.
	static{
			com.initech.provider.crypto.InitechProvider.addAsProvider(false,false);
	}

	
%>
<%
// 암호모듈 init 정상적으로 되었다면 NCLoginENC.jsp 리다이렉트
	if(Security.getProvider("Initech") != null) {
		response.sendRedirect("./NCLoginENC.jsp");
	}
%>