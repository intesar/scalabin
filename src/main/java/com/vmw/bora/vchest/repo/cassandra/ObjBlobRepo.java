package com.vmw.bora.vchest.repo.cassandra;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Blob;

@Repository
public interface ObjBlobRepo extends
		TypedIdCassandraRepository<Blob, String> {
	
	@Query("SELECT * FROM blob o WHERE o.id = ?1")
	List<Blob> findByObjId(String id);
}
