<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>创建群</title>
<%@include file="../../include/include.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/group/createGroup.css" />
<script type="text/javascript" src="<%=contextPath %>/js/jquery.form.js" ></script>
<script type="text/javascript" src="<%=currentPath %>/createGroup.js?v=<%=version %>"></script>
</head>

<body>
	<div id="main">
		<div id="header">
			<h2>创建群</h2>
			<div id="header-menu">
				<button>返回首页</button>
				<button>退出登录</button>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="center">
			<form id="createGroup-form">
				<input type="hidden" name="username" value="${user.username }" />
				<div id="left-div">
					<table>
						<tr>
							<td>群名称：</td>
							<td>
								<input type="text" name="name" />
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>群规模：</td>
							<td>
								<label><input type="radio" name="scale" value="50" checked="checked" />50人</label>
								<label><input type="radio" name="scale" value="100" />100人</label>
							</td>
						</tr>
						<tr>
							<td>群分类：</td>
							<td>
								<label><input type="radio" name="sort" value="旅行团" />旅行团</label>
								<label><input type="radio" name="sort" value="亲友" />亲友</label>
								<label><input type="radio" name="sort" value="同学同事" checked="checked" />同学同事</label>
							</td>
						</tr>
						<tr>
							<td>群地点：</td>
							<td><input type="text" name="address" /></td>
						</tr>
						<tr>
							<td style="vertical-align: top;">群介绍：</td>
							<td>
								<textarea name="description" style="height: 50px;vertical-align: bottom;"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div id="right-div">
					<div>
						<div id="image-div">
							<img src="<%=contextPath %>/img/defaultImg.png" />
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