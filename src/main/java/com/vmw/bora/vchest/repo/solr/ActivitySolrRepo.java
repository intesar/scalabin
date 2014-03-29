package com.vmw.bora.vchest.repo.solr;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Activity;

@Repository
public interface ActivitySolrRepo extends SolrCrudRepository<Activity, String> {

	public List<Activity> findByUsername(String user, Sort s);

}
