package com.vmw.bora.vchest.services;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vmw.bora.vchest.domain.Users;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class UsersServiceImplTest {

	@Autowired()
	UsersServiceImpl usersService;

	@Test(expected = RuntimeException.class)
	public void testSaveEmployee() throws IOException {
		Users user = new Users();
		user.setEnabled(true);
		user.setPassword("FooPassword");
		user.setTenantId("FooTenant");
		user.setUsername("FooUser");
		usersService.save(user);
		usersService.save(user);
	}

}
