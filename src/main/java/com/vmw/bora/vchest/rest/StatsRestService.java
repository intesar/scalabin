package com.vmw.bora.vchest.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Stats;
import com.vmw.bora.vchest.services.StatsServiceImpl;
import com.vmw.bora.vchest.services.UsersServiceImpl;

@Component
@Path("/stats")
public class StatsRestService {
	
	@Autowired
	StatsServiceImpl statsServiceImpl;
	
	@Autowired
	UsersServiceImpl usersServiceImpl;
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Stats get() {
		String id = UserContext.getLoggedInUser();
		String tenant = usersServiceImpl.getTenant(id);
		System.out.println("Showing stats for user: " + id);
		Stats stats =  statsServiceImpl.findByUserAndTenant(id, tenant);
		return stats;
	}
}