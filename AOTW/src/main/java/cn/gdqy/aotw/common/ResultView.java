package cn.gdqy.aotw.common;

import java.util.HashMap;
import java.util.Map;

public class ResultView {
	public final static String SUCCESS = "Y";
	public final static String ERROR = "N";
	private String isOk;
	private String msg;
	private Map<String, Object> data;

	public ResultView() {
		this.isOk = SUCCESS;
		this.data = new HashMap<String, Object>();
	}
	
	public void putData(String key, Object value) {
		this.data.put(key, value);
	}
	
	public Object getData(String key) {
		return this.data.get(key);
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

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
