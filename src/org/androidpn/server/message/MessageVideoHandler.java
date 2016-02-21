package org.androidpn.server.message;

import org.androidpn.server.xmpp.session.ClientSession;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.PacketError;
import org.xmpp.packet.PacketExtension;
/**
 * 
  * @ClassName: MessageVideoHandler
  * @Description: 给对方发送一个视频请求,这里进行视频请求,然后给对方发送一个视频播放的地址
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2016-2-20 上午9:48:43
  *
 */
public class MessageVideoHandler extends MessageHandler {
	
	
	
	public static final String NAME_SPACE = "jabber:message:video";
	@Override
	public Message handleIQ(Message packet) {
		// 表示你要请求视频的那个用户是不在线的
		ClientSession session = sessionManager.getSession(packet.getTo().getNode());
		if (session == null) {
            log.error("the other user is not online " + packet.getTo());
            Message createCopy = packet.createCopy();
            
            createCopy.addChildElement("uploadUri", NAME_SPACE);
            createCopy.setError(PacketError.Condition.unexpected_request);
            return createCopy;
        }
		
		// 另一个用户如果是在线的话
		Element uploadUri = packet.getChildElement("uploadUri", NAME_SPACE);

		Message createCopy = packet.createCopy();

		String playUri = uploadUri.attribute("uri").getText();
		createCopy.deleteExtension("uploadUri", NAME_SPACE);
		/*JID from = packet.getFrom();
		JID to = packet.getTo();
		
		createCopy.setTo(from);
		createCopy.setFrom(to);*/
		
		createCopy.addChildElement("playUri", NAME_SPACE);
		createCopy.getChildElement("playUri", NAME_SPACE).addAttribute("uri", playUri);
		return createCopy;
	}

	@Override
	public String getNamespace() {
		
		return NAME_SPACE;
	}

}
