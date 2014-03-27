package com.vmw.bora.vchest.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.ObjBlob;
import com.vmw.bora.vchest.repo.cassandra.ObjBlobRepo;
import com.vmw.bora.vchest.repo.solr.ObjBlobSolrRepo;

@Service
@Transactional
public class ObjBlobServiceImpl {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ObjBlobRepo objBlobRepo;

	@Autowired
	private ObjBlobSolrRepo objBlobSolrRepo;

	public void save(ObjBlob objBlob) {
		if (objBlobRepo.findByObjId(objBlob.getObjId()) !=null) {
			logger.error("Blob with that obj ID already exists: " + objBlob.getObjId());
			throw new RuntimeException();
		}
		objBlobRepo.save(objBlob);
		objBlobSolrRepo.save(objBlob);
	}
}
