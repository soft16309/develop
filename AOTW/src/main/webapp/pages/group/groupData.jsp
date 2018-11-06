<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>群资料</title>
<%@include file="../../include/include.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/group/groupData.css" />
<script>var groupId = "${group.id}";</script>
<script type="text/javascript" src="<%=currentPath %>/groupData.js?v=<%=version %>"></script>
</head>

<body>
	<div id="main">
		<div id="header">
			<h2>群资料</h2>
			<div id="header-menu">
				<button>返回首页</button>
				<button>退出登录</button>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="center">
			<div id="left-div">
				<table>
					<tr>
						<td>群主</td>
						<td><a href="">${group.username}${group.username==user.username ? "（我）" : "" }</a></td>
					</tr>
					<tr>
						<td>群名称：</td>
						<td>
							${group.name }
						</td>
					</tr>
					<tr>
						<td>群规模：</td>
						<td>
							${group.scale }人
						</td>
					</tr>
					<tr>
						<td>群分类：</td>
						<td>
							${group.sort }
						</td>
					</tr>
					<tr>
						<td>群地点：</td>
						<td>${group.address }</td>
					</tr>
					<tr>
						<td style="vertical-align: top;">群介绍：</td>
						<td>
							<textarea name="description" style="height: 50px;vertical-align: bottom;" disabled>${group.description }</textarea>
						</td>
					</tr>
				</table>
			</div>
			<div id="right-div">
				<div>
					<div id="image-div">
						<img src="<%=contextPath %>${group.image}" />
					</div>
					<div id="label-image"><span>群头像</span></div>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="bottom">
			<button name="${group.username==user.username ? "ok" : "joinGroup" }">
			${group.username==user.username ? "确定" : "加入群聊" }
			</button>
		</div>
	</div>
</body>
</html>