package com.vmw.bora.vchest.rest;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class MyApplication extends Application {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public Set<Class<?>> getClasses() {
		logger.info("setting rest classes and config");
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		// register resources and features
		classes.add(MultiPartFeature.class);
		// classes.add(MultiPartResource.class);
		classes.add(LoggingFilter.class);
		return classes;
	}
}