package com.vmw.bora.vchest.services.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vmw.bora.vchest.domain.Users;
import com.vmw.bora.vchest.repo.cassandra.UsersCassandraRepo;
import com.vmw.bora.vchest.repo.solr.UsersSolrRepo;
import com.vmw.bora.vchest.rest.UserContext;
import com.vmw.bora.vchest.services.ObjService;
import com.vmw.bora.vchest.services.UsersService;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UsersCassandraRepo usersCassandraRepo;

	@Autowired
	private UsersSolrRepo usersSolrRepo;

	@Autowired
	private ObjService objService;

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.UsersService#addUserAndTenant(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Users addUserAndTenant(String username, String password,
			String tenant) {

		Users user = new Users();

		user.setId(UUID.randomUUID().toString());
		user.setEnabled(true);

		user.setUsername(username);
		user.setPassword(password);
		user.setTenantId(tenant);

		save(user);

		return user;

	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.UsersService#addUserToTenant(java.lang.String, java.lang.String)
	 */
	@Override
	public Users addUserToTenant(String username, String password) {
		Users user = new Users();

		String tenant = UserContext.getUserTenant();
		if (usersSolrRepo.findByUsernameAndTenantId(username, tenant) != null) {
			logger.error("User Name exists: [{}]", username);
			throw new RuntimeException("Username exists");
		}

		user.setId(UUID.randomUUID().toString());
		user.setEnabled(true);

		user.setUsername(username);
		user.setPassword(password);
		user.setTenantId(tenant);

		save(user);

		return user;
	}

	private void save(Users users) {
		usersCassandraRepo.save(users);
		usersSolrRepo.save(users);
		// create home folder
		objService.addHomeBucket(users.getUsername(), users.getTenantId());
	}

	/* (non-Javadoc)
	 * @see com.vmw.bora.vchest.services.impl.UsersService#getByUsername(java.lang.String, java.lang.String)
	 */
	@Override
	public Users getByUsername(String username, String tenant) {
		return this.usersSolrRepo.findByUsernameAndTenantId(username, tenant);
	}
}
