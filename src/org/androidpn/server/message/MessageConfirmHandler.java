package org.androidpn.server.message;

import org.androidpn.server.service.ServiceLocator;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
/**
 * 
  * @ClassName: MessageConfirmHandler
  * @Description: 聊天推送成功回调,给用户推送消息,当用户收到消息的时候,用户就回去发送给一个收到消息的确认.这里是保证服务器推送消息的到达率.
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-22 下午6:53:16
  *
 */
public class MessageConfirmHandler extends MessageHandler {

	private final String NAMESPACE = "jabber:message:receptconfirm";
	
	
	@Override
	public Message handleIQ(Message packet) {
		if (packet != null) {
			JID from = packet.getFrom();
			String messageID = packet.getID();
			ServiceLocator.getMessageService().deteleMessageByMessageId(from.toString(), messageID);
		}
		
		return packet;
	}

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

}
