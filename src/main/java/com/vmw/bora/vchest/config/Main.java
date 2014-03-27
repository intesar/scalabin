/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vmw.bora.vchest.config;

import com.vmw.bora.vchest.domain.Emp;
import com.vmw.bora.vchest.services.EmployeeCassandraServiceImpl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author mdshannan
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "META-INF/spring/applicationContext*.xml");

        EmployeeCassandraServiceImpl service = context
                .getBean(EmployeeCassandraServiceImpl.class);

        Emp emp = new Emp();
        emp.setId((new Date()).toString());
        String username = "IM-" + new Date();
        emp.setUsername(username);
        emp.setJoinDate(new Date());
        emp.setStorageSize(10.0);
        emp.setContent(ByteBuffer.wrap(IOUtils.toByteArray(Main.class.getClassLoader().getResourceAsStream("log4j.properties"))));

        service.saveEmployee(emp);
        int count = 0;
        for (Emp e : service.findAllEmployees()) {
            System.out.println(e + String.valueOf(e.getContent()));
            System.out.println(new String(e.getContent().array()));
            count++;
        }

        System.out.println("done " + count);

        System.out.println(service.findByUsername(username));

    }
}
