package com.vmw.bora.vchest.repo.cassandra;

import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Activity;

@Repository
public interface ActivityRepo extends
		TypedIdCassandraRepository<Activity, String> {

}
