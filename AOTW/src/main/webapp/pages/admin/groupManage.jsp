<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="cn.gdqy.aotw.pojo.Group" %>
<!DOCTYPE html>
<html>
<head>
<title>群管理</title>
<%@include file="../../include/include.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/admin/common.css?v=<%=version%>"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/swiper.min.css?v=<%=version%>"/>
<script type="text/javascript" src="<%=contextPath%>/js/swiper.min.js" ></script>
<script type="text/javascript" src="<%=currentPath%>/groupManage.js?v=<%=version%>"></script>
</head>
<body>
	<div id="main">
		<div id="header">
			<h1>群管理</h1>
			<div id="searchDiv">
				<form action="<%=contextPath %>/group/groupManage.do" method="post">
					<input type="text" name="name" placeholder="请输入群名称" value="${param.name }" />
					<input type="submit" value="搜索" />
				</form>
			</div>
			<a href="<%=contextPath %>/page/toIndex.do">回到首页</a>
		</div>
		<div class="swiper-container" id="resultList">
			<div class="swiper-wrapper">
				<%
				List<Group> groupList = (List<Group>)request.getAttribute("groupList");
				if (groupList != null) {
					Byte status = (byte)1;
					for(int i=0; i<groupList.size(); i++) { %>
						<div class="swiper-slide">
						<% for (int j=0; i<groupList.size() && j<20; j++,i++) { 
								Group group = groupList.get(i);%>
								<div class="item">
									<img src="<%=contextPath+group.getImage() %>" />
									<p><%=group.getName() %></p>
									<div>
									<% if (group.getStatus().equals(status)) { %>
										<button disabled="disabled" onclick="updateStatus('<%=group.getId()%>', 1, this)")">启用</button>
										<button onclick="updateStatus('<%=group.getId()%>', 0, this)")">禁用</button>
									<% } else { %>
										<button onclick="updateStatus('<%=group.getId()%>', 1, this)")">启用</button>
										<button disabled="disabled" onclick="updateStatus('<%=group.getId()%>', 0, this)">禁用</button>
									<% } %>
									</div>
								</div>
						<% } %>
						</div>
					<% } %>
				<% } %>
			</div>
			
			<div class="swiper-pagination"></div>
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>
		</div>
	</div>
</body>
</html>