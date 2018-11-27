<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>My WebSocket</title>
</head>
 
<body>
Welcome，${user.username }<br/>
<input id="text" type="text" /><button onclick="send()">发送字节流</button> <button onclick="send2()">发送普通消息</button><button onclick="send3()">发送json数据</button>   <button onclick="closeWebSocket()">Close</button>
<br />
<button onclick="sendXiaoming()">发送给小明</button>
<button onclick="sendXiaoming01()">发送给小明01</button>
<button onclick="sendMoniData()">发送模拟数据</button>
<div id="message">
</div>
</body>
<script type="text/javascript" src="base64.js"></script>
<script type="text/javascript">
    var websocket = null;
 
    //判断当前浏览器是否支持WebSocket
    if('WebSocket' in window){
        websocket = new WebSocket("ws://192.168.56.1:8080/AOTW/websocket/17C50A16A248A5DD89DB1626180AFCCB");
    }
    else{
        alert('Not support websocket')
    }
 
    //连接发生错误的回调方法
    websocket.onerror = function(){
        setMessageInnerHTML("error");
    };
 
    //连接成功建立的回调方法
    websocket.onopen = function(event){
        setMessageInnerHTML("open");
    }
 
    //接收到消息的回调方法
    websocket.onmessage = function(event){
    	var data = JSON.parse(event.data);
    	console.log(data);
        setMessageInnerHTML(event.data);
    }
 
    //连接关闭的回调方法
    websocket.onclose = function(){
        setMessageInnerHTML("close");
    }
 
    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function(){
        websocket.close();
    }
 
    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML){
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }
 
    //关闭连接
    function closeWebSocket(){
        websocket.close();
    }
 
    //发送消息
    function send(){
    	websocket.binaryType = 'arraybuffer';
        var message = document.getElementById('text').value;
        doSend(stringToByte(message));
    }
    
    function send2(){
    	websocket.binaryType = 'blob';
        var message = document.getElementById('text').value;
        websocket.send(message);
    }
    
    function send3() {
        var message = document.getElementById('text').value;
        var base = new Base64();
        var result = base.encode(message);
        console.log(result);
        var jsonStr = {from : "aaaa", to : "bbb", file : result};
        websocket.send(JSON.stringify(jsonStr));
    }
    
    function doSend(bytes) {
        var buffer = new ArrayBuffer(bytes.length + 4);
        var view = new DataView(buffer);
        view.setUint32(0, bytes.length);
        for (var i = 0; i < bytes.length; i++) {
            view.setUint8(i + 4, bytes[i]);
        }
        websocket.send(view);
    };
    
    function stringToByte(str) {
		var bytes = new Array();
		var len, c;
		len = str.length;
		for(var i = 0; i < len; i++) {
			c = str.charCodeAt(i);
			if(c >= 0x010000 && c <= 0x10FFFF) {
				bytes.push(((c >> 18) & 0x07) | 0xF0);
				bytes.push(((c >> 12) & 0x3F) | 0x80);
				bytes.push(((c >> 6) & 0x3F) | 0x80);
				bytes.push((c & 0x3F) | 0x80);
			} else if(c >= 0x000800 && c <= 0x00FFFF) {
				bytes.push(((c >> 12) & 0x0F) | 0xE0);
				bytes.push(((c >> 6) & 0x3F) | 0x80);
				bytes.push((c & 0x3F) | 0x80);
			} else if(c >= 0x000080 && c <= 0x0007FF) {
				bytes.push(((c >> 6) & 0x1F) | 0xC0);
				bytes.push((c & 0x3F) | 0x80);
			} else {
				bytes.push(c & 0xFF);
			}
		}
		return bytes;
	}
    
    function sendXiaoming(){
        var message = document.getElementById('text').value;
        var jsonStr = {from: {
			username: "xiaoming01",
			image: "${user.image}"
		}, to_user : "xiaoming", msg : message};
        websocket.send(JSON.stringify(jsonStr));
    }
    
    function sendMoniData() {
    	var data = moniData();
    	for (var i=0; i<data.length; i++) {
    		websocket.send(data[i]);
    	}
    }
    
    function moniData() {
		var data = [JSON.stringify({
				from: {
					username: "xiaoming",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hello,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming02",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hi,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming03",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming01",
				msg: 'hey,xiaoming0'
			}), JSON.stringify({
				from: {
					username: "xiaoming02",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: '你好,xiaoming01'
			}), JSON.stringify({
				from: {
					username: "xiaoming",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hello,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming02",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hi,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming03",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hey,xiaoming0'
			}), JSON.stringify({
				from: {
					username: "xiaoming02",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: '你好,xiaoming01'
			}), JSON.stringify({
				from: {
					username: "xiaoming",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hello,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming02",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hi,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming03",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hey,xiaoming0'
			}), JSON.stringify({
				from: {
					username: "xiaoming02",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: '你好,xiaoming01'
			}), JSON.stringify({
				from: {
					username: "xiaoming",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hello,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming02",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hi,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming03",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hey,xiaoming0'
			}), JSON.stringify({
				from: {
					username: "xiaoming02",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: '你好,xiaoming01'
			}), JSON.stringify({
				from: {
					username: "xiaoming",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hello,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming04",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hi,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming05",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hey,xiaoming0'
			}), JSON.stringify({
				from: {
					username: "xiaoming05",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: '你好,xiaoming01'
			}), JSON.stringify({
				from: {
					username: "xiaoming07",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hello,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming08",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hi,xiaoming01'
			}),
			JSON.stringify({
				from: {
					username: "xiaoming09",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hey,xiaoming0'
			}), JSON.stringify({
				from: {
					username: "xiaoming09",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: '你好,xiaoming01'
			}), JSON.stringify({
				from: {
					username: "xiaoming08",
					image: "../../images/u16.png"
				},
				to_user: "xiaoming",
				msg: 'hi,xiaoming01'
			})
		];
		return data;
	}
    
    function sendXiaoming01(){
        var message = document.getElementById('text').value;
        var jsonStr = {from : "xiaoming", to_user : "xiaoming01", msg : message};
        websocket.send(JSON.stringify(jsonStr));
    }
</script>
 
</html>