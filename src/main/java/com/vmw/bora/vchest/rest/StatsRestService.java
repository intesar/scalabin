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
import com.vmw.bora.vchest.services.UsersService;
import com.vmw.bora.vchest.services.impl.StatsServiceImpl;

@Component
@Path("/stats")
public class StatsRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StatsServiceImpl statsServiceImpl;

	@Autowired
	UsersService usersServiceImpl;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Stats get() {
		logger.info("get stats user [{}] tenants [{}]", UserContext.getLoggedInUser(), UserContext.getUserTenant());
		String id = UserContext.getLoggedInUser();
		String tenant = UserContext.getUserTenant();
		Stats stats = statsServiceImpl.findByUserAndTenant(id, tenant);
		return stats;
	}
}