package com.vmw.bora.vchest.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Activity;
import com.vmw.bora.vchest.services.ActivityService;

@Component
@Path("/activity")
@Produces({"application/json;qs=1", "application/xml;qs=0.5"})
public class ActivityRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActivityService activityService;

	@GET
	public List<Activity> get() {
		logger.info("get activity user [{}] tenant [{}]", UserContext.getLoggedInUser(), UserContext.getUserTenant());
		// TODO find user by tenant
		List<Activity> result = activityService.getAll();
		return result;
	}
}