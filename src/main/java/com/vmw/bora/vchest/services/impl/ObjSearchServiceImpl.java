package com.vmw.bora.vchest.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.repo.solr.ObjSolrRepo;
import com.vmw.bora.vchest.search.SearchUtil;
import com.vmw.bora.vchest.services.ObjSearchService;

@Service
public class ObjSearchServiceImpl implements ObjSearchService {

	@Autowired
	private ObjSolrRepo objSolrRepo;

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjSearchService#addToIndex(com.vmw.bora.vchest.domain.Obj)
	 */
	@Override
	public void addToIndex(Obj o) {
		objSolrRepo.save(o);
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjSearchService#deleteFromIndex(java.lang.String)
	 */
	@Override
	public void deleteFromIndex(String id) {
		objSolrRepo.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjSearchService#searchByKind(java.lang.String)
	 */
	@Override
	public List<Obj> searchByKind(String kind) {
		List<Obj> result = null;
		result = objSolrRepo.findByKind(kind, SearchUtil.sortByBucketName());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjSearchService#searchByAllFields(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Obj> searchByAllFields(String searchTerm, String username,
			String tenant) {
		List<Obj> result = null;
		result = objSolrRepo.findByAllFields(searchTerm, username, tenant,
				SearchUtil.sortByBucketName());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjSearchService#findByBucketNameContainingAndOwnerAndTenant(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Obj> findByBucketNameContainingAndOwnerAndTenant(String name,
			String owner, String tenant) {
		return this.objSolrRepo.findByNameContainingAndOwnerAndTenantId(name,
				owner, tenant);
	}
}
