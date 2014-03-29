package com.vmw.bora.vchest.services;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Stats;
import com.vmw.bora.vchest.repo.cassandra.StatsRepo;
import com.vmw.bora.vchest.repo.solr.StatsSolrRepo;

@Service
@Transactional
public class StatsServiceImpl {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private StatsRepo statsRepo;

	@Autowired
	private StatsSolrRepo statsSolrRepo;

	public void save(Stats stats) {
		if ( StringUtils.isEmpty(stats.getId())) {
			stats.setId(UUID.randomUUID().toString());
		}
		statsRepo.save(stats);
		statsSolrRepo.save(stats);
	}
	
	public void update(Stats stats) {
		statsRepo.save(stats);
	}
	
	//public Stats findByUser(String user) {
	//	return statsSolrRepo.findByUser(user);
	//}
	
	public Stats findByUserAndTenant(String user, String tenant ) {
		return statsSolrRepo.findByUsernameAndTenantId(user,tenant);
	}
}
