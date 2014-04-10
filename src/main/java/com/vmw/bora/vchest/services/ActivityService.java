package com.vmw.bora.vchest.services;

import java.util.List;

import com.vmw.bora.vchest.domain.Activity;

public interface ActivityService {

	/**
	 * TODO: add name along with obj id
	 * 
	 * @param type
	 * @param on
	 * @param size
	 * @param tenant
	 */
	public void addActivity(String type, String on, long size,
			String tenant);

	public void save(Activity activity);

	public List<Activity> getAll();

}