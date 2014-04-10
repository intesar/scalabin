package com.vmw.bora.vchest.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Authority;
import com.vmw.bora.vchest.repo.cassandra.AuthorityRepo;
import com.vmw.bora.vchest.repo.solr.AuthoritySolrRepo;
import com.vmw.bora.vchest.services.AuthorityService;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorityRepo authorityRepo;

	@Autowired
	private AuthoritySolrRepo authoritySolrRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vmw.bora.vchest.services.impl.AuthorityService#save(com.vmw.bora.
	 * vchest.domain.Authority)
	 */
	@Override
	public void save(Authority authority) {
		// if (authorityRepo.findOne(authority.getUserName()) !=null) {
		// logger.error("Authority with that username exists: " +
		// authority.getUserName());
		// throw new RuntimeException();
		// }
		// we don't need this, b/c of a bug
		// authorityRepo.save(authority);
		// authoritySolrRepo.save(authority);
	}
}
