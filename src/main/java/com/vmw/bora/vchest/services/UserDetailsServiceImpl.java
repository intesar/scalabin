package com.vmw.bora.vchest.services;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Users;
import com.vmw.bora.vchest.repo.solr.UsersSolrRepo;

@Component
@Qualifier(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UsersSolrRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.info("trying to load user [{}] for authentication", username);

		String tokens[] = username.split("@");
		if (tokens.length != 2) {
			logger.error(
					"invalid username [{}], valid username should have username@tenant",
					username);
			throw new UsernameNotFoundException(username);
		}

		Users u = this.repo.findByUsernameAndTenantId(tokens[0], tokens[1]);

		if (u == null) {
			logger.error("unable to find username [{}] tenant [{}]", tokens[0],
					tokens[1]);
			throw new UsernameNotFoundException(username);
		}

		logger.info(
				"found user [{}] and tenant [{}] returning details for authentication",
				tokens[0], tokens[1]);

		UserDetails details = new User(u.getUsername(), u.getPassword(), true,
				true, true, true,
				Collections.singleton(new GrantedAuthorityImpl("ROLE_USER")));
		return details;
	}

}
