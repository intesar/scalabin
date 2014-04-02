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
	ActivityService activityService;

	@Test
	public void testSaveActivity() throws IOException {
		Activity activity = new Activity();
		activity.setActionType("upload");
		activity.setActionDate(new Date());
		activity.setId("1234");
		activity.setObjId("235");
		activity.setSize(2048);
		activity.setUsername("FooBar");
		activityService.save(activity);
	}

}
