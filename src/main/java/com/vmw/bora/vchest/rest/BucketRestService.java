package com.vmw.bora.vchest.rest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ObjServiceImpl objServiceImpl;

	@Autowired
	ObjBlobServiceImpl objBlobServiceImpl;

	@Autowired
	ActivityServiceImpl activityServiceImpl;

	@Autowired
	UsersServiceImpl usersServiceImpl;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response save(BucketDto bucket) {
		logger.info("save obj name [{}] parent [{}]", bucket.getName(),
				bucket.getParent());
		Obj obj = new Obj();
		obj.setId(UUID.randomUUID().toString());
		obj.setName(bucket.getName());
		if (StringUtils.isBlank(bucket.getParent())) {
			obj.setParent(HOME);
		} else {
			obj.setParent(bucket.getParent());
		}
		obj.setModified(new Date());
		obj.setChunkCount(0);
		obj.setKind("folder");
		obj.setOwner(UserContext.getLoggedInUser());
		obj.setTenantId(UserContext.getUserTenant());
		objServiceImpl.save(obj);

		// activity
		activityServiceImpl.addActivity("post", obj.getId(), 100, UserContext.getUserTenant());

		return Response.status(200).entity(obj.getId()).build();
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		logger.info("delete obj [{}]", id);
		// TODO recursively delete all.
		objServiceImpl.delete(id);

		// activity
		activityServiceImpl.addActivity("delete", id, 100, UserContext.getUserTenant());

		return Response.status(200).entity(SUCCESS).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public List<Obj> get(@PathParam("id") String id) {
		logger.info("get obj content for [{}]", id);

		// activity
		activityServiceImpl.addActivity("get", id, 100, UserContext.getUserTenant());

		return objServiceImpl.getObjs(id, UserContext.getLoggedInUser(), UserContext.getUserTenant());
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Obj> getFromHome() {
		logger.info("get obj [home] contents");
		return objServiceImpl.getObjs("home", UserContext.getLoggedInUser(), UserContext.getUserTenant());
	}

	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path(value = "/public/{id}")
	public Response makeBucketPublic(@PathParam("id") String id) {
		logger.info("make obj [{}] public", id);

		String username = UserContext.getLoggedInUser();
		String tenant = UserContext.getUserTenant();
		Obj obj = objServiceImpl.getByObjId(id, username, tenant);

		obj.setShared("public");
		objServiceImpl.save(obj);

		// activity
		activityServiceImpl.addActivity("post", id, 100, UserContext.getUserTenant());

		return Response.status(200).entity(id).build();
	}

}