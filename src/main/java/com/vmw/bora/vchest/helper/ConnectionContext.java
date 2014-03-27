package com.vmw.bora.vchest.helper;

public class ConnectionContext {

	private String bucketName;
	
	private String locationUri;
	
	private String chunkCount;

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getLocationUri() {
		return locationUri;
	}

	public void setLocationUri(String locationUri) {
		this.locationUri = locationUri;
	}

	public String getChunkCount() {
		return chunkCount;
	}

	public void setChunkCount(String chunkCount) {
		this.chunkCount = chunkCount;
	}
}
