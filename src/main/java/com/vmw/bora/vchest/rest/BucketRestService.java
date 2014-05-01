package com.vmw.bora.vchest.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.JSONP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.dto.BucketDto;
import com.vmw.bora.vchest.services.ActivityService;
import com.vmw.bora.vchest.services.ObjService;

@Component
@Path("/bucket")
@Produces({"application/json;qs=1", "application/xml;qs=0.5"})
public class BucketRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ObjService objService;

	@Autowired
	private ActivityService activityService;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response save(BucketDto bucket) {
		logger.info(
				"add bucket [{}] parent [{}] user [{}] tenant [{}]",
				new Object[] { bucket.getName(), bucket.getParent(),
						UserContext.getLoggedInUser(),
						UserContext.getUserTenant() });

		Obj obj = objService
				.addFolder(bucket.getName(), bucket.getParent());

		// activity
		activityService.addActivity("post", obj.getId(), 100,
				UserContext.getUserTenant());

		return Response.status(200).entity(obj.getId()).build();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/rename")
	public Response rename(BucketDto bucket) {
		logger.info(
				"add bucket [{}] parent [{}] user [{}] tenant [{}]",
				new Object[] { bucket.getName(), bucket.getParent(),
						UserContext.getLoggedInUser(),
						UserContext.getUserTenant() });

		Obj obj = objService
				.renameObj(bucket.getId(), bucket.getName());

		// activity
		activityService.addActivity("post", obj.getId(), 100,
				UserContext.getUserTenant());

		return Response.status(200).entity(obj.getId()).build();
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		logger.info("delete obj [{}] user [{}] tenant [{}]", id, UserContext.getLoggedInUser(),
				UserContext.getUserTenant());

		objService.delete(id);

		// activity
		activityService.addActivity("delete", id, 100,
				UserContext.getUserTenant());

		return Response.status(200).entity(id).build();
	}

	@GET
	@Path("/{id}")
	public List<Obj> get(@PathParam("id") String id) {
		logger.info("get obj content for [{}] user [{}] tenant [{}]", id, UserContext.getLoggedInUser(),
				UserContext.getUserTenant());

		// activity
		activityService.addActivity("get", id, 100,
				UserContext.getUserTenant());

		return objService.getObjItems(id);
	}

	@GET
	public List<Obj> getFromHome() {
		logger.info("get obj content for [/] user [{}] tenant [{}]", UserContext.getLoggedInUser(),
				UserContext.getUserTenant());
		return objService.getHomeItems();
	}

	@POST
	@Path(value = "/public/{id}")
	public Response makeBucketPublic(@PathParam("id") String id) {
		logger.info("make obj [{}] public user [{}] tenant [{}]", id, UserContext.getLoggedInUser(),
				UserContext.getUserTenant());

		objService.makeObjPublic(id);

		// activity
		activityService.addActivity("post", id, 100,
				UserContext.getUserTenant());

		return Response.status(200).entity(id).build();
	}

}