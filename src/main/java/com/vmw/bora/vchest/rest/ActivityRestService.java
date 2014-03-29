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
import com.vmw.bora.vchest.services.ActivityServiceImpl;

@Component
@Path("/activity")
public class ActivityRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ActivityServiceImpl activityServiceImpl;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Activity> get() {
		String id = UserContext.getLoggedInUser();
		logger.info("get activity [{}]", id);
		List<Activity> result = activityServiceImpl.findByUser(id);
		return result;
	}
}