package cn.gdqy.aotw.common;

public class GlobalConstant {
	//离线消息仓库保存的目录
	public final static String MESSAGE_STORAGE_PATH = "E:\\AOTW\\offlineMessage";
	
	//保存用户名的cookie的名字
	public final static String SESSION_LOGIN_USERNAME = "SESSION_LOGIN_USERNAME";
	
	//保存用户密码的cookie的名字
	public final static String SESSION_LOGIN_PASSWORD = "SESSION_LOGIN_PASSWORD";
	
	//websocket 心跳维持帧
	public final static String WEBSOCKET_HEARTBEAT = "1";
	
	public final static class UserStatus {
		public final static Byte ENABLE = 1;
		public final static Byte DISABLE = 0;
	}
	
	public final static class GroupStatus {
		public final static Byte ENABLE = 1;
		public final static Byte DISABLE = 0;
	}
	
	public final static class UserRole {
		public final static Byte ADMIN = 1;
		public final static Byte ORDINARY_USER = 0;	//普通用户
	}
}
