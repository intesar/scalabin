package com.vmw.bora.vchest.repo.solr;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.ObjBlob;

@Repository
public interface ObjBlobSolrRepo extends SolrCrudRepository<ObjBlob, String> {

}
