package com.vmw.bora.vchest.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {

	/**
	 * finds logged-in user's username.
	 */
	public static String getLoggedInUser() {
		String username = getPrincipal();
		return username.split("@")[0];
	}

	/**
	 * finds logged-in user's tenant.
	 */
	public static String getUserTenant() {
		String username = getPrincipal();
		return username.split("@")[1];
	}

	private static String getPrincipal() {
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication auth = sc.getAuthentication();
		String name = auth.getName();
		return name;
	}

}
