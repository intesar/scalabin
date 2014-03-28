package com.vmw.bora.vchest.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.vmw.bora.vchest.domain.Users;
import com.vmw.bora.vchest.repo.solr.UsersSolrRepo;

@Component
@Qualifier(value="userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersSolrRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		String tokens[] = username.split("@");
		Users u = this.repo.findByUserNameAndTenantId(tokens[0], tokens[1]);

		if (u == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetails details = new User(u.getUserName(),
				u.getPassword(), true, true, true, true,
				Collections.singleton(new GrantedAuthorityImpl("ROLE_USER")));
		return details;
	}

}
