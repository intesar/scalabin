package com.vmw.bora.vchest.domain;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Activity {

	@PrimaryKey
	@Field
	private String id;

	@Field("activity_username")
	private String username;

	@Field("activity_actionDate")
	private Date actionDate;

	@Field("activity_actionType")
	private String actionType;

	@Field("activity_objId")
	private String objId;

	@Field("activity_objName")
	private String objName;

	@Field("activity_size")
	private long size;

	@Field("activity_groupId")
	private String groupId;

	@Field("activity_tenantId")
	private String tenantId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
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

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

}
