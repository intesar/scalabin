package com.vmw.bora.vchest.repo.solr;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Activity;

@Repository
public interface ActivitySolrRepo extends SolrCrudRepository<Activity, String> {

}
