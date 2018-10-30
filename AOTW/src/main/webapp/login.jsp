<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>登录</title>
		<link rel="stylesheet" type="text/css" href="css/login.css"/>
		<script src="js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.form.js" ></script>
		<script type="text/javascript" src="login.js"></script>
	</head>
	<body>
		<div id="main">
			<div id="header"><img src="img/header.png" /></div>
			<div><img src="img/backgroud.png" /></div>
			<div id="form-div">
				<form>
					<table>
						<tr>
							<td>用户名：</td>
							<td>
								<input type="text" name="username" />
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>密&nbsp;码：</td>
							<td>
								<input type="password" name="password" />
								<span class="tip"></span>
							</td>
						</tr>
					</table>
				</form>
				<div id="menu">
					<button>注册</button>
					<button>登录</button>
				</div>
			</div>
		</div>
	</body>
</html>
