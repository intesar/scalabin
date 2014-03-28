package com.vmw.bora.vchest.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Authority;
import com.vmw.bora.vchest.repo.cassandra.AuthorityRepo;
import com.vmw.bora.vchest.repo.solr.AuthoritySolrRepo;

@Service
@Transactional
public class AuthorityServiceImpl {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private AuthorityRepo authorityRepo;

	@Autowired
	private AuthoritySolrRepo authoritySolrRepo;

	public void save(Authority authority) {
		//if (authorityRepo.findOne(authority.getUserName()) !=null) {
		//	logger.error("Authority with that username exists: " + authority.getUserName());
		//	throw new RuntimeException();
		//}
		// we don't need this, b/c of a bug
		//authorityRepo.save(authority);
		//authoritySolrRepo.save(authority);
	}
}
