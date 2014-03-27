package com.vmw.bora.vchest.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vmw.bora.vchest.domain.Users;
import com.vmw.bora.vchest.services.UsersServiceImpl;

@Component
@Path("/users")
public class UserRestService {

	@Autowired
	UsersServiceImpl usersService;

	@GET
	@Path("/ping")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String save() {
		return "done";
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String save(Users user) {
		user.setId(user.getUserName());
		user.setEnabled(true);
		usersService.save(user);
		return "done";
	}

}