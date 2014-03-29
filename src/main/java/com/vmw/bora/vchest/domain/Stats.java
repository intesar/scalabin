package com.vmw.bora.vchest.domain;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Stats {
	
	@PrimaryKey
	@Field
	private String id;

	@Field
	private String user;

	@Field
	private String year;

	@Field
	private String month;

	@Field	
	private String storage;

	@Field
	private String uploadedBytes;

	@Field
	private String downloadedBytes;
	
	@Field
	private String tenant;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getUploadedBytes() {
		return uploadedBytes;
	}

	public void setUploadedBytes(String uploadedBytes) {
		this.uploadedBytes = uploadedBytes;
	}

	public String getDownloadedBytes() {
		return downloadedBytes;
	}

	public void setDownloadedBytes(String downloadedBytes) {
		this.downloadedBytes = downloadedBytes;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

}
