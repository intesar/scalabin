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
import com.vmw.bora.vchest.services.ActivityService;
import com.vmw.bora.vchest.services.ObjSearchService;

@Component
@Path("/search")
public class SearchRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ObjSearchService objSearchService;

	@Autowired
	private ActivityService activityService;

	/*
	 * @GET
	 * 
	 * @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 * 
	 * @Path("/kind/{term}") public List<Obj> getByKind(@PathParam("term")
	 * String term) { System.out.println("search on term: " + term); return
	 * objSearchServiceImpl.searchByKind(term); }
	 */

	// @GET
	// @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	// @Path("/user/{id}/query/{q}")
	// public List<Obj> getByAll(@PathParam("id") String id,
	// @PathParam("q") String q) {
	// logger.info("search on query [{}]", q);
	// return objSearchServiceImpl.searchByAllFields(q);
	// }

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{q}")
	public List<Obj> getByAll(@PathParam("q") String q) {
		
		String owner = UserContext.getLoggedInUser();
		String tenant = UserContext.getUserTenant();
		
		logger.info("search on query [{}] user [{}] tenant [{}]", q,
				owner, tenant);

		List<Obj> result = objSearchService.searchByAllFields(q, owner,
				tenant);

		// activity
		activityService.addActivity("search", "Obj", result.size(),
				UserContext.getUserTenant());

		return result;

	}
}