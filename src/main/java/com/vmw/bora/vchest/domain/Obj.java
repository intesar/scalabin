package com.vmw.bora.vchest.domain;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Obj {
	
	@PrimaryKey
	@Field
	private String id;

	@Field
	private String name;

	@Field
	private String kind;

	@Field
	private String location;

	@Field
	private long size;

	@Field
	private String parent;

	@Field
	private Date modified;

	@Field
	private long chunkCount;

	@Field
	private String owner;

	@Field
	private String tenantId;

	@Field
	private String groupId;

	@Field
	private String shared = "private";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public long getChunkCount() {
		return chunkCount;
	}

	public void setChunkCount(long chunkCount) {
		this.chunkCount = chunkCount;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getShared() {
		return shared;
	}

	public void setShared(String shared) {
		this.shared = shared;
	}

	

}
