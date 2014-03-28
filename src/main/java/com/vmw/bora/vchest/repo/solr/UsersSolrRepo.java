package com.vmw.bora.vchest.repo.solr;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Users;

@Repository
public interface UsersSolrRepo extends SolrCrudRepository<Users, String> {

	
}
