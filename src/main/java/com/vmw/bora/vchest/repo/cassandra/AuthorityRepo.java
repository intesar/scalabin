package com.vmw.bora.vchest.repo.cassandra;

import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Authority;

@Repository
public interface AuthorityRepo extends
		TypedIdCassandraRepository<Authority, String> {

}
