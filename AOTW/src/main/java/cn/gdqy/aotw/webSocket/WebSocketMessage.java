package cn.gdqy.aotw.webSocket;

import cn.gdqy.aotw.pojo.Group;
import cn.gdqy.aotw.pojo.User;

public class WebSocketMessage {	
	private User from;
	private String to_user;		//发送到哪个用户
	private String msg;			//文本消息
	private String file;		//采用base64编码
	private String fileType;	//文件类型
	private Group to_group;		//发送到哪个群（包括的群资料）
	
	//扩展属性
	private String date;		//接收的日期	yyyy-MM-dd HH:mm:ss
	private boolean isShowDate; //是否显示日期（用于聊天界面展示时的发送消息时间的提示）
	
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public String getTo_user() {
		return to_user;
	}
	public void setTo_user(String to_user) {
		this.to_user = to_user;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Group getTo_group() {
		return to_group;
	}
	public void setTo_group(Group to_group) {
		this.to_group = to_group;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean getIsShowDate() {
		return isShowDate;
	}
	public void setIsShowDate(boolean isShowDate) {
		this.isShowDate = isShowDate;
	}
	
}
