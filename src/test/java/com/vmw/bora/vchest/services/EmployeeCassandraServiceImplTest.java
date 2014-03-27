package com.vmw.bora.vchest.services;


import com.vmw.bora.vchest.domain.Emp;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
public class EmployeeCassandraServiceImplTest {

	@Test
	public void testMarkerMethod() {
	}

	@Autowired()
	@Qualifier(value = "cassandraService")
	EmployeeCassandraServiceImpl employeeService;

	@Test
	//@Rollback(true)
	//@Ignore
	public void testSaveEmployee() throws IOException {
		//for (Emp e : employeeService.findAllEmployees()) {
		//	System.out.println (" Deleting : " + e.toString());
		//	employeeService.deleteEmployee(e);
		//}
		
		String id = UUID.randomUUID().toString();
		String name = "Borathon " + new Date();
		Emp obj = new Emp();
		obj.setUsername(name);
		obj.setJoinDate(new Date());
		obj.setStorageSize(10.0);
		obj.setId(id);
		
		ByteBuffer bb = ByteBuffer.wrap(IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream("log4j.properties")));
		//ByteBuffer buffer = ByteBufferUtil.bytes("Hello world");
		obj.setContent(bb);
		//obj.setImg(IOUtils.toByteArray("Hello World"));
		Assert.assertNotNull(
				"Data on demand for 'Employee' failed to provide a new transient entity",
				obj);
		employeeService.saveEmployee(obj);
		Assert.assertNotNull(
				"Expected 'Employee' identifier to no longer be null",
				obj.getId());
		//Assert.assertFalse(employeeService.findAllEmployees().isEmpty());

		Emp e = employeeService.findById(id);
		System.out.println(" find by Id : " + e );
                System.out.println(new String(e.getContent().array()));
		
		
		//System.out.println(ByteBufferUtil.string(e.getContent()));
		
		//for (Emp e1 : employeeService.findByUsername(name)) {
		//	System.out.println(" find by username : " + e1);
		//}
		//employeeService.deleteAll();
		
	}
	
	@Test
	@Ignore
	public void testSaveAllEmployee() {
		String username = UUID.randomUUID().toString();
		List<Emp> list = new ArrayList<>();
		int count = 1;
		for (int i = 2000; i < 1000000000; i++) {
			Emp obj = new Emp();
			obj.setUsername(username);
			obj.setId(UUID.randomUUID().toString());
			list.add(obj);
			if ( i % 3000 == 0 ) {
				try {
					employeeService.saveEmployee(list);
					list.clear();
				} catch (Exception ex) {
					ex.printStackTrace();
					list.clear();
				}
				if ( i % 1000000 == 0 ) {
					System.out.println("Pushed " + count++ + " " + i + " " + new Date());
				}
			}
		}
	}
	

}
