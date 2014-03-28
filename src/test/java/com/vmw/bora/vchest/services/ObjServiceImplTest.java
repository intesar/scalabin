package com.vmw.bora.vchest.services;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vmw.bora.vchest.domain.Obj;
import com.vmw.bora.vchest.domain.Users;
import com.vmw.bora.vchest.repo.solr.ObjSolrRepo;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class ObjServiceImplTest {

	@Autowired()
	ObjServiceImpl objService;

	@Autowired()
	ObjSolrRepo repo;

	@Test
	public void testAll() {
		for (Obj o : repo.findAll()) {
			System.out.println("o : " + o);
		}
	}
	
	
	@Test
	@Ignore
	public void testSaveObj() throws IOException {
		Obj obj = new Obj();
		obj.setBucketName("FooBucket1");
		obj.setChunkCount("Foo101");
		obj.setDateModified("FooDate1");
		obj.setGroup("FooGroup1");
		obj.setId("FooId1");
		obj.setKind("FooKind1");
		
		objService.save(obj);
	}

	@Test(expected = RuntimeException.class)
	@Ignore
	public void testDeleteObj() {
		Obj obj = new Obj();
		obj.setBucketName("FooBucket");
		obj.setChunkCount("Foo10");
		obj.setDateModified("FooDate");
		obj.setGroup("FooGroup");
		obj.setId("FooId");
		obj.setKind("FooKind");
		
		objService.save(obj);
		objService.delete(obj.getId());

		objService.delete(obj.getId());
	}
}
