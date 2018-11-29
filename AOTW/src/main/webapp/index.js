var currentPosition;
var map;
var timer;
$(function() {
	//获取当前位置(方法名)
//	mMap.getSessionLocation(locationFunc);
	getCurrentLocation();

	//初始化群聊树
	initGroupTree();
	//初始化左边三个菜单（个人信息维护、退出登录、创建群）
	initLeftMenu();
	//初始化搜索菜单
	initSearchMenu();
	//初始化【进入】按钮
	initIntoBtn();
	//绑定管理员菜单点击事件
	bindSystemManageMenuListener();
});

function getCurrentLocation() {
	//用户位置定位   使用geolocation定位
	AMap.plugin('AMap.Geolocation', function() {
	    var geolocation = new AMap.Geolocation({
	        enableHighAccuracy: true,//是否使用高精度定位，默认:true
	        timeout: 10000,          //超过10秒后停止定位，默认：5s
	        buttonPosition:'RB',    //定位按钮的停靠位置
	        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
	        zoomToAccuracy: true,   //定位成功后是否自动调整地图视野到定位点

	    });
	    geolocation.getCurrentPosition(function(status,result){
	        if(status=='complete'){
	            onComplete(result)
	        }else{
	            onError(result)
	        }
	    });
	});
	//解析定位结果
	function onComplete(data) {
		initMap(data.position);
		currentPosition = data.position;
		$.ajax({
			url : contextPath + "/user/updateLocation.do",
			type : "post",
			data : {
				username : session.getCurrentUser().username,
				location : JSON.stringify(currentPosition)
			},
			success : function(r) {
			}
		});
		markerPosition(map, data.position, session.getCurrentUser());
	}
	//解析定位错误信息
	function onError(data) {
		var str = '定位失败,';
		str += '错误信息：'
		switch(data.info) {
			case 'PERMISSION_DENIED':
				str += '浏览器阻止了定位操作';
				break;
			case 'POSITION_UNAVAILBLE':
				str += '无法获得当前位置';
				break;
			case 'TIMEOUT':
				str += '定位超时';
				break;
			default:
				str += '未知错误';
				break;
		}
		alert(str)
	}
}

function initGroupTree() {
	$(".group-tree-head").dblclick(function(){
		clearTimeout(timer);
		var $iconSpan = $(this).find("span.icon");
		var $groupTreeContent = $(this).next('ul.group-tree-content');
		var classes = $iconSpan.attr('class');
		if(classes.indexOf('icon-shrink') > 0) {
		   	if ($groupTreeContent.find("li").length > 0) { 
				$groupTreeContent.children().remove();
			}
			$.ajax({
				url : contextPath + "/group/getAllGroupMember.do",
				type : "post",
				async : false,
				data : {groupId : $iconSpan.attr('groupId')},
				success : function(r) {
					var li = "<li>"+r.data.groupOwner.username+"</li>";
					$groupTreeContent.append(li);
					if (r.data.userList) {
						var userList = r.data.userList;
						for (var i=0; i<userList.length; i++) {
							li = "<li>"+userList[i].username+"</li>";
							$groupTreeContent.append(li);
						}
					}
				}
			});
			//绑定群成员的点击事件
			bindGroupMemberClickListener($groupTreeContent);
			$iconSpan.removeClass('icon-shrink');
			$iconSpan.addClass('icon-extend');
			$groupTreeContent.css('display', 'block');
		} else {
			$iconSpan.removeClass('icon-extend');
			$iconSpan.addClass('icon-shrink');
			$(this).next('ul.group-tree-content').css('display', 'none');
		}
	});
	
	$(".group-tree-head").click(function(){
		clearTimeout(timer);
		var $iconSpan = $(this).find("span.icon");
		var $groupTreeContent = $(this).next('ul.group-tree-content');
	    timer = setTimeout(function() {
			$.ajax({
				url : contextPath + "/group/getAllGroupMember.do",
				type : "post",
				async : false,
				data : {groupId : $iconSpan.attr('groupId')},
				success : function(r) {
					debugger;
					map.destroy();
					initMap(currentPosition);
					markerPosition(map, currentPosition, session.getCurrentUser());
					if (r.data.userList) {
						var userList = r.data.userList;
						for (var i=0; i<userList.length; i++) {
							var position = JSON.parse(userList[i].location);
							markerPosition(map, position, userList[i]);
						}
					}
					map.setFitView();
				}
			});
	    }, 300);
	});
}

