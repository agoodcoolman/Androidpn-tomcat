package org.androidpn.server.model;

import java.awt.TextArea;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.PacketExtension;
/**
 *  聊天的消息的存储表单
  * @ClassName: MessageTable
  * @Description: TODO
  * @author jin
  * @Company 深圳德奥技术有限公司
  * @date 2015-10-22 下午2:32:54
  *
 */
@Entity
@Table(name = "messageim")
public class MessageIM implements Serializable{

	private static final long serialVersionUID = 4733464888738351568L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "created_date", updatable = false, length=20)
	private Date createdDate = new Date(); // updatable 意思是是否可以更新
	
	@Column(name = "messageId", updatable = false, length=100)
	private String messageId;
	
	@Column(name = "message_from", nullable = false, length=100)
	private String message_from;//将JID toString之后存储
	
	@Column(name = "message_to", length=100)
	private String message_to;
	
	@Column(name = "body",length=300)
	private String body;
	
	@Column(name = "picture", length=200)
	private String picture; // 传输图片,暂时没用
	
	@Column(name = "resource", length=20)
	private String resource;
	
	@Column(name="message_type", length=30)
	private String message_type; // 消息类型
	
	@Column(name="message_xmlns", length=40)
	private String message_xmlns;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage_from() {
		return message_from;
	}

	public void setMessage_from(String message_from) {
		this.message_from = message_from;
	}

	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getMessage_xmlns() {
		return message_xmlns;
	}

	public void setMessage_xmlns(String message_xmlns) {
		this.message_xmlns = message_xmlns;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getMessage_to() {
		return message_to;
	}

	public void setMessage_to(String message_to) {
		this.message_to = message_to;
	}

	// 将数据库存的数据取出来,按照xmpp的格式导成聊天的消息
	public Packet toXML () {
		
		Message message = new Message();
		if (message_xmlns != null ) {
			Element notification = DocumentHelper.createElement(QName.get(
	                "notification", message_xmlns));
		}
		if (messageId != null) {
			message.setID(messageId);
		}
		if (message_from != null){
			message.setFrom(new JID(message_from));
		}
		if (message_to != null){
			message.setFrom(new JID(message_to));
		}
		if (body != null) {
			message.setBody(body);
		}
		if (picture!= null) {
			
			message.addChildElement("picture", "jabber:message:picture").addCDATA(picture);
		}
		if (message_type!= null ) {
			if (message_type.equals(Message.Type.chat)) {
				message.setType(Message.Type.chat);
			}
		}
 		return message;
	}
	
	
}
