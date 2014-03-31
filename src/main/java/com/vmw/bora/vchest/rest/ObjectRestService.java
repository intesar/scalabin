package com.vmw.bora.vchest.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.vmw.bora.vchest.domain.Blob;
import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.services.ObjBlobServiceImpl;
import com.vmw.bora.vchest.services.ObjServiceImpl;
import com.vmw.bora.vchest.services.UsersServiceImpl;

@Component
@Path("/object")
public class ObjectRestService {

	final static String HOME = "home";
	final static String SUCCESS = "success";
	final static String FAILED = "failed";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ObjServiceImpl objServiceImpl;

	@Autowired
	ObjBlobServiceImpl objBlobServiceImpl;

	@Autowired
	UsersServiceImpl usersServiceImpl;

	// curl -i -F name=bookmarks.html -u admin:admin -F file=@bookmarks.html
	// http://localhost:8080/vChest/rest/object
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream is,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("parent") String parent) {

		logger.info("uploading file [{}] user [{}]", fileDetail.getFileName(),
				UserContext.getLoggedInUser());
		String fileName = "test";
		if (fileDetail != null) {
			fileName = fileDetail.getFileName();
		}
		ByteBuffer bb = null;
		try {
			// logger.info("file contents [{}]",
			// IOUtils.toString(IOUtils.toByteArray(is), "UTF-8"));
			bb = ByteBuffer.wrap(IOUtils.toByteArray(is));
			// logger.info("file contents [{}]", IOUtils.toString(bb.array(),
			// "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			// return error
		}

		Obj obj = new Obj();
		obj.setId(UUID.randomUUID().toString());
		obj.setName(fileName);
		if (StringUtils.isBlank(parent)) {
			obj.setParent(HOME);
		} else {
			obj.setParent(parent);
		}
		obj.setModified(new Date());
		obj.setChunkCount(1);
		obj.setKind("file");
		obj.setOwner(UserContext.getLoggedInUser());
		obj.setTenantId(UserContext.getUserTenant());

		objServiceImpl.save(obj);

		Blob blob = new Blob();
		blob.setId(obj.getId());
		blob.setBlob(bb);
		objBlobServiceImpl.save(blob);

		return Response.status(200).entity(obj.getId()).build();

	}

	// http://localhost:8080/vChest/rest/bucket/706fa510-55d6-4cc3-82bd-6642309c3de8
	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		logger.info("delete file [{}]", id);
		objServiceImpl.delete(id);
		objBlobServiceImpl.delete(id);
		return Response.status(200).entity(SUCCESS).build();
	}

	// http://localhost:8080/vChest/rest/object/1b04072f-5e9f-4217-8765-3e80c3fe6007
	// octet-stream
	// authorization
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response get(@PathParam("id") String id) {
		logger.info("downloading file [{}] user [{}]", id,
				UserContext.getLoggedInUser());

		if (!objServiceImpl.find(id)) {
			return Response.status(404).entity(FAILED).build();
		}

		Obj obj = objServiceImpl.getByObjId(id, UserContext.getLoggedInUser(),
				UserContext.getUserTenant());

		Blob blob = objBlobServiceImpl.find(obj.getId());
		ByteBuffer byteBuffer = blob.getBlob();
		byte[] byteArray = BytesUtil.getArray(byteBuffer);

		return Response
				.ok(byteArray, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition",
						"attachment; filename = " + obj.getName()).build();

	}

}