function bindGroupMemberClickListener($ul) {
	$ul.on('click', 'li', function() {
		var username = $(this).text();
		map.destroy();
		initMap(currentPosition);
		markerPosition(map, currentPosition, session.getCurrentUser());
		if (username != session.getCurrentUser().username) {
			$.ajax({
				url : contextPath + "/user/findUser.do?username=" + username,
				async : false,
				success : function(r) {
					if (r.isOk == "Y") {
						var user = r.data.user;
						var position = JSON.parse(user.location);
						markerPosition(map, position, user);
					} else {
						alert(r.msg);
					}
					map.setFitView();
				}
			});
		}
	});
}

//初始化左边的菜单
function initLeftMenu() {
	var buttons = $("#center-left-menu button");
	var $updatePersonInfoBtn = $(buttons[0]);
	var $logoutBtn = $(buttons[1]);
	var $createGroupBtn = $(buttons[2]);
	
	$updatePersonInfoBtn.click(function() {
		window.location = contextPath + "/pages/user/personInfo.jsp";
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
	
	$createGroupBtn.click(function() {
		window.location = contextPath + "/pages/group/createGroup.jsp";
	});
}

function initSearchMenu() {
	$("#header-right input[name='name']").bind('input propertychange', function() {
		var $obj = $(this);
		$.ajax({
			url : contextPath + "/group/fuzzySearchGroupsByName.do",
			data : {name : $obj.val()},
			success : function(r) {
				if (r.isOk == "Y") {
					debugger;
					var ul = $("#groupList ul:first-child");
					ul.empty();
					if (r.data.groupList && r.data.groupList.length > 0) {
						var li;
						var groupList = r.data.groupList;
						for (var i=0; i<groupList.length; i++) {
							li = '<li groupId="'+groupList[i].id+'">'+groupList[i].name+'</li>';
							ul.append($(li));
						}
					} else {
						ul.html("没有找到匹配的结果");
					}
					bindFoundGroupLiClick();
					$("#groupList").show();
				} else {
					alert(r.msg);
				}
			}
		})
	});
	
	$("#header-right input[name='name']").blur(function() {
		//防止点击群聊的li元素没来得及获取焦点就被隐藏而导致点击事件失效
		setTimeout(function(){
			$("#groupList").hide();
		}, 300);
	})
}

//绑定搜索得到的群聊的点击事件
function bindFoundGroupLiClick() {
	var $ul = $("#groupList ul:first-child");
	$ul.on('click', 'li', function() {
		$ul.html('<input type="hidden" name="selectGroup" value="'+$(this).attr('groupId')+'" />')
		$("#header-right input[name='name']").val($(this).text());
	});
}

function initMap(position) {
	map = new AMap.Map('mapContainer', {
		resizeEnable: true,
		center: [position.lng, position.lat],
		zoom: 13
	});
}

function initIntoBtn() {
	$("#intoBtn").click(function() {
		var groupId = $("#groupList ul:first-child").find("input[name='selectGroup']").val();
		if (groupId) {
			window.location = contextPath + "/group/toGroupData.do?groupId=" + groupId;
		} else {
			alert("找不到该群！");
		}
	});
}

function markerPosition(map, position, user) {
	var content = [];
	var src = contextPath + user.image; 
	content.push('<img src="'+src+'" />');
	var marker = new AMap.Marker({
		position: [position.lng, position.lat], //位置
		content : createInfoWindow(content, user)
	})
	map.add(marker); //添加到地图
}

//构建自定义信息窗体
function createInfoWindow(content, user) {
	var info = document.createElement("div");

	// 定义中部内容
	var middle = document.createElement("div");
	if (session.getCurrentUser().username == user.username) {
		middle.className = "middle-red";
	} else {
		middle.className = "middle";
	}
	middle.innerHTML = content;
	info.appendChild(middle);

	// 定义向下指向箭头
	var sharp = document.createElement("span");
	if (session.getCurrentUser().username == user.username) {
		sharp.className = "sharp-red";
	} else {
		sharp.className = "sharp";
	}
	info.appendChild(sharp);

	return info;
}

function bindSystemManageMenuListener() {
	$("#systemManage").click(function() {
		$("#menuList").toggle();
	});
	var $ul = $("#menuList ul");
	var menus = $ul.find("li");
	console.log(menus)
	var $groupManage = $(menus[0]);
	$groupManage.click(function() {
		window.location = contextPath + "/pages/admin/groupManage.jsp";
	});
	
	var $userManage = $(menus[1]);
	$userManage.click(function() {
		window.location = contextPath + "/pages/admin/userManage.jsp";
	});
}