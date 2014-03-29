package com.vmw.bora.vchest.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
@XmlRootElement
public class Users {

	@PrimaryKey
	@Field
	@XmlElement
	private String id;

	@XmlElement
	@Field("users_username")
	private String username;

	@XmlElement
	@Field("users_password")
	private String password;

	@Field("users_enabled")
	@XmlElement
	private boolean enabled;

	@Field("users_groupId")
	@XmlElement
	private String groupId;

	@Field("users_tenantId")
	@XmlElement
	private String tenantId;

	public String getUsername() {
		return username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
