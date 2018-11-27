$(function() {
	var swiper = new Swiper('.swiper-container', {
		pagination: {
	       el: '.swiper-pagination',
	       type: 'fraction',
	    },
		navigation: {
	       nextEl: '.swiper-button-next',
	       prevEl: '.swiper-button-prev',
	    }
	});
});

function updateStatus(groupId, status, obj) {
	$.ajax({
		url : contextPath + "/group/updateGroupStatus.do",
		data : {
			groupId : groupId,
			status : status
		},
		success : function(r) {
			if (r.isOk == "Y") {
				alert("操作成功！");
				var buttons = $(obj).parent().find("button");
				debugger;
				for (var i=0; i<buttons.length; i++) {
					if ($(buttons[i]).attr('disabled') == "disabled") {
						$(buttons[i]).removeAttr("disabled");
					} else {
						$(buttons[i]).attr("disabled", "disabled");
					}
				}
			} else {
				alert(r.msg);
			}
		}
	})
}