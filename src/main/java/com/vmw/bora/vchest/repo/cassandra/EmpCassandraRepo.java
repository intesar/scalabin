/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vmw.bora.vchest.repo.cassandra;

import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Emp;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author mdshannan
 */
@Repository
public interface EmpCassandraRepo extends TypedIdCassandraRepository<Emp, String> {

	//@Query("SELECT * FROM emp e WHERE e.username = ?1")
    //List<Emp> findAllByUsername(String username);
}
