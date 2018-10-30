var imgFormat = [ '.jpg', '.jpeg', '.png' ];

$(function() {
	init();
});

function init() {
	$("#image-div input[type='file']").change(function() {
		var file = this.files[0];
		var index = file.name.lastIndexOf(".");
		if (index < 0) {
			alert("图片格式不正确！");
			return;
		}
		var extName = file.name.substr(index);
		if (!imgFormat.contain(extName)) {
			alert("请选择jpg、jpeg、png格式的图片!");
			return;
		}
		loadImg($("#image-div img"), file);
	})
	
	$("input[name='username']").blur(function(){
		if (this.value.trim() == '') {
			this.value = '';
			$(this).next('span.tip').text('登录名不能为空！');
		} else {
			$(this).next('span.tip').text('');
		}
	});
	$("input[name='password']").blur(function(){
		if (this.value.trim()=='') {
			this.value = '';
			$(this).next('span.tip').text('密码不能为空！');
		} else if (this.value.length < 6 || this.value.length > 16) {
			$(this).next('span.tip').text('请输入6~16位密码！')
		} else {
			$(this).next('span.tip').text('');
		}
	});
	$("input[name='rePass']").blur(function() {
		if (this.value != $("input[name='password']").val()) {
			$(this).next('span.tip').text('两次密码不一致！');
		} else {
			$(this).next('span.tip').text('');
		}
	});
	$("input[name='nickname']").blur(function() {
		if (this.value.trim() == '') {
			this.value = '';
			$(this).next('span.tip').text('昵称不能为空！');	
		} else {
			$(this).next('span.tip').text('');
		}
	});
	$("input[name='phone']").blur(function() {
		if (this.value.trim() == '') {
			this.value  = '';
			$(this).next('span.tip').text('手机不能为空！');
		} else if (!(/^1[34578]\d{9}$/.test(this.value))) {
			$(this).next('span.tip').text('请填写有效的手机号码！');
		} else {
			$(this).next('span.tip').text('');
		}
	});
	$("input[name='email']").blur(function() {
		if (this.value.trim() == '') {
			this.value  = '';
			$(this).next('span.tip').text('邮箱不能为空！');
		} else if (!(/^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/.test(this.value))) {
			$(this).next('span.tip').text('请填写有效的邮箱地址！');
		} else {
			$(this).next('span.tip').text('');
		}
	});
	$("#bottom button").click(function() {
		if (!validate()) {
			return;
		}
		$("#register-form").ajaxSubmit({
			url : "${pageContext.request.contextPath}/user/register.do",
			type : "post",
			success : function(r) {
				if (r.isOk == 'Y') {
					alert("注册成功！");
					window.location = "login.jsp";
				} else {
					alert(r.msg);
				}
			}
		});
	});
}

function validate() {
	if ($("input[name='username']").val().trim() == '') {
		$("input[name='username']").next('span.tip').text('登录名不能为空！');
		return false;
	}
	if ($("input[name='password']").val().trim() == '') {
		$("input[name='password']").next('span.tip').text('密码不能为空！');
		return false;
	}
	if ($("input[name='rePass']").val().trim() == '') {
		$("input[name='rePass']").next('span.tip').text('两次密码不一致！');
		return false;
	}
	if ($("input[name='nickname']").val().trim() == '') {
		$("input[name='nickname']").next('span.tip').text('昵称不能为空！');
		return false;
	}
	if ($("input[name='phone']").val().trim() == '') {
		$("input[name='phone']").next('span.tip').text('手机不能为空！');
		return false;
	}
	if ($("input[name='email']").val().trim() == '') {
		$("input[name='email']").next('span.tip').text('邮箱不能为空！');
		return false;
	}
	return true;
}