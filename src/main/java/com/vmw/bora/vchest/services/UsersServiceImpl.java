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
		if (usersSolrRepo.findByUsernameAndTenantId(users.getUsername(),
				users.getTenantId()) != null) {
			logger.error("User Name exists: " + users.getUsername());
			throw new RuntimeException();
		}
		usersCassandraRepo.save(users);
		usersSolrRepo.save(users);
	}

	public Users getByUsername(String username) {
		return this.usersSolrRepo.findOne(username);
	}

	public String getTenant(String username) {
		return getByUsername(username).getTenantId();
	}
}
