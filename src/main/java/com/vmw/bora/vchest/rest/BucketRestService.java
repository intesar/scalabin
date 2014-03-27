package com.vmw.bora.vchest.rest;

import java.util.Date;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.BucketDto;
import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.services.ObjBlobServiceImpl;
import com.vmw.bora.vchest.services.ObjServiceImpl;

@Component
@Path("/bucket")
public class BucketRestService {

	@Autowired
	ObjServiceImpl objServiceImpl;
	
	@Autowired
	ObjBlobServiceImpl objBlobServiceImpl;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String save(BucketDto bucket) {
		System.out.println(bucket.getName() + " " + bucket.getParent());
		Obj bucketObj = new Obj();
		bucketObj.setId(UUID.randomUUID().toString());
		bucketObj.setBucketName(bucket.getName());
		bucketObj.setParent(bucket.getParent());
		bucketObj.setDateModified(new Date().toString());
		objServiceImpl.save(bucketObj);
		return "done";
	}

	@DELETE
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public String delete(@PathParam("id") String id) {
		System.out.println("Bucket is: " + id);
		objServiceImpl.delete(id);
		return "done";
	}
}