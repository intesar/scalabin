package com.vmw.bora.vchest.services;

import java.util.List;

import com.vmw.bora.vchest.domain.Obj;

public interface ObjSearchService {

	public abstract void addToIndex(Obj o);

	public abstract void deleteFromIndex(String id);

	public abstract List<Obj> searchByKind(String kind);

	public abstract List<Obj> searchByAllFields(String searchTerm,
			String username, String tenant);

	public abstract List<Obj> findByBucketNameContainingAndOwnerAndTenant(
			String name, String owner, String tenant);

}