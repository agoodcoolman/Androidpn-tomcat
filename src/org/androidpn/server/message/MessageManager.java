package org.androidpn.server.message;

import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.xmpp.packet.Packet;

/**
 *  聊天消息的管理类
  * @ClassName: MessageManager
  * @Description: 主要的功能是下发推送消息
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-23 下午3:30:54
  *
 */
public class MessageManager {

	protected SessionManager sessionManager;
	private static MessageManager instance;
	
	private  MessageManager() {
		sessionManager = SessionManager.getInstance();
	}
	 
	public void sendMessageIM(String username, Packet packet) {
		ClientSession session = sessionManager.getSession(username);
		if (session != null && session.getPresence().isAvailable()) {
			session.deliver(packet);
		}
		
	}
	
	
	/**
     * Returns the singleton instance of MessageManager.
     * 
     * @return the instance
     */
    public static MessageManager getInstance() {
        if (instance == null) {
            synchronized (MessageManager.class) {
            	if (instance == null) {
            		instance = new MessageManager();
            	}
            }
        }
        return instance;
    }
}
