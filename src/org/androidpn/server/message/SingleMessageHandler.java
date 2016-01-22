package org.androidpn.server.message;

import org.androidpn.server.model.MessageIM;
import org.androidpn.server.model.User;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.exception.UserNotFoundException;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.PacketError;
import org.xmpp.packet.PacketError.Condition;

public class SingleMessageHandler extends MessageHandler {

	private SessionManager sessionManager;
	
	public SingleMessageHandler () {
		sessionManager = SessionManager.getInstance();
	}
	
	@Override
	public Message handleIQ(Message packet) {
		// 这个方法是处理消息的,如果这个消息有发送用户不存在,不是好友等等问题,在这里处理
		
		try {
			
			JID domain = packet.getTo();
    		String node = domain.getNode();
    		
    		// 这里是没有找到当前的被发送的用户.那么报异常,有的话,直接找到
    		User userByUsername = ServiceLocator.getUserService().getUserByUsername(node);
    		// 发送消息
    		ClientSession session = sessionManager.getSession(node);
    		if (session != null &&  session.getPresence().isAvailable()) {
    			session.deliver(packet);
    		}
		
			// <message xmlns="jabber:message:send" id="6Bt7Q-22" to="minmin@deao.com/android" type="chat" from="police001@127.0.0.1/AndroidpnClient"><body>朕的发送消息就指着你啦!</body></message>
			MessageIM messageIM = new MessageIM();
			messageIM.setMessage_xmlns(packet.getElement().getNamespaceURI());
			messageIM.setMessageId(packet.getID());
			messageIM.setMessage_type(packet.getType().toString());
			messageIM.setBody(packet.getBody());
			messageIM.setMessage_from(packet.getFrom().toString());
			
			ServiceLocator.getMessageService().saveMessage(messageIM);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			packet.setError(new PacketError(Condition.recipient_unavailable));
		}
		
		return packet;
	}

	@Override
	public String getNamespace() {
		return "jabber:message:send";
	}

}
