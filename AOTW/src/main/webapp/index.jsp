<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>天下纵横</title>
		<%@include file="include/include.jsp" %>
		<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/index.css"/>
		<script type="text/javascript" src="<%=contextPath%>/js/jquery-1.7.2.min.js" ></script>
  		<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&amp;key=e8496e8ac4b0f01100b98da5bde96597"></script>
		<script type="text/javascript" src="<%=contextPath%>/js/geolocation.js" ></script>
		<script type="text/javascript" src="<%=currentPath%>/index.js?v=<%=version%>"></script>
	</head>
	<body>
		<div id="main">
			<div id="header">
				<div id="header-left">
					<h3>欢迎您，${user.username }</h3>
				</div>
				<div id="header-right">
					<input type="text" placeholder="搜索群" />
					<button id="intoBtn">进入</button>
				</div>
				<div style="clear: both;"></div>
			</div>
			<div id="center">
				<div id="center-left">
					<div id="center-left-menu">
						<button>个人信息维护</button>
						<button>退出登录</button>
						<button>创建群</button>
					</div>
					<input type="text" id="searchText" placeholder="搜索" />
					<div id="center-left-content">
						<div id="myCreateGroups">
							<p class="center-left-content-header">----我创建的群聊----</p>
							<c:forEach items="${createGroups }" var="group">
								<div class="group-tree">
									<div class="group-tree-head">
										<span class="icon icon-shrink" groupId="${group.id }"></span>
										<span class="groud-tree-head_content">${group.name }</span>
										<div style="clear: both;"></div>
									</div>
									<ul class="group-tree-content">
	
									</ul>
								</div>
							</c:forEach>
						</div>
						<div id="myJoinGroups">
							<p class="center-left-content-header">----我加入的群聊----</p>
							<c:forEach items="${joinGroups }" var="group">
								<div class="group-tree">
									<div class="group-tree-head">
										<span class="icon icon-shrink" groupId="${group.id }"></span>
										<span class="groud-tree-head_content">${group.name }</span>
										<div style="clear: both;"></div>
									</div>
									<ul class="group-tree-content">
										
									</ul>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<div id="center-right">
					<div id="mapContainer"></div>
				</div>
			</div>
		</div>
	</body>
</html>
