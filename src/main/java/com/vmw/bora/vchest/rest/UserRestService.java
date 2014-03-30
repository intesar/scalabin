package com.vmw.bora.vchest.rest;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Users;
import com.vmw.bora.vchest.services.ActivityServiceImpl;
import com.vmw.bora.vchest.services.UsersServiceImpl;

@Component
@Path("/users")
public class UserRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UsersServiceImpl usersService;

	@Autowired
	ActivityServiceImpl activityServiceImpl;

	@GET
	@Path("/ping")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String save() {
		return "done";
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String save(Users user) {
		
		logger.info("add tenant [{}] user [{}]", user.getTenantId(),
				user.getUsername());
		
		user.setId(UUID.randomUUID().toString());
		user.setEnabled(true);
		user.setGroupId("default");
		usersService.save(user);

		return "done";
	}

}