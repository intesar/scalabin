package com.vmw.bora.vchest.repo.cassandra;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Blob;

@Repository
public interface ObjBlobRepo extends
		TypedIdCassandraRepository<Blob, String> {
	
	@Query("SELECT * FROM ObjBlob o WHERE o.objId = ?1")
	List<Blob> findByObjId(String objId);
}
