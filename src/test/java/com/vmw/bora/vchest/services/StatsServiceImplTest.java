package com.vmw.bora.vchest.services;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vmw.bora.vchest.domain.Stats;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class StatsServiceImplTest {

	@Autowired()
	StatsServiceImpl statsService;

	@Test
	public void testSaveStats() throws IOException {
		Stats stats = new Stats();
		stats.setDownloadedBytes("1024");
		stats.setId("123");
		stats.setMonth("1");;
		stats.setStorage("512");
		stats.setUploadedBytes("512");
		stats.setUser("Foo bar");
		stats.setYear("2014");
		statsService.save(stats);
	}

}
