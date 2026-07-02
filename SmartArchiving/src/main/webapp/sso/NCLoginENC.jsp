<%@ page language="java"%> 
<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.initech.provider.crypto.InitechProvider"%>
<%@ page import="com.initech.provider.crypto.Provider"%>
<%@ page import="com.initech.cryptox.util.Base64Util"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.initech.eam.nls.Ticket"%>
<%@ page import="com.initech.eam.nls.CookieManager"%>
<%@ page import="java.security.SecureRandom"%>
<%@ page import="com.initech.eam.nls.NLSHelper"%>

<%@ include file="/js/lib/cutCarriageReturn.jsp" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<%


	//브라우져 타입 검출 
	//////////////////////////////////////////////////////////
	Boolean bIE = false;
	String browser = "";
	String userAgent = request.getHeader("User-Agent");
		
	if (userAgent.indexOf("Trident")>0 )
	{
		bIE = true;
	}
	//////////////////////////////////////////////////////////

	/* 
	  #noCacheNonce : 프록시에서의 캐싱을 방지하기 위한 Cookie와 RequestParameter로 이용됨
	  1) LoginFormPage와 resession.jsp에서 생성하고, 그 이후 페이지에 RequestParameter로 넘겨준다.
	     -> NLS에서 Page이동시 항상 넘기고 받도록 한다.
	  2) LoginFormPage와 resession.jsp에서 이 이름의 Cookie를 생성해주도록 한다.    
	 */
	  
    // -- nonce 생성
	SecureRandom random = new SecureRandom();
	byte[] nonce = new byte[8];
	random.nextBytes(nonce);
	
	String nonceStr = cutCarriageReturn(new String(Base64Util.encode(nonce)));	//Ticket 사용을 위한 nonce 값 생성
	session.setAttribute("nexess.nls.resession.nonce", nonceStr); //Ticket 사용을 위한 nonce 값 세션에 저장
		
	Boolean bNewSSO = bIE; //브라우저 체크를 위한 값

%>
<!-- [if !IE] -->
<script language="javascript">

	var bBrowser = <%=bIE%>;
	
	if (bBrowser){
		if(navigator.userAgent.indexOf('MSIE 10') == -1 && navigator.userAgent.indexOf('Trident') == -1) {
			  //alert('사용중인 IE가 IE10 이 아님');
			 document.write("<embed id=\"NEXESS_API\"  type=\"application/initech/npNexessClient,version=2,6,1,1\" width=0 height=0 ><br>");
		}else{
			 //alert('사용중인 IE가 10임');
			 document.write("<OBJECT ID=\"NEXESS_API\" CLASSID=\"CLSID:D4F62B67-8BA3-4A8D-94F6-777A015DB612\" width=0 height=0></OBJECT>");
		}
	}
</script>
<!-- [endif] -->



<!-- Non-ActvieX 를 사용하기 위한 js파일 해당 파일이 호출이 안되면 Non-ActiveX 사용불가-->
<script charset='utf-8' src="${pageContext.request.contextPath}/js/lib/NexessDemonFunc.js"></script>
<script language=javascript>

var bBrowser2 = <%=bIE%>; //브라우저 체크

//팝업창을 닫기위한 function
function fn_close() {
	if(navigator.appVersion.indexOf("MSIE 7.0")>=0 || navigator.appVersion.indexOf("MSIE 8.0")>=0) {
		window.opener = "nothing";
		window.open('', '_parent', '');
		window.close();		
	} else { 
		window.opener = self; 
		self.close();
	}
}


	if (bBrowser2) //신규 SSO분기 처리 브라우저가 IE면 true
	{
	
	try{
		if( NEXESS_API.Islogin() == 0 ) { //NexessClient 가 로그인되어있는지 확인 값이 0이라면 로그인되어있지 않으므로 통합로그인페이지로 이동.
			//self.location.replace(LoginUrl + "?UURL=" + USER_URL + "&RTOA=1"); // 통합로그인페이지로 이동
			alert("통합로그인필요!");
		}else{
			
			var ticket = NEXESS_API._GetTicket("<%=nonceStr%>"); // NexessClient 에서 Ticket 발행을 위해 위에서 생성한 nonce값을 넣어준다.
			if(ticket.length == 1 || ticket == "") {  //로그인이 되어 있지 않은 상태 로그인 페이지로 이동
				//self.location.replace(LoginUrl + "?UURL=" + USER_URL + "&RTOA=1"); // 통합로그인페이지로 이동
				alert("통합로그인필요!");
			}else{
				while(ticket.indexOf("+") != -1) {
					ticket= ticket.replace("+", "%2b"); // Ticket 값에서 +를 %2b로 치환
				}
					// 아래의 TOA 값으로 개발서버 로그인 후 운영서버를 접근방지 / 운영서버 로그인 후 개발서버 접근을 방지하기 위한 로직.
					//if( NEXESS_API.GetLoginTOA() == "92" || NEXESS_API.GetLoginTOA() == "93" )  // 통합인증 개발서버 로그인, 92 : 인증서로그인, 93 : ID/PW 로그인
					if( NEXESS_API.GetLoginTOA() == "2" || NEXESS_API.GetLoginTOA() == "3" )  // 통합인증 운영서버 로그인, 2 : 인증서로그인, 3 : ID/PW 로그인 
					{
						alert("통합로그인 개발서버 인증 시 운영 접속 불가.");
						fn_close();
					} else {
						self.location.replace("./NCLoginEncVerifyTicket.jsp?ticket="+escape(ticket)); //Ticket 값 파라미터를 붙혀 검증페이지로 이동		
					}
				
			}
		}
	}catch(e){
		//농협 NC가 설치 되지 않은경우
		alert("농협은행 통합로그인을 위해서는 NexessClient설치가 필요합니다.");
		fn_close();
	}
	}else{
		//여기서 부터 신규 SSO non-activeX 처리 코드
		try{
			
			Nexess_GetConnectNLSURL(Callback_GetConnectNLSURL); // 통합로그인페이지 URL 을 받아오기위해 최초 호출 Callback_GetConnectNLSURL 함수로 이동. 
			
		}
		catch(e){
			alert("로그인정보를 찾을 수 없습니다.");
			fn_close();
		}
		/////////////////////////////////
			
	}


	
