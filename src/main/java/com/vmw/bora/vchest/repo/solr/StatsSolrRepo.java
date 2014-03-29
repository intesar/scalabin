package com.vmw.bora.vchest.repo.solr;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Stats;

@Repository
public interface StatsSolrRepo extends SolrCrudRepository<Stats, String> {

	@Query("username:?0")
	public Stats findByUsername(String user);
	
	public Stats findByUsernameAndTenantId(String username, String tenantId);
	
}
