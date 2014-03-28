package com.vmw.bora.vchest.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Activity;
import com.vmw.bora.vchest.domain.Stats;
import com.vmw.bora.vchest.repo.cassandra.ActivityRepo;
import com.vmw.bora.vchest.repo.solr.ActivitySolrRepo;
import com.vmw.bora.vchest.rest.UserContext;
import com.vmw.bora.vchest.search.SearchUtil;

@Service
@Transactional
public class ActivityServiceImpl {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ActivityRepo activityRepo;
	
	@Autowired
	private StatsServiceImpl statServiceImpl;

	@Autowired
	private ActivitySolrRepo activitySolrRepo;

	public void addActivity(String type, String on, String size, String tenant) {
		Activity a = new Activity();
		a.setId(UUID.randomUUID().toString());
		a.setDate(new Date().toString());
		a.setActivity(type);
		a.setObjId(on);
		a.setSize(size);
		String user = UserContext.getLoggedInUser(); 
		a.setUser(user);
		a.setTenantId(tenant);
		save(a);
		
		Stats stat = statServiceImpl.findByUser(user);
		if(stat!= null)
		{
			switch (type) {
			case "post":
				stat.setUploadedBytes(String.valueOf(Integer.valueOf(stat
						.getUploadedBytes()) + Integer.valueOf(size)));
				stat.setStorage(stat.getStorage() + size);
			case "get":
				stat.setDownloadedBytes(String.valueOf(Integer.valueOf(stat
						.getDownloadedBytes()) + Integer.valueOf(size)));
			case "delete":
				stat.setStorage(String.valueOf(Integer.valueOf(stat
						.getStorage()) - Integer.valueOf(size)));
			case "search":
				stat.setDownloadedBytes(String.valueOf(Integer.valueOf(stat
						.getDownloadedBytes()) + 1));
			default:
			}
			statServiceImpl.save(stat);
		} else {
			Stats stats = new Stats();
			stats.setMonth("March");
			stats.setYear("2014");
			stats.setUser(user);
			switch (type) {
			case "post":
				stats.setUploadedBytes(size);
				stats.setStorage(size);
			case "get":
				stats.setDownloadedBytes(size);
			default:
				// delete & search on new objects not allowed
			}
			statServiceImpl.save(stat);
		}
	}
	
	public void save(Activity activity) {
		activityRepo.save(activity);
		activitySolrRepo.save(activity);
	}
	
	public List<Activity> findByUser(String user) {
		return activitySolrRepo.findByUser(user, SearchUtil.sortByYearMonth());
	}
}
