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
		System.out.println(username);
		String tokens[] = username.split("@");
		//for (Users u1 : repo.findAll()) {
		//	System.out.println(u1);
		//}
		Users u = this.repo.findByUserNameAndTenantId(tokens[0], tokens[1]);

		if (u == null) {
			throw new UsernameNotFoundException(username);
		}
		System.out.println(u);
		UserDetails details = new User(u.getUserName(),
				u.getPassword(), true, true, true, true,
				Collections.singleton(new GrantedAuthorityImpl("ROLE_USER")));
		return details;
	}

}
