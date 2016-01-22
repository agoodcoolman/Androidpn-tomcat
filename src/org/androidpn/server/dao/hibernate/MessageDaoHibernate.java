package org.androidpn.server.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.androidpn.server.dao.MessageDao;
import org.androidpn.server.model.MessageIM;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
 * 
  * @ClassName: MessageDaoHibernate
  * @Description: 消息数据库的操作类
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-22 下午3:38:35
  *
 */
public class MessageDaoHibernate extends HibernateDaoSupport implements
		MessageDao {

	public void saveMessage(MessageIM message) {
		getHibernateTemplate().save(message);
	}

	public void deteleMessage(MessageIM message) {
		getHibernateTemplate().delete(message);
	}

	public List<MessageIM> findMessageByUsername(String username) {
		List messages = getHibernateTemplate().find("from MessageIM where message_to = ?", username);
		if (messages != null && messages.size() > 0 ) {
			return messages;
		} else { // 找不到消息算了啊,毕竟可能真的没有消息给他
			return new ArrayList<MessageIM>();
		}
	}

	public void deteleMessageByMessageId(String receptUsername, String messageID) {
		
	}

}
