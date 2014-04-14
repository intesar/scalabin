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
@Path("/users")
public class UserRestService {

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
	public Response save(UsersDto dto) {

		logger.info("adding user [{}] tenant [{}]", dto.getUsername(),
				dto.getTenantId());

		Users user = usersService.addUserAndTenant(dto.getName(), dto.getUsername(),
				dto.getPassword(), dto.getTenantId());

		return Response.status(200).entity(user.getId()).build();
	}

}