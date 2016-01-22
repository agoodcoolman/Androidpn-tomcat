package org.androidpn.server.dao.hibernate;

import java.util.List;

import javax.management.ObjectName;
import javax.security.auth.Subject;
import org.androidpn.server.dao.NotificationDao;
import org.androidpn.server.model.Notification;
import org.androidpn.server.service.exception.NotificationNotFoundException;
import org.hibernate.property.Getter;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.sun.jmx.remote.security.NotificationAccessController;
/**
 * 
  * @ClassName: NotificationDaoHibernate
  * @Description: this class implemention hibernateDao using Spring hibernateTmplate
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-19 下午2:47:56
  *
 */
public class NotificationDaoHibernate extends HibernateDaoSupport implements
		NotificationDao {

	public Notification getNotifiaction(Long id) {
		return (Notification)getHibernateTemplate().get(Notification.class, id);
	}

	public List<Notification> getNotifiactionByUserName(String userName) throws NotificationNotFoundException, DataAccessException {
		List notifications = getHibernateTemplate().find("from Notification where userName=?", userName);
		if (notifications == null ) {
			throw new NotificationNotFoundException("User'" + userName + "' not found");
		} else {
			return notifications;
		}
	}

	public boolean saveNotifiaction(Notification notification) {
		getHibernateTemplate().saveOrUpdate(notification);
		getHibernateTemplate().flush();
		return true;
	}

	public void detelNotification(Notification notification) {
		getHibernateTemplate().delete(notification);
	}

	public void saveNotification(String apiKey, String title, String message,
			String uri, String uuid, String username) {
		Notification notifiacation = new Notification();
		notifiacation.setApiKey(apiKey);
		notifiacation.setTitle(title);
		notifiacation.setMessage(message);
		notifiacation.setUri(uri);
		notifiacation.setUuid(uuid);
		notifiacation.setUsername(username);
		getHibernateTemplate().save(notifiacation);
	}

	public void detelNotificationByUUID(String uuid) {
		
		List find = getHibernateTemplate().find("from Notification n where n.uuid = ?", new String []{uuid});
		if (find != null && find.size() > 0 && find.get(0) != null) {
			getHibernateTemplate().delete(find.get(0));
		}
		
	}
}
