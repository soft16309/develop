<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注册</title>
<link rel="stylesheet" type="text/css" href="css/register.css" />
<script src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js" ></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="register.js"></script>
</head>

<body>
	<div id="main">
		<div id="header">
			<h2>注册</h2>
		</div>
		<div id="center">
			<form id="register-form">
				<div id="left-div">
					<table>
						<tr>
							<td>登录名：</td>
							<td>
								<input type="text" name="username" /> 
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>登录密码：</td>
							<td>
								<input type="password" name="password" /> 
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>确认密码：</td>
							<td>
								<input type="password" name="rePass" />
								<span class="tip"></span>	
							</td>
						</tr>
						<tr>
							<td>用户真实姓名：</td>
							<td><input type="text" name="realname" /></td>
						</tr>
						<tr>
							<td>昵称：</td>
							<td>
								<input type="text" name="nickname" />
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>手机号码：</td>
							<td>
								<input type="text" name="phone" />
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>电子邮箱：</td>
							<td>
								<input type="text" name="email" />
								<span class="tip"></span>
							</td>
						</tr>
						<tr>
							<td>联系地址：</td>
							<td><input type="text" name="address" /></td>
						</tr>
					</table>
				</div>
				<div id="right-div">
					<div>
						<div id="image-div">
							<img src="img/u16.png" /> <input type="file" name="file" />
						</div>
						<div id="label-image">
							<span>头像</span>
						</div>
					</div>
				</div>
				<div style="clear: both;"></div>
			</form>
			]
		</div>
		<div id="bottom">
			<button>提交注册</button>
		</div>
	</div>
</body>

</html>