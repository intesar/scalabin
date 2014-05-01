package com.vmw.bora.vchest.services.impl;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Blob;
import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.repo.cassandra.ObjBlobRepo;
import com.vmw.bora.vchest.repo.cassandra.ObjCassandraRepo;
import com.vmw.bora.vchest.repo.solr.ObjSolrRepo;
import com.vmw.bora.vchest.rest.UserContext;
import com.vmw.bora.vchest.services.ObjService;

@Service
@Transactional
public class ObjServiceImpl implements ObjService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private AtomicInteger fileCount = new AtomicInteger();
	@Autowired
	private ObjCassandraRepo objCassandraRepo;

	@Autowired
	private ObjSolrRepo objSolrRepo;

	@Autowired
	private ObjBlobRepo objBlobRepo;

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#addObject(java.lang.String, java.lang.String, java.nio.ByteBuffer)
	 */
	@Override
	public String addObject(String fileName, String parent, ByteBuffer bb) {

		Obj parentBucket = null;
		parentBucket = getParentBucket(parent);

		isUniqueName(fileName, parentBucket.getId());

		Obj obj = new Obj();
		obj.setId(UUID.randomUUID().toString());
		obj.setName(fileName);

		obj.setModified(new Date());
		obj.setChunkCount(1);
		obj.setKind("file");
		obj.setOwner(UserContext.getLoggedInUser());
		obj.setTenantId(UserContext.getUserTenant());
		obj.setSize(bb.limit());
		obj.setParent(parentBucket.getId());

		save(obj);

		Blob blob = new Blob();
		blob.setId(obj.getId());
		blob.setBlob(bb);
		objBlobRepo.save(blob);

		logger.info("uploading file [{}] size [{}] count [{}]", obj.getName(), bb.limit(), fileCount.incrementAndGet());
		//addSizeAndItemCountRecusively(parentBucket, bb.limit());

		return blob.getId();
	}

	private Obj getParentBucket(String parent) {
		Obj parentBucket;
		if (StringUtils.isBlank(parent)) {
			parentBucket = getHomeBucket();
		} else {
			parentBucket = getByObjId(parent);
		}
		return parentBucket;
	}

	private void isUniqueName(String fileName, String parentBucketId) {
		Obj unique = this.objSolrRepo.findByNameAndParentAndOwnerAndTenantId(
				fileName, parentBucketId, UserContext.getLoggedInUser(),
				UserContext.getUserTenant());
		if (unique != null) {
			//throw new RuntimeException("File exists with the name : " + fileName);
		}
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#deleteBlob(java.lang.String)
	 */
	@Override
	public void deleteBlob(String id) {

		Blob blob = objBlobRepo.findOne(id);
		if (blob == null) {
			logger.error("Delete failed following object not found id:" + id);
			throw new RuntimeException();
		}

		objBlobRepo.delete(blob);
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#findBlob(java.lang.String)
	 */
	@Override
	public Blob findBlob(String id) {
		if (getByObjId(id) == null) {
			throw new RuntimeException();
		}

		Blob blob = objBlobRepo.findOne(id);
		return blob;
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#addHomeBucket(java.lang.String, java.lang.String)
	 */
	@Override
	public Obj addHomeBucket(String username, String tenant) {
		Obj obj = new Obj();
		obj.setId(UUID.randomUUID().toString());
		obj.setItemCount(0);
		obj.setKind("folder");
		obj.setLocation("");
		obj.setModified(new Date());
		obj.setName("home");
		obj.setOwner(username);
		obj.setSize(0);
		obj.setTenantId(tenant);
		obj.setHome(true);

		save(obj);

		return obj;
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#getHomeBucket()
	 */
	@Override
	public Obj getHomeBucket() {
		return objSolrRepo.findByHomeAndOwnerAndTenantId(true,
				UserContext.getLoggedInUser(), UserContext.getUserTenant());
	}
	
	public Obj renameObj(String id, String newName) {
		String owner = UserContext.getLoggedInUser();
		String tenant = UserContext.getUserTenant();
		Obj obj = objSolrRepo.findByIdAndOwnerAndTenantId(id, owner, tenant);
		isUniqueName(newName, obj.getParent());
		obj.setName(newName);
		save(obj);
		return obj;
		
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#addFolder(java.lang.String, java.lang.String)
	 */
	@Override
	public Obj addFolder(String name, String parentId) {

		Obj parent = null;
		isUniqueName(name, getParentBucket(parentId).getId());

		Obj obj = new Obj();
		obj.setId(UUID.randomUUID().toString());
		obj.setName(name);

		if (StringUtils.isBlank(parentId)) {
			parent = getHomeBucket();
			obj.setParent(parent.getId());
			obj.setLocation("");
		} else {
			obj.setParent(parentId);
			parent = getByObjId(obj.getParent());
			String parentLoc = StringUtils.isEmpty(parent.getLocation()) ? "" : parent.getLocation() + "/";
			obj.setLocation(parentLoc + parent.getId() + ":" + parent.getName());
		}

		obj.setModified(new Date());
		obj.setChunkCount(0);
		obj.setKind("folder");
		obj.setSize(0);
		obj.setItemCount(0);
		obj.setOwner(UserContext.getLoggedInUser());
		obj.setTenantId(UserContext.getUserTenant());

		save(obj);

		//addSizeAndItemCountRecusively(parent, 0);

		return obj;
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#save(com.vmw.bora.vchest.domain.Obj)
	 */
	@Override
	public void save(Obj obj) {
		objCassandraRepo.save(obj);
		objSolrRepo.save(obj);
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) {
		Obj obj = getByObjId(id);
		if (obj == null) {
			throw new RuntimeException();
		}

		deleteObj(obj);
		//substractSizeAndItemCountRecusively(getByObjId(obj.getParent()),
		//		obj.getSize());
	}

	private void deleteObj(Obj obj) {
		logger.info("Deleting obj [{}]", obj.getName());
		objCassandraRepo.delete(obj);
		objSolrRepo.delete(obj);
		if (obj.getKind() == "file") {
			deleteBlob(obj.getId());
		}
		for (Obj o : getObjItems(obj.getId())) {
			deleteObj(o);
		}
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#getByObjId(java.lang.String)
	 */
	@Override
	public Obj getByObjId(String id) {
		return objSolrRepo.findByIdAndOwnerAndTenantId(id,
				UserContext.getLoggedInUser(), UserContext.getUserTenant());
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#getObjItems(java.lang.String)
	 */
	@Override
	public List<Obj> getObjItems(String id) {
		return objSolrRepo.findByParentAndOwnerAndTenantId(id,
				UserContext.getLoggedInUser(), UserContext.getUserTenant());
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#getHomeItems()
	 */
	@Override
	public List<Obj> getHomeItems() {
		return objSolrRepo.findByParentAndOwnerAndTenantId(getHomeBucket()
				.getId(), UserContext.getLoggedInUser(), UserContext
				.getUserTenant());
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.ObjService#makeObjPublic(java.lang.String)
	 */
	@Override
	public Obj makeObjPublic(String id) {
		Obj obj = getByObjId(id);
		obj.setShared("public");
		save(obj);
		return obj;
	}

	private void addSizeAndItemCountRecusively(Obj obj, long size) {
		logger.info("adding item count [1] and size [{}] to obj [{}] ", size,
				obj.getName());
		obj.setSize(obj.getSize() + size);
		obj.setItemCount(obj.getItemCount() + 1);
		save(obj);
		if (StringUtils.isNotBlank(obj.getParent())) {
			addSizeAndItemCountRecusively(getByObjId(obj.getParent()), size);
		}
	}

	private void substractSizeAndItemCountRecusively(Obj obj, long size) {
		logger.info("substracting item count [1] and size [{}] to obj [{}] ",
				size, obj.getName());
		obj.setSize(obj.getSize() - size);
		obj.setItemCount(obj.getItemCount() - 1);
		save(obj);
		if (StringUtils.isNotBlank(obj.getParent())) {
			substractSizeAndItemCountRecusively(getByObjId(obj.getParent()),
					size);
		}
	}

}
