package com.vmw.bora.vchest.services.impl;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Stats;
import com.vmw.bora.vchest.repo.cassandra.StatsRepo;
import com.vmw.bora.vchest.repo.solr.StatsSolrRepo;
import com.vmw.bora.vchest.services.StatsService;

@Service
@Transactional
public class StatsServiceImpl implements StatsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StatsRepo statsRepo;

	@Autowired
	private StatsSolrRepo statsSolrRepo;

	public void save(Stats stats) {
		if (StringUtils.isEmpty(stats.getId())) {
			stats.setId(UUID.randomUUID().toString());
		}
		statsRepo.save(stats);
		statsSolrRepo.save(stats);
	}

	public void update(Stats stats) {
		statsRepo.save(stats);
	}

	public Stats findByUserAndTenant(String user, String tenant) {
		return statsSolrRepo.findByUsernameAndTenantId(user, tenant);
	}
}
