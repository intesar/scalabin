package com.vmw.bora.vchest.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.vmw.bora.vchest.domain.Blob;
import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.services.ObjService;

@Component
@Path("/object")
public class ObjectRestService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ObjService objService;

	@Autowired
	private ObjService objBlobService;

	// curl -i -F name=bookmarks.html -u admin:admin -F file=@bookmarks.html
	// http://localhost:8080/vChest/rest/object
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream is,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("parent") String parent) {

		logger.info("uploading file [{}] parent [{}] user [{}] tenant [{}]", 
				new Object[] {fileDetail.getFileName(), parent,
				UserContext.getLoggedInUser(), UserContext.getUserTenant()});
		
		String fileName = null;
		if (fileDetail != null) {
			fileName = fileDetail.getFileName();
		}
		ByteBuffer bb = null;
		try {
			bb = ByteBuffer.wrap(IOUtils.toByteArray(is));
		} catch (IOException e) {
			e.printStackTrace();
			// return error
		}

		String id = objBlobService.addObject(fileName, parent, bb);

		return Response.status(200).entity(id).build();

	}

	// http://localhost:8080/vChest/rest/bucket/706fa510-55d6-4cc3-82bd-6642309c3de8
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		logger.info("delete file [{}] user [{}] tenant [{}]", id, UserContext.getLoggedInUser(), UserContext.getUserTenant());

		if (objService.getByObjId(id) == null) {
			return Response.status(404).entity(id).build();
		}

		objService.delete(id);
		objBlobService.delete(id);
		return Response.status(200).entity(id).build();
	}

	// http://localhost:8080/vChest/rest/object/1b04072f-5e9f-4217-8765-3e80c3fe6007
	// octet-stream
	// authorization
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response get(@PathParam("id") String id) {
		logger.info("downloading file [{}] user [{}] tenant [{}]", id,
				UserContext.getLoggedInUser(), UserContext.getUserTenant());

		if (objService.getByObjId(id) == null) {
			return Response.status(404).entity(id).build();
		}

		Obj obj = objService.getByObjId(id);

		Blob blob = objBlobService.findBlob(obj.getId());
		ByteBuffer byteBuffer = blob.getBlob();
		byte[] byteArray = BytesUtil.getArray(byteBuffer);

		return Response
				.ok(byteArray, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition",
						"attachment; filename = " + obj.getName()).build();

	}

}
