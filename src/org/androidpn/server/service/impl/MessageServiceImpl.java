package org.androidpn.server.service.impl;

import java.util.List;

import org.androidpn.server.dao.MessageDao;
import org.androidpn.server.dao.NotificationDao;
import org.androidpn.server.model.MessageIM;
import org.androidpn.server.service.MessageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageServiceImpl implements MessageService {

	protected final Log log = LogFactory.getLog(getClass());
	// notificationDao 通过AOP的注入方式在spring-xml中注入
	private MessageDao messageDao;
	
	public MessageServiceImpl() {
		
	}
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void saveMessage(MessageIM messageIM) {
		messageDao.saveMessage(messageIM);
	}

	public void deteleMessage(MessageIM messageIM) {
		messageDao.deteleMessage(messageIM);
	}

	public List<MessageIM> findMessageByUsername(String username) {
		return messageDao.findMessageByUsername(username);
	}

	public void deteleMessageByMessageId(String receptUsername, String messageID) {
		messageDao.deteleMessageByMessageId(receptUsername, messageID);
	}

}
