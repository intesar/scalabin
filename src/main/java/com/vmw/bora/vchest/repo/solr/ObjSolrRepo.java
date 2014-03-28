package com.vmw.bora.vchest.repo.solr;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Obj;

@Repository
public interface ObjSolrRepo extends SolrCrudRepository<Obj, String> {

	List<Obj> findByParentAndOwnerAndTenant(String id, String owner, String tenant);
	
	@Query("parent:home AND tenant:?1 AND (owner:?0 OR shared:public)")
	List<Obj> findInHome(String owner, String tenant);
	
	
	Obj findByIdAndOwnerAndTenant(String id, String owner, String tenant);
	

	@Query("kind:*?0*")
	public List<Obj> findByKind(String searchTerm, Sort s);

	@Query("id:*?0* OR " + "bucketName:*?0* OR " + "kind:*?0* OR "
			+ "locationUri:*?0* OR " + "size:*?0* OR " + "parent:*?0* OR "
			+ "dateModified:*?0* OR " + "chunkCount:*?0* OR "
			+ "owner:*?0* OR " + "tenant:*?0* OR " + "group:*?0* OR "
			+ "shared:*?0* OR ")
	public List<Obj> findByAllFields(String searchTerm, Sort s);
	
	public List<Obj> findByBucketNameContainingAndOwnerAndTenant(String name, String owner, String tenant);

}
