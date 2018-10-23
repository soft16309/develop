package cn.gdqy.aotw.utils;

import java.util.HashMap;
import java.util.Map;

public class ResultView {
	private String isOk;
	private String msg;
	private Map<String, Object> data;
	
	public ResultView() {
		this.isOk = "Y";
		this.data = new HashMap<String, Object>();
	}
	
	public void putData(String key, Object value) {
		this.data.put(key, value);
	}
	
	public String getIsOk() {
		return isOk;
	}

	public void setIsOk(String isOk) {
		this.isOk = isOk;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
