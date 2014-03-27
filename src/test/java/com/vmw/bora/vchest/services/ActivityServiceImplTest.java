package com.vmw.bora.vchest.services;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vmw.bora.vchest.domain.Activity;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class ActivityServiceImplTest {

	@Autowired()
	ActivityServiceImpl activityService;

	@Test(expected = RuntimeException.class)
	public void testSaveActivity() throws IOException {
		Activity activity = new Activity();
		activity.setActivity("upload");
		activity.setDate(new Date());
		activity.setId("123");
		activity.setObjId(235);
		activity.setSize(2048);
		activity.setUser("FooBar");
		activityService.save(activity);
		activityService.save(activity);
	}

}