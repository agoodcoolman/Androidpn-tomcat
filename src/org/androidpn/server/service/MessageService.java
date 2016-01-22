package org.androidpn.server.service;

import java.util.List;
import org.androidpn.server.model.MessageIM;
/**
  * 
  * @ClassName: MessageService
  * @Description: 聊天消息的服务类
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-22 下午3:49:52
  *
 */
public interface MessageService {
	public void saveMessage(MessageIM messageIM);
	
	public void deteleMessage(MessageIM messageIM);
	
	public List<MessageIM> findMessageByUsername(String username);
	
	public void deteleMessageByMessageId(String receptUsername, String messageID);
}
