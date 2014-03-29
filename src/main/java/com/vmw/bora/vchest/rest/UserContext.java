package com.vmw.bora.vchest.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {

	private static Logger logger = LoggerFactory.getLogger(UserContext.getLoggedInUser());

	public static String getLoggedInUser() {
		try {
			SecurityContext sc = SecurityContextHolder.getContext();
			Authentication auth = sc.getAuthentication();
			String name = auth.getName();
			return name;
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage(), ex);
			throw new RuntimeException("User not authenticated!");
		}

	}
}
