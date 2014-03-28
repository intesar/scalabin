package com.vmw.bora.vchest.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Blob;
import com.vmw.bora.vchest.repo.cassandra.ObjBlobRepo;

@Service
@Transactional
public class ObjBlobServiceImpl {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ObjBlobRepo objBlobRepo;

	public void save(Blob objBlob) {
		if (objBlobRepo.findOne(objBlob.getId())!=null) {
			logger.error("Blob with that obj ID already exists: " + objBlob.getId());
			throw new RuntimeException();
		}
		objBlobRepo.save(objBlob);
	}
}
