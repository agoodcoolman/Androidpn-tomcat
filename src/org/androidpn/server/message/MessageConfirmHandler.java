package org.androidpn.server.message;

import org.androidpn.server.service.ServiceLocator;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
/**
 * 
  * @ClassName: MessageConfirmHandler
  * @Description: 聊天推送成功回调
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
