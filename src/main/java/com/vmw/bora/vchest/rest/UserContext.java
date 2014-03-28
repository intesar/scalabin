package com.vmw.bora.vchest.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContext {
	public static String getLoggedInUser() {
		try {
			SecurityContext sc = SecurityContextHolder.getContext();

			Authentication auth = sc.getAuthentication();
			String name = auth.getName(); // get logged in username
			System.out.println("user from context : " + name);
			return name;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "admin";
		}

	}
}
