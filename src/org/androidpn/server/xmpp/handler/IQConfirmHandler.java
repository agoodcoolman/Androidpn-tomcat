package org.androidpn.server.xmpp.handler;

import org.androidpn.server.service.NotificationService;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.exception.UnauthorizedException;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
/**
 * 
  * @ClassName: IQConfirmHandler
  * @Description: process client response message
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-20 上午9:50:51
  *
 */
public class IQConfirmHandler extends IQHandler {
	private static final String NAMESPACE = "androidpn:iq:notificationconfirm";
	
	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		
		IQ reply = null;

        ClientSession session = sessionManager.getSession(packet.getFrom());
        if (session == null) {
            log.error("Session not found for key " + packet.getFrom());
            reply = IQ.createResultIQ(packet);
            reply.setChildElement(packet.getChildElement().createCopy());
            reply.setError(PacketError.Condition.internal_server_error);
            return reply;
        }
		if (session.getStatus() == Session.STATUS_AUTHENTICATED) {
			if (IQ.Type.get.equals(packet.getType())) {
				String uuid = packet.getChildElement().elementText("uuid");
				NotificationService notificationService = ServiceLocator.getNotificationService();
				notificationService.detelNotificationByUUID(uuid);
			}
			
		}
		return null;
	}

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

}
