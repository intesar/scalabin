package com.vmw.bora.vchest.rest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.vmw.bora.vchest.domain.Blob;
import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.services.ObjBlobServiceImpl;
import com.vmw.bora.vchest.services.ObjServiceImpl;
 
@Component
@Path("/object")
public class ObjectRestService {
 
	final static String HOME = "home";
	final static String SUCCESS = "success";
	
	@Autowired
	ObjServiceImpl objServiceImpl;
	
	@Autowired
	ObjBlobServiceImpl objBlobServiceImpl;
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("file") InputStream is,
		@FormDataParam("file") FormDataContentDisposition fileDetail,
		@FormDataParam("parent") String parent) {
 
		String fileName = "test";
		if (fileDetail != null) {
			fileName = fileDetail.getFileName();
		}
		ByteBuffer bb = null;
		try {
			bb = ByteBuffer.wrap(IOUtils.toByteArray(is));
		} catch (IOException e) {
			e.printStackTrace();
			// return error
		}
		
		Obj obj = new Obj();
		obj.setId(UUID.randomUUID().toString());
		obj.setBucketName(fileName);
		if (StringUtils.isBlank(parent)) {
			obj.setParent(HOME);
		} else {
			obj.setParent(parent);
		}
		obj.setDateModified(new Date().toString());
		obj.setChunkCount("1");
		obj.setKind("file");
		
		objServiceImpl.save(obj);
		
		Blob blob = new Blob();
		blob.setId(obj.getId());
		blob.setBlob(bb);
		objBlobServiceImpl.save(blob);
 
		return Response.status(200).entity(obj.getId()).build();
 
	}

 
}
