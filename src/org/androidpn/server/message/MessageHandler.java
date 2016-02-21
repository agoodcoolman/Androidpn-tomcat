package org.androidpn.server.message;

import org.androidpn.server.service.exception.UnauthorizedException;
import org.androidpn.server.xmpp.router.PacketDeliverer;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
/**
 * 	分发消息 /route the message
  * @ClassName: MessageHandler
  * @Description: TODO
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-21 下午5:09:43
  *
 */
public abstract class MessageHandler {
	protected final Log log = LogFactory.getLog(getClass());

    protected SessionManager sessionManager;

	private MessageManager messageManager;

    /**
     * Constructor.
     */
    public MessageHandler() {
        sessionManager = SessionManager.getInstance();
        messageManager = MessageManager.getInstance();
    }
    
    
    /**
     * Processes the received Message packet.
     *  处理收到的信息
     * @param packet the packet
     */
    public void process(Packet packet) {
    	if (packet != null && packet instanceof Message) {
    		Message message = (Message)packet;
    		Message resultMessage = handleIQ(message);
    		
    		if (resultMessage.getError() != null) {
    			// 如果出现错误,将错误信息返回给发送者
    			messageManager.sendMessageIM(message.getFrom().getNode(), message);
    		} else {
    			PacketDeliverer.deliver(resultMessage);
    		}
    	}
    }
    
    /**
     * Handles the received Message packet.
     * 
     * @param packet the packet
     * @return the response to send back
     */
    public abstract Message handleIQ(Message packet);
    
    /**
     * Returns the namespace of the handler.
     * 
     * @return the namespace
     */
    public abstract String getNamespace();
    
}
