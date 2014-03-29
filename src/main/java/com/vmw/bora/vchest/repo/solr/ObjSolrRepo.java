package com.vmw.bora.vchest.repo.solr;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Obj;

@Repository
public interface ObjSolrRepo extends SolrCrudRepository<Obj, String> {

	List<Obj> findByParentAndOwnerAndTenantId(String id, String owner, String tenant);
	
	@Query("parent:home AND tenantId:?1 AND (owner:?0 OR shared:public)")
	List<Obj> findInHome(String owner, String tenant);
	
	
	Obj findByIdAndOwnerAndTenantId(String id, String owner, String tenant);
	

	@Query("kind:*?0*")
	public List<Obj> findByKind(String searchTerm, Sort s);

	@Query("id:*?0* OR " + "name:*?0* OR " + "kind:*?0* OR "
			+ "locationUri:*?0* OR " + "size:*?0* OR " + "parent:*?0* OR "
			+ "modified:*?0* OR " + "chunkCount:*?0* OR "
			+ "owner:*?0* OR " + "tenantId:*?0* OR " + "groupId:*?0* OR "
			+ "shared:*?0* OR ")
	public List<Obj> findByAllFields(String searchTerm, Sort s);
	
	public List<Obj> findByNameContainingAndOwnerAndTenantId(String name, String owner, String tenant);

}
