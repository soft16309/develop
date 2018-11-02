$(function(){
	init();
});

function init() {
	$("input[name='username']").blur(function() {
		if (this.value.trim() == '') {
			this.value = '';
			$(this).next('span.tip').text("请输入登录名！");
		} else {
			$(this).next('span.tip').text("");
		}
	})
	$("input[name='password']").blur(function() {
		if (this.value.trim() == '') {
			this.value = '';
			$(this).next('span.tip').text("请输入密码！");
		} else {
			$(this).next('span.tip').text("");
		}
	})
	debugger;
	var buttons = $("#menu button");
	var $regBtn = $(buttons[0]);
	var $loginBtn = $(buttons[1]);
	$regBtn.click(function(){
		window.location = "register.jsp";
	})
	$loginBtn.click(function(){
		if (!validate()) {
			return;
		}
		
		$("#login-form").ajaxSubmit({
			url : contextPath + "/user/login.do",
			type : "post",
			success : function(r) {
				if (r.isOk == 'Y') {
					window.location = contextPath + "/page/toIndex.do";
				} else {
					alert(r.msg);
				}
			}
		});
	})
}

function validate() {
	if ($("input[name='username']").val().trim() == '') {
		$("input[name='username']").next('span.tip').text('请输入登录名！');
		return false;
	}
	if ($("input[name='password']").val().trim() == '') {
		$("input[name='password']").next('span.tip').text('请输入密码！');
		return false;
	}
	return true;
}