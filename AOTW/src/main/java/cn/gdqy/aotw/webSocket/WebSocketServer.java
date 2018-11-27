package cn.gdqy.aotw.webSocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import cn.gdqy.aotw.common.GlobalConstant;
import cn.gdqy.aotw.common.ResultView;
import cn.gdqy.aotw.pojo.Group;
import cn.gdqy.aotw.pojo.User;
import cn.gdqy.aotw.service.GroupMemberService;
import cn.gdqy.aotw.service.MessageService;
import cn.gdqy.aotw.utils.JsonUtils;
import cn.gdqy.aotw.utils.SessionContext;
import cn.gdqy.aotw.utils.SpringContextHelper;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @author zrz
 */
@ServerEndpoint("/websocket/{sessionId}")
public class WebSocketServer {
	
	//注入spring创建的service对象
	private MessageService messageService = SpringContextHelper.getBean(MessageService.class);
	private GroupMemberService groupMemberService = SpringContextHelper.getBean(GroupMemberService.class);
	
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    public static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<String, WebSocketServer>();
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    //标识哪个用户
    private String username;

    /**
     * 连接建立成功调用的方法
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value="sessionId") String sessionId, Session session) {
        this.session = session;
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    	HttpSession httpSession = SessionContext.getInstance().getSession(sessionId);  
    	if (httpSession != null && httpSession.getAttribute("user") != null) {
    		User user = (User) httpSession.getAttribute("user");
    		if (messageService.isStorageExist(user.getUsername())) {
    			List<String> content = messageService.getStorageContent(user.getUsername());
    			try {
					sendMessage(JsonUtils.objectToJson(content));
					messageService.dropStrorage(user.getUsername());
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		username = user.getUsername();
    		webSocketMap.put(username, this);
    	} else {
    		try {
				sendMessage("你没有登录");
	    		session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(this.username);
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	//如果是心跳维持帧，则不处理
    	if (GlobalConstant.WEBSOCKET_HEARTBEAT.equals(message)) {
    		return;
    	}
        WebSocketMessage msg = JsonUtils.jsonToPojo(message, WebSocketMessage.class);
        if (msg.getTo_user() != null) {		//单聊
        	try {
				WebSocketServer server = webSocketMap.get(msg.getTo_user());
				if (server != null) {
					server.sendMessage(message);
				} else {
					//用户不在线时将消息暂时保存到离线消息仓库，以便用户上线时推送给用户
					messageService.saveContentToStorage(msg.getTo_user(), message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else if (msg.getTo_group() != null) {	 //群聊
        	Group group = msg.getTo_group();
        	ResultView result = groupMemberService.findAllGroupMember(group.getId());
        	List<User> groupMembers = (List<User>) result.getData("userList");
        	groupMembers.add((User)result.getData("groupOwner"));	//添加群主到发送队列
        	if (groupMembers != null && !groupMembers.isEmpty()) {
	        	for (User groupMember : groupMembers) {
	        		if (groupMember.getUsername().equals(username)) {
	        			//自己则不用发送
	        			continue;
	        		}
	        		try {
	        			WebSocketServer server = webSocketMap.get(groupMember.getUsername());
	        			if (server != null) {
	    					server.sendMessage(message);
	    				} else {
	    					messageService.saveContentToStorage(groupMember.getUsername(), message);
	    				}
	        		} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	        	}
        	}
        } else {
        	System.out.println("消息格式不正确");
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
