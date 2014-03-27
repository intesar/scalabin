package com.vmw.bora.vchest.repo.solr;

import java.util.List;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Obj;

@Repository
public interface ObjSolrRepo extends SolrCrudRepository<Obj, String> {

	List<Obj> findByParent(String id);
	
}
