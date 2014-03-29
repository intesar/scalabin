package com.vmw.bora.vchest.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.services.ActivityServiceImpl;
import com.vmw.bora.vchest.services.ObjSearchServiceImpl;
import com.vmw.bora.vchest.services.ObjServiceImpl;
import com.vmw.bora.vchest.services.UsersServiceImpl;

@Component
@Path("/search")
public class SearchRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ObjServiceImpl objServiceImpl;

	@Autowired
	UsersServiceImpl usersServiceImpl;

	@Autowired
	ObjSearchServiceImpl objSearchServiceImpl;

	@Autowired
	ActivityServiceImpl activityServiceImpl;

	/*
	 * @GET
	 * 
	 * @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 * 
	 * @Path("/kind/{term}") public List<Obj> getByKind(@PathParam("term")
	 * String term) { System.out.println("search on term: " + term); return
	 * objSearchServiceImpl.searchByKind(term); }
	 */

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/user/{id}/query/{q}")
	public List<Obj> getByAll(@PathParam("id") String id,
			@PathParam("q") String q) {
		logger.info("search on query [{}]", q);
		return objSearchServiceImpl.searchByAllFields(q);
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{q}")
	public List<Obj> getByAll(@PathParam("q") String q) {
		logger.info("search on query [{}] user [{}]", q, UserContext.getLoggedInUser());
		String owner = UserContext.getLoggedInUser();
		String tenant = usersServiceImpl.getTenant(owner);

		List<Obj> result = objSearchServiceImpl
				.findByBucketNameContainingAndOwnerAndTenant(q, owner, tenant);

		// activity
		activityServiceImpl.addActivity("search", "Obj", result.size(),
				usersServiceImpl.getTenant(UserContext.getLoggedInUser()));

		return result;

	}
}