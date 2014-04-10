package com.vmw.bora.vchest.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Activity;
import com.vmw.bora.vchest.domain.Stats;
import com.vmw.bora.vchest.repo.cassandra.ActivityRepo;
import com.vmw.bora.vchest.repo.solr.ActivitySolrRepo;
import com.vmw.bora.vchest.rest.UserContext;
import com.vmw.bora.vchest.search.SearchUtil;
import com.vmw.bora.vchest.services.ActivityService;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ActivityRepo activityRepo;

	@Autowired
	private StatsServiceImpl statService;

	@Autowired
	private ActivitySolrRepo activitySolrRepo;

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ActivityService#addActivity(java.lang.String, java.lang.String, long, java.lang.String)
	 */
	@Override
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

		Stats stat = statService.findByUserAndTenant(user, tenant);
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
			statService.save(stat);
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
			statService.save(stats);
		}
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ActivityService#save(com.vmw.bora.vchest.domain.Activity)
	 */
	@Override
	public void save(Activity activity) {
		activityRepo.save(activity);
		activitySolrRepo.save(activity);
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ActivityService#findByUser(java.lang.String)
	 */
	@Override
	public List<Activity> getAll() {
		return activitySolrRepo.findByUsernameAndTenantId(UserContext.getLoggedInUser(), UserContext.getUserTenant(), 
				SearchUtil.sortByYearMonth());
	}
}
