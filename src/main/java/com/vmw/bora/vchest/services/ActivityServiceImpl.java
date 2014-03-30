package com.vmw.bora.vchest.services;

import java.util.Calendar;
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

	@Autowired
	UsersServiceImpl usersServiceImpl;

	public void addActivity(String type, String on, long size, String tenant) {
		Activity a = new Activity();
		a.setId(UUID.randomUUID().toString());
		a.setActionDate(new Date());
		a.setActionType(type);
		a.setObjId(on);
		a.setSize(size);
		String user = UserContext.getLoggedInUser();
		a.setUsername(user);
		a.setTenantId(tenant);
		save(a);

		Stats stat = statServiceImpl.findByUserAndTenant(user, tenant);
		if (stat != null) {
			switch (type) {
			case "post":
				stat.setUploadedBytes(stat.getUploadedBytes() + size);
				stat.setStorage(stat.getStorage() + size);
				break;
			case "get":
				stat.setDownloadedBytes(stat.getDownloadedBytes() + size);
				break;
			case "delete":
				stat.setStorage(stat.getStorage() - size);
				break;
			case "search":
				stat.setDownloadedBytes(stat.getDownloadedBytes() + 1);
			default:
			}
			statServiceImpl.save(stat);
		} else {
			Stats stats = new Stats();
			Calendar now = Calendar.getInstance();
			stats.setMonth(now.get(Calendar.MONTH));
			stats.setYear(now.get(Calendar.YEAR));
			stats.setUsername(user);
			stats.setTenantId(UserContext.getUserTenant());
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
		return activitySolrRepo.findByUsername(user,
				SearchUtil.sortByYearMonth());
	}
}
