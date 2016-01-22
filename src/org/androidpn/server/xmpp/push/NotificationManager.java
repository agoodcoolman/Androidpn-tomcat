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
package org.androidpn.server.xmpp.push;

import java.util.List;
import java.util.Random;

import org.androidpn.server.model.Notification;
import org.androidpn.server.model.User;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserService;
import org.androidpn.server.service.impl.NotificationServiceImpl;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;

/** 
 * This class is to manage sending the notifcations to the users.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationManager {

    private static final String NOTIFICATION_NAMESPACE = "androidpn:iq:notification";

    private final Log log = LogFactory.getLog(getClass());

    private SessionManager sessionManager;

    /**
     * Constructor.
     */
    public NotificationManager() {
        sessionManager = SessionManager.getInstance();
    }

    /**
     * Broadcasts a newly created notification message to all connected users.
     * 
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public void sendBroadcast(String apiKey, String title, String message,
            String uri) {
        log.debug("sendBroadcast()...");
        
        UserService userService = ServiceLocator.getUserService();
        List<User> users = userService.getUsers();
        for (User user : users) {
        	// 推送广播都存到数据库,推送失败,下次直接在用户单推中推送
        	ClientSession session = sessionManager.getSession(user.getUsername());
        	IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri, user.getUsername());
        	if (session != null && session.getPresence().isAvailable()) {
        		notificationIQ.setTo(session.getAddress());
        		session.deliver(notificationIQ);
        	}
		}
    }

    /**
     * Sends a newly created notification message to the specific user.
     * 
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public void sendNotifcationToUser(String apiKey, String username,
            String title, String message, String uri) {
        log.debug("sendNotifcationToUser()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri, username);
        ClientSession session = sessionManager.getSession(username);
        if (session != null) {
            if (session.getPresence().isAvailable()) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
            }
        }
    }

    /**
     * Sends a older notification message to the specific user.
     * 这里是从数据库中直接进取出的值推送,包含uuid,用户登录上来查库的推送
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public void sendNotifcationToUser(String apiKey, String username,
            String title, String message, String uri, String uuid) {
        log.debug("sendNotifcationToUser()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri, username, uuid);
        ClientSession session = sessionManager.getSession(username);
        if (session != null) {
            if (session.getPresence().isAvailable()) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
            }
        }
    }
    
    /**
     * Creates a new notification IQ and returns it.
     */
    private IQ createNotificationIQ(String apiKey, String title,
            String message, String uri, String username) {
        return createNotificationIQ(apiKey, title, message, uri, username, "");
    }
    
    /**
     * Creates older notification IQ and returns it.
     * 这里是创建IQ并且返回,可以根据之前的id
     */
    private IQ createNotificationIQ(String apiKey, String title,
            String message, String uri, String username, String uuid) {
    	boolean save = false;
    	if (uuid != null && "".equals(uuid)) {
    		// 需要保存的时候才生成uuid,UUID为空是新建,不为空是读取的
    		save = true;
    		Random random = new Random();
            uuid = Integer.toHexString(random.nextInt());
    	}
    	
        Element notification = DocumentHelper.createElement(QName.get(
                "notification", NOTIFICATION_NAMESPACE));
        notification.addElement("id").setText(uuid);
        notification.addElement("apiKey").setText(apiKey);
        notification.addElement("title").setText(title);
        notification.addElement("message").setText(message);
        notification.addElement("uri").setText(uri);
        
        Notification notifiacation = new Notification();
        if (save) {
        	// save to batabase
        	NotificationServiceImpl notificationService =  (NotificationServiceImpl) ServiceLocator.getNotificationService();
        	notificationService.saveNotification(apiKey, title, message, uri, uuid, username);
        }
        IQ iq = new IQ();
        iq.setType(IQ.Type.set);
        iq.setChildElement(notification);

        return iq;
    }
}