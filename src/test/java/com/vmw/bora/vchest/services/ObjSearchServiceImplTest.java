package com.vmw.bora.vchest.services;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vmw.bora.vchest.domain.Obj;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class ObjSearchServiceImplTest {

	@Autowired()
	ObjService objService;

	@Autowired()
	ObjSearchService objSearchService;
	
	@Test
	public void testSearchByKind() {
		
		//objService.deleteAll(); // clear all prev. records
		
		Obj o1 = new Obj();
		o1.setKind("text");
		o1.setId("object1");
		o1.setParent("home");
		o1.setName("arizona");
		objService.save(o1);
		
		List<Obj> result = objSearchService.searchByKind("text");
		assertEquals(1, result.size());
		assertEquals(result.get(0).getId(), "object1");
		
		result = objSearchService.searchByKind("fakeKind");
		assertEquals(0, result.size());
		
		Obj o2 = new Obj();
		o2.setKind("text");
		o2.setId("object2");
		o2.setParent("home");
		o2.setName("california");
		objService.save(o2);
		
		Obj o3 = new Obj();
		o3.setKind("text");
		o3.setId("object3");
		o3.setParent("home");
		o3.setName("arizona");
		objService.save(o3);
		
		Obj o4 = new Obj();
		o4.setKind("png");
		o4.setId("object4");
		o4.setParent("home");
		objService.save(o4);
		
		/*
		 * Test multiple results in response and the alphabetical sorting order as per bucker name 
		 */
		result = objSearchService.searchByKind("text");
		assertEquals(3, result.size());
		assertEquals("object1", result.get(0).getId());
		assertEquals("arizona", result.get(0).getName());
		assertEquals("object3", result.get(1).getId());
		assertEquals("arizona", result.get(1).getName());
		assertEquals("object2", result.get(2).getId());
		assertEquals("california", result.get(2).getName());
		
		result = objSearchService.searchByKind("png");
		assertEquals(1, result.size());
		assertEquals(result.get(0).getId(), "object4");
		
		result = objSearchService.searchByKind("fakeKind");
		assertEquals(0, result.size());
	}

}
