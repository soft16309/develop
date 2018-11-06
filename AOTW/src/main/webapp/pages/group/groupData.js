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
	
	//加入群聊
	$("#bottom button").click(function() {
		if($(this).attr('name') == "joinGroup") {
			$.ajax({
				url : contextPath + "/group/joinGroup.do?groupId=" + groupId,
				success : function(r) {
					if (r.isOk == 'Y') {
						alert("已成功加入群聊！");
						window.location = contextPath + "/page/toIndex.do";
					} else {
						alert(r.msg);
					}
				}
			});
		} else {
			window.location = contextPath + "/page/toIndex.do";
		}
	});
}