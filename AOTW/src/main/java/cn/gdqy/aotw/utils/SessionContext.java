package cn.gdqy.aotw.utils;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * 设置及获取session对象的帮助类
 * @author zrz
 */
public class SessionContext {  
    private static SessionContext instance;  
    private HashMap<String,HttpSession> sessionMap;  
  
    private SessionContext() {  
        sessionMap = new HashMap<String,HttpSession>();  
    }  
  
    public static SessionContext getInstance() {  
        if (instance == null) {  
            instance = new SessionContext();  
        }  
        return instance;  
    }  
  
    public synchronized void addSession(HttpSession session) {  
        if (session != null) {  
            sessionMap.put(session.getId(), session);  
        }  
    }  
  
    public synchronized void delSession(HttpSession session) {  
        if (session != null) {  
            sessionMap.remove(session.getId());  
        }  
    }  
  
    public synchronized HttpSession getSession(String sessionID) {  
        if (sessionID == null) {  
            return null;  
        }  
        return sessionMap.get(sessionID);  
    }  
  
}
