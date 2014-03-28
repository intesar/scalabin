package com.vmw.bora.vchest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.repo.solr.ObjSolrRepo;
import com.vmw.bora.vchest.search.SearchUtil;

@Service
public class ObjSearchServiceImpl {

	@Autowired
	ObjSolrRepo objSolrRepo;
	
	public void addToIndex(Obj o){
		objSolrRepo.save(o);
	}
	
	public void deleteFromIndex(String id) {
		objSolrRepo.delete(id);
	}
	
	public List<Obj> searchByKind(String kind) {
		List<Obj> result = null;
		result = objSolrRepo.findByKind(kind, SearchUtil.sortByBucketName());
		return result;
	}
	
	public List<Obj> searchByAllFields(String searchTerm) {
		List<Obj> result = null;
		result = objSolrRepo.findByAllFields(searchTerm, SearchUtil.sortByBucketName());
		return result;
	}
	
	public List<Obj> findByBucketNameContainingAndOwnerAndTenant(String name, String owner, String tenant) {
		return this.objSolrRepo.findByBucketNameContainingAndOwnerAndTenant(name, owner, tenant);
	}
}
