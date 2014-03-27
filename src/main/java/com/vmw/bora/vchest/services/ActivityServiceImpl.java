package com.vmw.bora.vchest.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Activity;
import com.vmw.bora.vchest.repo.cassandra.ActivityRepo;
import com.vmw.bora.vchest.repo.solr.ActivitySolrRepo;

@Service
@Transactional
public class ActivityServiceImpl {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ActivityRepo activityRepo;

	@Autowired
	private ActivitySolrRepo activitySolrRepo;

	public void save(Activity activity) {
		activityRepo.save(activity);
		activitySolrRepo.save(activity);
	}
}
