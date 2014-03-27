/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vmw.bora.vchest.repo.cassandra;

import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;
import org.springframework.stereotype.Repository;

import com.vmw.bora.vchest.domain.Users;

@Repository
public interface UsersCassandraRepo extends TypedIdCassandraRepository<Users, String> {

}
