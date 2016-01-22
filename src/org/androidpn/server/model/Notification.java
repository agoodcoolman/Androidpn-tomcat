package org.androidpn.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 消息存储在数据库
 * notification table,save send notification 
 */

@Entity
@Table(name = "notification")
public class Notification implements Serializable{
	private static final long serialVersionUID = 4733464888738354589L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="apiKey", nullable=false, length=30)
	private String apiKey;
	
	@Column(name="title", length=200)
	private String title;
	
	@Column(name="message", length=200)
	private String message;
	
	@Column(name="uri", length=200, nullable=true)
	private String uri;
	
	@Column(name="uuid", length=200)
	private String uuid;
	
	@Column(name = "created_date", updatable = false)
	private Date createdDate = new Date(); // updatable 意思是是否可以更新

	@Column(name="userName", nullable=false, length=200)
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
	 
}
