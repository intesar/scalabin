package com.vmw.bora.vchest.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.services.ObjSearchServiceImpl;
import com.vmw.bora.vchest.services.ObjServiceImpl;

@Component
@Path("/search")
public class SearchRestService {
	
	@Autowired
	ObjServiceImpl objServiceImpl;
	
	@Autowired
	ObjSearchServiceImpl objSearchServiceImpl;
	
	/*
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/kind/{term}")
	public List<Obj> getByKind(@PathParam("term") String term) {
		System.out.println("search on term: " + term);
		return objSearchServiceImpl.searchByKind(term);
	}
	*/
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/user/{id}/query/{q}")
	public List<Obj> getByAll(@PathParam("id") String id, @PathParam("q") String q) {
		System.out.println("search on q: " + q);
		return objSearchServiceImpl.searchByAllFields(q);
	}
}