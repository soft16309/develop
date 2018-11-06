<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>个人信息维护</title>
<%@include file="../../include/include.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/user/personInfo.css" />
<script type="text/javascript" src="<%=contextPath %>/js/jquery.form.js" ></script>
<script type="text/javascript" src="<%=currentPath %>/personInfo.js?v=<%=version %>"></script>
</head>

<body>
	<div id="main">
		<div id="header">
			<h2>个人信息维护</h2>
			<div id="header-menu">
				<button>返回首页</button>
				<button>退出登录</button>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="center">
			<form id="personInfo-form">
				<div id="left-div">
					<table>
						<tr>
							<td>登录名：</td>
							<td>
								${user.username }
								<input type="hidden" name="username" value="${user.username }" />
							</td>
						</tr>
						<tr>
							<td>用户真实姓名：</td>
							<td><input type="text" name="realname" value="${user.realname }" /></td>
						</tr>
						<tr>
							<td>昵称：</td>
							<td>
								<input type="text" name="nickname" value="${user.nickname }" />
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>手机号码：</td>
							<td>
								<input type="text" name="phone" value="${user.phone }" />
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>电子邮箱：</td>
							<td>
								<input type="text" name="email" value="${user.email }" />
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>联系地址：</td>
							<td><input type="text" name="address" value="${user.address }" /></td>
						</tr>
					</table>
				</div>
				<div id="right-div">
					<div>
						<div id="image-div">
							<img src="<%=contextPath %>${user.image }" />
							<input type="hidden" name="image" value="${user.image }" />
							<input type="file" name="file" />
						</div>
						<div id="label-image"><span>头像</span></div>
					</div>
				</div>
				<div style="clear: both;"></div>
			</form>
		</div>
		<div id="bottom">
			<button>确认</button>
		</div>
	</div>
</body>

</html>