package com.vmw.bora.vchest.services;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vmw.bora.vchest.domain.Blob;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class ObjBlobServiceImplTest {

	@Autowired()
	ObjBlobServiceImpl objBlobService;

	@Test(expected = RuntimeException.class)
	public void testSaveObjBlob() throws IOException {
		Blob objBlob = new Blob();
		ByteBuffer bb = ByteBuffer.wrap(IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream("log4j.properties")));
		objBlob.setBlob(bb);
		objBlob.setId("123");
		objBlobService.save(objBlob);
		objBlobService.save(objBlob);
	}

	@Test
	public void testSaveObjBlob2() throws IOException {
		Blob objBlob = new Blob();
		ByteBuffer bb = ByteBuffer.wrap(IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream("log4j.properties")));
		objBlob.setBlob(bb);
		objBlob.setId("124");
		objBlobService.save(objBlob);
	}
}