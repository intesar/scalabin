package com.vmw.bora.vchest.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.repo.cassandra.ObjCassandraRepo;
import com.vmw.bora.vchest.repo.solr.ObjSolrRepo;

@Service
@Transactional
public class ObjServiceImpl {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ObjCassandraRepo objCassandraRepo;

	@Autowired
	private ObjSolrRepo objSolrRepo;

	public void save(Obj obj) {
		if (obj.getParent().equals("home")) {
			obj.setLocation("/" + obj.getParent());
		} else {
			// A h /home
			// B a /home/a
			// C b /home/a/b
			Obj parent = objCassandraRepo.findOne(obj.getParent());
			obj.setLocation(parent.getLocation() + "/" + parent.getName());
		}

		objCassandraRepo.save(obj);
		objSolrRepo.save(obj);
	}

	public void delete(String id) {
		Obj obj = objCassandraRepo.findOne(id);
		if (obj == null) {
			logger.error("Delete failed following object not found bucket:"
					+ obj.getName() + " uri:" + obj.getLocation());
			throw new RuntimeException();
		}
		objCassandraRepo.delete(obj);
		objSolrRepo.delete(obj);
	}

	public void deleteAll() {
		objCassandraRepo.deleteAll();
		objSolrRepo.deleteAll();
	}

	public boolean find(String id) {
		Obj obj = objCassandraRepo.findOne(id);
		if (obj == null) {
			return false;
		}
		return true;
	}

	public Obj getByObjId(String id, String owner, String tenant) {
		return objSolrRepo.findByIdAndOwnerAndTenantId(id, owner, tenant);
	}

	public List<Obj> getObjs(String id, String owner, String tenant) {
		if (id.equals("home")) {
			return objSolrRepo.findInHome(owner, tenant);
		}
		return objSolrRepo.findByParentAndOwnerAndTenantId(id, owner, tenant);
	}
}
