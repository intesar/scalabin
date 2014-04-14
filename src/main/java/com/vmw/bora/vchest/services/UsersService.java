package com.vmw.bora.vchest.services;

import com.vmw.bora.vchest.domain.Users;

public interface UsersService {

	/**
	 * TODO: Verify and add tenant
	 * 
	 * @param username
	 * @param password
	 * @param tenant
	 * @return
	 */
	public abstract Users addUserAndTenant(String name, String username, String password,
			String tenant);

	/**
	 * TODO: Verify user exits
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public abstract Users addUserToTenant(String username, String password);

	public abstract Users getByUsername(String username, String tenant);

}