function Callback_IsLogin(responseData)
{
		var res = responseData.msg;
	
		if(res == 0){ //NexessClient 가 로그인되어있는지 확인 값이 0이라면 로그인되어있지 않으므로 통합로그인페이지로 이동.
			//self.location.replace(SSO_LOGIN_URL + "nls/clientLogin.jsp?UURL=" + USER_URL + "&RTOA=1"); // 통합로그인페이지로 이동.
			//var Login_URL = "https://ssodev.nonghyup.com:15502/"; //(개발)          
			var Login_URL = "https://sso.nonghyup.com:15502/";  //(운영)        
			alert("통합로그인필요!");
			self.location.replace(Login_URL); // 통합로그인페이지로 이동.
		}else{
			Nexess_GetLoginToa(Callback_GetLoginToa); // 통합로그인이 되어있다면 TOA 값을 체크하기 위해 Callback_GetLoginToa 이동
	}
}

function Callback_GetConnectNLSURL(responseData)
{
	if (responseData.resultCode == '0') // 통합로그인 URL을 정상적으로 얻어오는지 확인한다.
	{
		alert("NLSURL 획득 실패 운영자에게 확인 부탁드립니다.");
		fn_close();		
	}
	else
	{
		SSO_LOGIN_URL = responseData.msg; // 정상적으로 통합로그인페이지 URL 을 확인했다면 SSO_LOGIN_URL 에 담는다.
		Nexess_IsLogin(Callback_IsLogin); // NexessClient(통합로그인) 가 로그인되어있는지 확인을 위해 Callback_IsLogin 이동
	}

}


function Callback_GetTicket(responseData)
{
	var ticket = responseData.msg;
	if(ticket.length == 1 || ticket == "") {  //로그인이 되어 있지 않은 상태 로그인 페이지로 이동
		//self.location.replace(SSO_LOGIN_URL + "?UURL=" + USER_URL + "&RTOA=1");
		alert("통합로그인필요!");
	}else{
		while(ticket.indexOf("+") != -1) {
			ticket= ticket.replace("+", "%2b"); // Ticket 값에서 +를 %2b로 치환
		}
		
		var NLSURLEnc = encodeURIComponent(SSO_LOGIN_URL);   //NC에서 받아온 로그인 URL 전달
		
		self.location.replace("./NCLoginEncVerifyTicket.jsp?ticket="+escape(ticket) + "&nlsurl="+ NLSURLEnc ); // ticket 검증을위해 ticket값과 검증실패시 돌아갈 통합로그인URL을 보낸다.
	}
}

function Callback_PrepareResession(responseData)
{
	
	if(responseData.resultCode == '1'){ // 통합로그인이 정상적으로 되어있는 상태라면 resultCode 값이 1이 나온다.
		Nexess_GetTicketWithNonce(Callback_GetTicket,"<%=nonceStr%>"); // ticket 값 생성 요청으로 Callback_GetTicket 이동
	}
}


function Callback_GetLoginToa(responseData)
{
	var toa = responseData.msg
	
	// 아래의 TOA 값으로 개발서버 로그인 후 운영서버를 접근방지 / 운영서버 로그인 후 개발서버 접근을 방지하기 위한 로직.
	if(toa == "92" || toa == "93"){ // 통합인증 개발서버 로그인 92 : 인증서로그인, 93 : ID/PW 로그인
	//if(toa == "2" || toa == "3"){ // 통합인증 운영서버 로그인 2 : 인증서로그인, 3 : ID/PW 로그인
		alert("통합로그인 개발서버 인증 시 운영 접속 불가, 로그아웃 후 운영 로그인 후 접속 하세요.");
	}else{
		Nexess_PrepareResession(Callback_PrepareResession, "<%=nonceStr%>"); //TOA 검증이 끝나고 TicTket 값을 생성하기 위해 nonce 값과 같이 Callback_PrepareResession 이동
	}
	
}
	
</script>
</body>
