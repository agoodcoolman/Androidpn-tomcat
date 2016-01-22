package org.androidpn.server.service.impl;

import java.util.List;

import org.androidpn.server.dao.NotificationDao;
import org.androidpn.server.model.Notification;
import org.androidpn.server.service.NotificationService;
import org.androidpn.server.service.exception.NotificationNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
  * @ClassName: NotificationServiceImpl
  * @Description: this service for Notification
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-19 下午3:31:31
  *
 */
public class NotificationServiceImpl implements NotificationService{

	protected final Log log = LogFactory.getLog(getClass());
	// notificationDao 通过AOP的注入方式在spring-xml中注入
	private NotificationDao notificationDao;
	public NotificationServiceImpl() {
		
	}
	
	public NotificationDao getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}



	public Notification getNotifiaction(Long id) {
		
		return notificationDao.getNotifiaction(id);
	}

	public List<Notification> getNotifiactionByUserName(String userName)
			throws NotificationNotFoundException {
		
		return notificationDao.getNotifiactionByUserName(userName);
	}

	public boolean saveNotifiaction(Notification notification) {
		notificationDao.saveNotifiaction(notification);
		return true;
	}

	public void detelNotification(Notification notification) {
		notificationDao.detelNotification(notification);
	}

	public void saveNotification(String apiKey, String title, String message,
			String uri, String uuid, String username) {
		notificationDao.saveNotification(apiKey, title, message, uri, uuid, username);
	}

	public void detelNotificationByUUID(String uuid) {
		notificationDao.detelNotificationByUUID(uuid);
	}

}
