package com.vmw.bora.vchest.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Users;
import com.vmw.bora.vchest.repo.cassandra.UsersCassandraRepo;
import com.vmw.bora.vchest.repo.solr.UsersSolrRepo;

@Service
@Transactional
public class UsersServiceImpl {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UsersCassandraRepo usersCassandraRepo;

	@Autowired
	private UsersSolrRepo usersSolrRepo;

	public void save(Users users) {
		if (usersCassandraRepo.findOne(users.getUserName())!=null) {
			logger.error("User Name exists: " + users.getUserName());
			throw new RuntimeException();
		}
		usersCassandraRepo.save(users);
		usersSolrRepo.save(users);
	}
}
