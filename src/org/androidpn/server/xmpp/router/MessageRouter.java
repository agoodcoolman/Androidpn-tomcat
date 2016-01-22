/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.xmpp.router;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.androidpn.server.message.MessageConfirmHandler;
import org.androidpn.server.message.MessageHandler;
import org.androidpn.server.message.SingleMessageHandler;
import org.androidpn.server.xmpp.handler.IQHandler;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.PacketError;
import org.xmpp.packet.Presence;

/** 
 * This class is to route Message packets to their corresponding handler.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class MessageRouter {
	
	private final Log log = LogFactory.getLog(getClass());
	
	private SessionManager sessionManager;
	
	private List<MessageHandler> messageHandlers = new ArrayList<MessageHandler>();

	private Map<String, MessageHandler> namespace2Handlers = new ConcurrentHashMap<String, MessageHandler>();

    /**
     * Constucts a packet router.
     */
    public MessageRouter() {
    	 sessionManager = SessionManager.getInstance();
    	 messageHandlers.add(new SingleMessageHandler());
    	 messageHandlers.add(new MessageConfirmHandler());
    }

    /**
     * Routes the Message packet.
     * 
     * @param packet the packet to route
     */
    public void route(Message packet) {
    	if (packet == null) {
            throw new NullPointerException();
        }
    	ClientSession session = sessionManager.getSession(packet.getFrom());
    	if (session != null && session.getStatus() == Session.STATUS_AUTHENTICATED /*|| 
    			("jabber:message:message".equals(packet.getElement().getNamespace().toString()))*/   ) {
    		handle(packet);
    	} else {
    		// FIXME 这里都是没有发送出去的,那个 session.process 就是处理失败,然后加个失败的回复,返回给请求的设备
    		packet.setTo(session.getAddress());
            packet.setFrom((JID) null);
            packet.setError(PacketError.Condition.not_authorized);
            session.process(packet);
    	}
    }
    
    /**
     * handle send message
     * @param packet
     */
    private void handle(Message packet) { 
    	
    	Element element = packet.getElement();
    	String namespace = null;
    	if (element != null) {
    		namespace = element.getNamespaceURI();
    	} 
    	if (namespace == null) {
    		Message.Type type = packet.getType();
    		if (type == null || Message.Type.error == type || packet.getTo() != null) {
    			log.warn("Unknown Message type");
    		}
    	} else {
    		MessageHandler handler = getHandler(namespace);
    		if (handler == null) {
                sendErrorPacket(packet,
                        PacketError.Condition.service_unavailable);
            } else {
                handler.process(packet);
            }
    	}
    	
    }
    /**
     * Senda the error packet to the original sender
     */
    private void sendErrorPacket(Message originalPacket,
            PacketError.Condition condition) {
        if (Message.Type.error == originalPacket.getType()) {
            log.error("Cannot reply an IQ error to another IQ error: "
                    + originalPacket);
            return;
        }
        /*originalPacket.
        IQ reply = IQ.createResultIQ(originalPacket);
        reply.setChildElement(originalPacket.getChildElement().createCopy());
        reply.setError(condition);
        try {
            PacketDeliverer.deliver(reply);
        } catch (Exception e) {
            // Ignore
        }*/
    }
    /**
     * Returns an IQHandler with the given namespace.
     */
    private MessageHandler getHandler(String namespace) {
    	MessageHandler handler = namespace2Handlers.get(namespace);
        if (handler == null) {
            for (MessageHandler handlerCandidate : messageHandlers) {
                if (namespace.equalsIgnoreCase(handlerCandidate.getNamespace())) {
                    handler = handlerCandidate;
                    namespace2Handlers.put(namespace, handler);
                    break;
                }
            }
        }
        return handler;
    }
}
