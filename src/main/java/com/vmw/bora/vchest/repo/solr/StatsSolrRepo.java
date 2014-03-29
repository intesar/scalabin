package com.vmw.bora.vchest.repo.solr;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Stats;

@Repository
public interface StatsSolrRepo extends SolrCrudRepository<Stats, String> {

	@Query("user:?0")
	public Stats findByUser(String user);
	
	public Stats findByUserAndTenant(String user, String tenant);
	
}
