package org.androidpn.server.service;

import java.util.List;

import org.androidpn.server.model.Notification;
import org.androidpn.server.service.exception.NotificationNotFoundException;

/**
 * 
  * @ClassName: NotificationService
  * @Description: 
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-19 下午3:18:42
  *
 */
public interface NotificationService {
	public Notification getNotifiaction(Long id);
	
	public List<Notification> getNotifiactionByUserName(String userName) throws NotificationNotFoundException;
	
	public boolean saveNotifiaction(Notification notification);
	
	public void detelNotification(Notification notification);
	
	public void saveNotification(String apiKey, String title,String message, String uri, String uuid, String username);
	
	public void detelNotificationByUUID(String uuid);
}
