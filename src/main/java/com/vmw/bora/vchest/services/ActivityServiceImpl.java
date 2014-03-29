package com.vmw.bora.vchest.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
	
	@Autowired
	UsersServiceImpl usersServiceImpl;

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
		
		Stats stat = statServiceImpl.findByUserAndTenant(user, tenant);
		if(stat!= null)
		{
			switch (type) {
			case "post":
				String ub = "0";
				if (StringUtils.isNotBlank(stat
						.getUploadedBytes())) {
					ub = stat
							.getUploadedBytes();
				}
				stat.setUploadedBytes(String.valueOf (Integer.valueOf(ub) + Integer.valueOf(size)));
				String s = "0";
				if (StringUtils.isNotBlank(stat
						.getStorage())) {
					s = stat
							.getStorage();
				}
				stat.setStorage(String.valueOf (Integer.valueOf(s) + Integer.valueOf(size)));
				break;
			case "get":
				String db = "0";
				if (StringUtils.isNotBlank(stat
						.getDownloadedBytes())) {
					db = stat
							.getDownloadedBytes();
				}
				stat.setDownloadedBytes(String.valueOf (Integer.valueOf(db) + Integer.valueOf(size)));
				break;
			case "delete":
				String s1 = "0";
				if (StringUtils.isNotBlank(stat
						.getStorage())) {
					s1 = stat
							.getStorage();
				}
				stat.setStorage(String.valueOf(Integer.valueOf(s1) - Integer.valueOf(size)));
				break;
			case "search":
				String db1 = "0";
				if (StringUtils.isNotBlank(stat
						.getDownloadedBytes())) {
					db1 = stat
							.getDownloadedBytes();
				}
				stat.setDownloadedBytes(String.valueOf(Integer.valueOf(db1) + 1));
			default:
			}
			statServiceImpl.save(stat);
		} else {
			Stats stats = new Stats();
			stats.setMonth("March");
			stats.setYear("2014");
			stats.setUser(user);
			stats.setTenant(this.usersServiceImpl.getTenant(user));
			switch (type) {
			case "post":
				stats.setUploadedBytes(size);
				stats.setStorage(size);
			case "get":
				stats.setDownloadedBytes(size);
			default:
				// delete & search on new objects not allowed
			}
			statServiceImpl.save(stats);
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
