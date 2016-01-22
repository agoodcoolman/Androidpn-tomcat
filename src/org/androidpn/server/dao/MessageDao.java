package org.androidpn.server.dao;

import java.util.List;

import org.androidpn.server.model.MessageIM;

/**
 * 
  * @ClassName: MessageDao
  * @Description: 消息机制功能接口
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-22 下午3:27:26
  *
 */
public interface MessageDao {

	public void saveMessage(MessageIM message);
	
	public void deteleMessage(MessageIM message);
	
	public List<MessageIM> findMessageByUsername(String username);
	
	public void deteleMessageByMessageId(String receptUsername, String messageID);
	
}
