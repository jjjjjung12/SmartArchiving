<%@ page language="java"%> 
<%@ page contentType="text/html;charset=EUC-KR"%>
<%@page import="com.initech.provider.crypto.InitechProvider"%>
<%@page import="com.initech.provider.crypto.Provider"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.util.List"%>
<%@page import="com.initech.eam.nls.TicketV3"%>

<%@ include file="/js/lib/cutCarriageReturn.jsp" %>
<%
	List res = null;
	String userid = null;
	String toa = null;
	String strNLS = null;
	String ASCP_URL = ""; // ????????? URL
	//String Login_URL = "https://ssodev.nonghyup.com:15502/"; (????)
	//String Login_URL = "https://sso.nonghyup.com:15502/";      (??)
	
	try {
		String ticket = (String) request.getParameter("ticket");
		strNLS = (String) request.getParameter("nlsurl");  //???
		String savedNonce = (String) session.getAttribute("nexess.nls.resession.nonce");
		
		//20230829 428206, 428207, 428212, 428216, 428217 NULL_RETURN_STD (Java/JSP)
		//20230829 428209, 428210, 428211, 428215, 428218 UNCHECKED_NULL (Java/JSP)
		int firstIndex        = 0;
		int secondeIndex      = 0;
		String encNonce       = "";
		String encSKIPAndTime = "";
		String encIDAndTOA    = "";
		//
		
		if (ticket != null) {
			ticket = URLDecoder.decode(ticket);
			//20230829 428206, 428207, 428212, 428216, 428217 NULL_RETURN_STD (Java/JSP)
			//20230829 428209, 428210, 428211, 428215, 428218 UNCHECKED_NULL (Java/JSP)
			firstIndex        = ticket.indexOf("&&");
			secondeIndex      = ticket.lastIndexOf("&&");
			encNonce       = ticket.substring(0, firstIndex);
			encSKIPAndTime = ticket.substring(firstIndex + 2, secondeIndex);
			encIDAndTOA    = ticket.substring(secondeIndex + 2);
			//
		}
		
		if (strNLS != null){
			strNLS = URLDecoder.decode(strNLS);
		}
		
		
		String decNonce = null;
		try {
			decNonce = cutCarriageReturn(TicketV3.decryptNonce(encSKIPAndTime, encNonce));
		} catch (Exception e) {
			System.out.println("NCLoginEncVerifyTicket decNonce cutCarriageReturn Exception");
			//??????? ????? ???? ????��? ????
			/* if(strNLS != null && !"".equals(strNLS)){
				strNLS = strNLS.replaceAll("[\\r\\n]", "");
				if(strNLS == Login_URL){
					response.sendRedirect(strNLS); //???�٥�? ???
				} else {
					System.out.println("?��??? URL ????.");
				}
			} */
		}
			
	
		if (decNonce.equals(savedNonce)) {
		
			res = TicketV3.readIDAndTOA(encSKIPAndTime, encIDAndTOA);
			userid = (String) res.get(0);
			toa = (String) res.get(1);
			
			session.setAttribute("sso_id", userid);
			session.setAttribute("toa", toa);
			response.sendRedirect(request.getContextPath() + "/");
			
		}else{
			System.out.println("NCLoginEncVerifyTicket decNonce, savedNoce not equals");
			//??????? ????? ???? ????��? ????
			/* if(strNLS != null && !"".equals(strNLS)){
				strNLS = strNLS.replaceAll("[\\r\\n]", "");
				if(strNLS == Login_URL){
					response.sendRedirect(strNLS); //????? ???
				} else {
					System.out.println("?��??? URL ????.");
				}
			} */
		}
	} catch (Exception e) {
		System.out.println("?????? ????! ????��??? ???!");
		//??????? ????? ???? ????��? ????
		/* if(strNLS != null && !"".equals(strNLS)){
			strNLS = strNLS.replaceAll("[\\r\\n]", "");
			if(strNLS == Login_URL){
				response.sendRedirect(strNLS); //????? ???
			} else {
				System.out.println("?��??? URL ????.");
			}
		} */
	}

%>

<html>
<body>
<b>Nexess Login Agent</b><br>
Login Success<br>
&nbsp&nbsp&nbsp&nbsp userid = [<%=userid%>]<br>
&nbsp&nbsp&nbsp&nbsp toa = [<%=toa%>]<br>
</body>
</html> 