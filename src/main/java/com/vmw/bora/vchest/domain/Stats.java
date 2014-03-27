package com.vmw.bora.vchest.domain;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Stats {

	@PrimaryKey
	@Field
	protected String id;
		
	@Field
	private String user;

	@Field
	private int year;

	@Field
	private int month;

	@Field	
	private long storage;

	@Field
	private long uploadedBytes;

	@Field
	private long downloadedBytes;
	
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public long getStorage() {
		return storage;
	}

	public void setStorage(long storage) {
		this.storage = storage;
	}

	public long getUploadedBytes() {
		return uploadedBytes;
	}

	public void setUploadedBytes(long uploadedBytes) {
		this.uploadedBytes = uploadedBytes;
	}

	public long getDownloadedBytes() {
		return downloadedBytes;
	}

	public void setDownloadedBytes(long downloadedBytes) {
		this.downloadedBytes = downloadedBytes;
	}

}
