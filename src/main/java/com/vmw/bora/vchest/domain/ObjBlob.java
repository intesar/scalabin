package com.vmw.bora.vchest.domain;

import java.nio.ByteBuffer;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class ObjBlob {

	@PrimaryKey
	@Field
	private String id;
	
	@Field
	private String objId;
	
	@Field
	private ByteBuffer blob;

	public ByteBuffer getBlob() {
		return blob;
	}

	public void setBlob(ByteBuffer blob) {
		this.blob = blob;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}
	
}
