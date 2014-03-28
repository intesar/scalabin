package com.vmw.bora.vchest.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Stats;
import com.vmw.bora.vchest.repo.cassandra.StatsRepo;
import com.vmw.bora.vchest.repo.solr.StatsSolrRepo;
import com.vmw.bora.vchest.search.SearchUtil;

@Service
@Transactional
public class StatsServiceImpl {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private StatsRepo statsRepo;

	@Autowired
	private StatsSolrRepo statsSolrRepo;

	public void save(Stats stats) {
		statsRepo.save(stats);
		statsSolrRepo.save(stats);
	}
	
	public List<Stats> findByUser(String user) {
		return statsSolrRepo.findByUser(user, SearchUtil.sortByYearMonth());
	}
}
