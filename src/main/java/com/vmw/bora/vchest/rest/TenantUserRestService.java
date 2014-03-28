package com.vmw.bora.vchest.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vmw.bora.vchest.domain.Users;
import com.vmw.bora.vchest.services.UsersServiceImpl;

@Component
@Path("/tenant")
public class TenantUserRestService {

	@Autowired
	UsersServiceImpl usersService;

	@GET
	@Path("/ping")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String save() {
		return "done";
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/user")
	public String save(Users user) {
		Users u = this.usersService.getByUsername(UserContext.getLoggedInUser());
		user.setId(user.getUserName());
		user.setEnabled(true);
		user.setTenantId(u.getTenantId());
		usersService.save(user);
		return "done";
	}

}