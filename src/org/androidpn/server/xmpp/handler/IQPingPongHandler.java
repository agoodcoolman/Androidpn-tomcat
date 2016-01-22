package org.androidpn.server.xmpp.handler;

import org.androidpn.server.service.exception.UnauthorizedException;
import org.androidpn.server.xmpp.session.ClientSession;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;
/**
 * 
  * @ClassName: IQPingPongHandler
  * @Description: process android/client heart 
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2016-1-5 下午1:58:25
  *
 */
public class IQPingPongHandler extends IQHandler {
	
	private static final String NAMESPACE = "urn:xmpp:ping";
	private IQ probeResponse;
	
	
	public IQPingPongHandler() {
		 probeResponse = new IQ();
	}

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
        // normol
       if(IQ.Type.get == packet.getType()) {
    	   reply = IQ.createResultIQ(packet);
    	   
       } else {
    	   // error
    	   reply = IQ.createResultIQ(packet);
           reply.setChildElement(packet.getChildElement().createCopy());
           reply.setError(PacketError.Condition.not_acceptable);
           
       }
       // Send the response directly to the session
       if (reply != null) {
           session.process(reply);
       }

		return null;
	}

	/**
     * Returns the namespace of the handler.
     * 
     * @return the namespace
     */
	@Override
    public String getNamespace() {
        return NAMESPACE;
    }

}
