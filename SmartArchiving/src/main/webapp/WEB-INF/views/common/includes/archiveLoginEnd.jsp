<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String sso_id = (String) session.getAttribute("sso_id");
%>
				</div>
			</section>
		</div>
	</div>
	<footer class="footer login-footer"><div><p class="lgrey">Copyright &copy; A1 Communications Korea Inc.</p></div></footer>
</div>
<div class="loading_bg" id="loginLoading"><div class="loading"></div></div>
<script>var sessionSsoId="<%=sso_id!=null?sso_id:""%>"; var archiveCtx = "${pageContext.request.contextPath}";</script>
<script src="${pageContext.request.contextPath}/js/auth/login.js"></script>
</body>
</html>
