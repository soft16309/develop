<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="cn.gdqy.aotw.utils.JsonUtils" %>
<%
	response.setHeader("Pragma","No-cache");  
	response.setHeader("Cache-Control","no-cache");  
	response.setDateHeader("Expires", 0);  
	String currentPath = request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/")) ;
	String contextPath = request.getContextPath()=="/"?"":request.getContextPath();
	String version = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
%>

<script>
	var session = function(){
		this.getCurrentUser = function() {
			return <%=JsonUtils.objectToJson(session.getAttribute("user"))%>;
		}
		return this;
	}();
	var contextPath = "<%=contextPath%>";
	var currentPath = "<%=currentPath%>";
</script>