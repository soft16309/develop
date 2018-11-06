var imgFormat = [ '.jpg', '.jpeg', '.png' ];

$(function() {
	init();
});

function init() {
	var buttons = $("#header-menu button");
	var $toIndexBtn = $(buttons[0]);
	var $logoutBtn = $(buttons[1]);
	$toIndexBtn.click(function() {
		window.location = contextPath + "/page/toIndex.do";
	});
	$logoutBtn.click(function() {
		$.ajax({
			url : contextPath + "/user/logout.do",
			success : function(r) {
				if (r.isOk == "Y") {
					window.location = contextPath + "/login.jsp";
				}
			}
		})
	});
	
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
	$("input[name='name']").blur(function() {
		if (this.value.trim() == '') {
			this.value = '';
			$(this).next('span.tip').text('群名称不能为空！');	
		} else {
			$(this).next('span.tip').text('');
		}
	});
	
	$("#bottom button").click(function() {
		if (!validate()) {
			return;
		}
		$("#createGroup-form").ajaxSubmit({
			url : contextPath + "/group/createGroup.do",
			type : "post",
			success : function(r) {
				if (r.isOk == 'Y') {
					alert("创建成功！");
					window.location = contextPath + "/page/toIndex.do";
				} else {
					alert(r.msg);
				}
			}
		});
	});
}

function validate() {
	if ($("input[name='name']").val().trim() == '') {
		$("input[name='name']").next('span.tip').text('昵称不能为空！');
		return false;
	}
	return true;
}