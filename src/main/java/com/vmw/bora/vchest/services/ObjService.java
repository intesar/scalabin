package com.vmw.bora.vchest.services;

import java.nio.ByteBuffer;
import java.util.List;

import com.vmw.bora.vchest.domain.Blob;
import com.vmw.bora.vchest.domain.Obj;

public interface ObjService {

	/**
	 * verify unique file name recursively add file size recursively add
	 * itemcount
	 * 
	 * @param fileName
	 * @param parent
	 * @param bb
	 * @return
	 */
	public abstract String addObject(String fileName, String parent,
			ByteBuffer bb);

	/**
	 * check its a blob delete blob & obj
	 * 
	 * @param id
	 */
	public abstract void deleteBlob(String id);

	public abstract Blob findBlob(String id);

	/**
	 * add home folder
	 * 
	 * @param username
	 * @param tenant
	 * @return
	 */
	public abstract Obj addHomeBucket(String username, String tenant);

	public abstract Obj getHomeBucket();

	/**
	 * TODO: check folder name exits? TODO: verify parent exits null or empty
	 * parent is home
	 * 
	 * @param name
	 * @return
	 */
	public abstract Obj addFolder(String name, String parentId);

	public abstract void save(Obj obj);

	/**
	 * TODO: check its bucket TODO recursively delete all
	 * 
	 * @param id
	 */
	public abstract void delete(String id);

	public abstract Obj getByObjId(String id);

	public abstract List<Obj> getObjItems(String id);

	public abstract List<Obj> getHomeItems();

	public abstract Obj makeObjPublic(String id);

}