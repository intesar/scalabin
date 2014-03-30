package com.vmw.bora.vchest.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Stats;
import com.vmw.bora.vchest.services.StatsServiceImpl;
import com.vmw.bora.vchest.services.UsersServiceImpl;

@Component
@Path("/stats")
public class StatsRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	StatsServiceImpl statsServiceImpl;

	@Autowired
	UsersServiceImpl usersServiceImpl;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Stats get() {
		String id = UserContext.getLoggedInUser();
		logger.info("get stats user [{}]", id);
		String tenant = UserContext.getUserTenant();
		Stats stats = statsServiceImpl.findByUserAndTenant(id, tenant);
		return stats;
	}
}