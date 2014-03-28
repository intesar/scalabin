package com.vmw.bora.vchest.rest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Activity;
import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.dto.BucketDto;
import com.vmw.bora.vchest.services.ActivityServiceImpl;
import com.vmw.bora.vchest.services.ObjBlobServiceImpl;
import com.vmw.bora.vchest.services.ObjServiceImpl;
import com.vmw.bora.vchest.services.UsersServiceImpl;

@Component
@Path("/bucket")
public class BucketRestService {
	
	final static String HOME = "home";
	final static String SUCCESS = "success";

	@Autowired
	ObjServiceImpl objServiceImpl;
	
	@Autowired
	ObjBlobServiceImpl objBlobServiceImpl;
	
	@Autowired
	ActivityServiceImpl activityServiceImpl;
	
	@Autowired
	UsersServiceImpl usersServiceImpl;
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response save(BucketDto bucket) {
		System.out.println(bucket.getName() + " " + bucket.getParent());
		Obj obj = new Obj();
		obj.setId(UUID.randomUUID().toString());
		obj.setBucketName(bucket.getName());
		if (StringUtils.isBlank(bucket.getParent())) {
			obj.setParent(HOME);
		} else {
			obj.setParent(bucket.getParent());
		}
		obj.setDateModified(new Date().toString());
		obj.setChunkCount("0");
		obj.setKind("folder");
		obj.setOwner(UserContext.getLoggedInUser());
		obj.setTenant(usersServiceImpl.getTenant(obj.getOwner()));
		objServiceImpl.save(obj);
		
		// activity
		activityServiceImpl.addActivity("post", obj.getId(), "0K", usersServiceImpl.getTenant(UserContext.getLoggedInUser()));
		
		return Response.status(200).entity(obj.getId()).build();
	}

	@DELETE
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		System.out.println("Bucket is: " + id);
		// TODO recursively delete all.
		objServiceImpl.delete(id);
		return Response.status(200).entity(SUCCESS).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public List<Obj> get(@PathParam("id") String id) {
		System.out.println("Bucket is: " + id);
		return objServiceImpl.getObjs(id, UserContext.getLoggedInUser());
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Obj> getFromHome() {
		System.out.println("Bucket is: home");
		return objServiceImpl.getObjs("home", UserContext.getLoggedInUser());
	}
	
	
}