package com.vmw.bora.vchest.services;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vmw.bora.vchest.domain.Authority;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class AuthorityServiceImplTest {

	@Autowired()
	AuthorityServiceImpl authorityService;

	@Test(expected = RuntimeException.class)
	public void testSaveAuthority() throws IOException {
		Authority authority = new Authority();
		authority.setUserId("FooUser");
		authority.setAuthority("auth1");
		authorityService.save(authority);
		authorityService.save(authority);
	}

}
