var currentPosition;
var timer;
$(function() {
	//获取当前位置(方法名)
	mMap.getSessionLocation(locationFunc);

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
					mMap.map.destroy();
					initMap(currentPosition);
					markerPosition(mMap.map, currentPosition, session.getCurrentUser());
					if (r.data.userList) {
						var userList = r.data.userList;
						for (var i=0; i<userList.length; i++) {
							var position = JSON.parse(userList[i].location);
							markerPosition(mMap.map, position, userList[i]);
						}
					}
					mMap.map.setFitView();
				}
			});
	    }, 300);
	});

	
});
	
function locationFunc() {
	var data = JSON.parse(sessionStorage.getItem("location"));
	initMap(data.position);
	currentPosition = data.position;
	markerPosition(mMap.map, data.position, session.getCurrentUser());
}

function initMap(position) {
	mMap.map = new AMap.Map('mapContainer', {
		resizeEnable: true,
		center: [position.lng, position.lat],
		zoom: 13
	});
}

function markerPosition(map, position, user) {
	var content = [];
	var src = contextPath + user.image; 
	content.push('<img src="'+src+'" />');
	var marker = new AMap.Marker({
		position: [position.lng, position.lat], //位置
		content : createInfoWindow(content)
	})
	map.add(marker); //添加到地图

	
//	var infoWindow = new AMap.InfoWindow({
//		isCustom: true, //使用自定义窗体
//		content: createInfoWindow(content),
//		offset: new AMap.Pixel(0, 6)
//	});
//	infoWindow.open(map, marker.getPosition());
}

//构建自定义信息窗体
function createInfoWindow(content) {
	var info = document.createElement("div");

	// 定义中部内容
	var middle = document.createElement("div");
	middle.className = "middle";
	middle.innerHTML = content;
	info.appendChild(middle);

	// 定义向下指向箭头
	var sharp = document.createElement("span");
	sharp.className = "sharp";
	info.appendChild(sharp);

	return info;
}
