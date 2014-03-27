package com.vmw.bora.vchest.repo.cassandra;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.ObjBlob;

@Repository
public interface ObjBlobRepo extends
		TypedIdCassandraRepository<ObjBlob, String> {
	
	@Query("SELECT * FROM ObjBlob o WHERE o.objId = ?1")
	List<ObjBlob> findByObjId(String objId);
}
