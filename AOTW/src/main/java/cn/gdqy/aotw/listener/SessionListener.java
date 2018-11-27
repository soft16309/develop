package cn.gdqy.aotw.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.gdqy.aotw.utils.SessionContext;

/**
 * session监听，用于保存和移除session
 * @author zrz
 */
public class SessionListener implements HttpSessionListener {  
    
    private SessionContext sessionContext = SessionContext.getInstance();  
      
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {  
        HttpSession session = httpSessionEvent.getSession();  
        sessionContext.addSession(session);
    }  
  
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {  
        HttpSession session = httpSessionEvent.getSession();  
        sessionContext.delSession(session);
    }  
  
}  
