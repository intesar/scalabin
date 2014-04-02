package com.vmw.bora.vchest.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Users;
import com.vmw.bora.vchest.dto.UsersDto;
import com.vmw.bora.vchest.services.UsersService;

@Component
@Path("/tenant")
public class TenantUserRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UsersService usersService;

	@GET
	@Path("/ping")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String save() {
		return "live";
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/user")
	public Response save(UsersDto dto) {

		String tenant = UserContext.getUserTenant();

		logger.info("add user [{}] tenant [{}] by [{}]", dto.getUsername(),
				tenant, UserContext.getLoggedInUser());

		Users user = usersService.addUserToTenant(dto.getUsername(),
				dto.getPassword());

		return Response.status(200).entity(user.getId()).build();
	}

}