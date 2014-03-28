package com.vmw.bora.vchest.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Activity;
import com.vmw.bora.vchest.services.ActivityServiceImpl;

@Component
@Path("/activity")
public class ActivityRestService {
	
	@Autowired
	ActivityServiceImpl activityServiceImpl;
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	//@Path("/user/{id}")
	public List<Activity> get(/*@PathParam("id") String id*/) {
		String id = UserContext.getLoggedInUser();
		System.out.println("Showing activity for user: " + id);
		List<Activity> result = activityServiceImpl.findByUser(id);
		return result;
	}
}