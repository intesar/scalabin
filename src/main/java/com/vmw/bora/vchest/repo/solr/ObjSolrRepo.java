package com.vmw.bora.vchest.repo.solr;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Obj;

@Repository
public interface ObjSolrRepo extends SolrCrudRepository<Obj, String> {

	List<Obj> findByParentAndOwner(String id, String owner);
	

	@Query("kind:*?0*")
	public List<Obj> findByKind(String searchTerm, Sort s);

	@Query("id:*?0*^0.1 OR " + "bucketName:*?0* OR " + "kind:*?0* OR "
			+ "locationUri:*?0* OR " + "size:*?0*^0.1 OR " + "parent:*?0* OR "
			+ "dateModified:*?0* OR " + "chunkCount:*?0*^0.1 OR "
			+ "owner:*?0* OR " + "tenant:*?0* OR " + "group:*?0* OR "
			+ "shared:*?0*^0.1 OR ")
	public List<Obj> findByAllFields(String searchTerm, Sort s);

}
