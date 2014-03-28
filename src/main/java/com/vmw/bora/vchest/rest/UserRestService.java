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
import com.vmw.bora.vchest.services.ActivityServiceImpl;
import com.vmw.bora.vchest.services.UsersServiceImpl;

@Component
@Path("/users")
public class UserRestService {

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
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String save(Users user) {
		user.setId(user.getUserName());
		user.setEnabled(true);
		usersService.save(user);
		
		// activity
		activityServiceImpl.addActivity("addUser", "Users", "0K", usersService.getTenant(UserContext.getLoggedInUser()));

		return "done";
	}

}