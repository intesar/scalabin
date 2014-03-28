package com.vmw.bora.vchest.repo.solr;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Activity;

@Repository
public interface ActivitySolrRepo extends SolrCrudRepository<Activity, String> {

	@Query("user:?0")
	public List<Activity> findByUser(String user, Sort s);

}
