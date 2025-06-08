package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.userrepository;
import com.smart.entities.user;

public class userdetailimplements  implements UserDetailsService{
	
@Autowired
private userrepository abc;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		user  u = abc.getuserBy(username);
		if(u==null) {
			throw new UsernameNotFoundException("could not found user");
		}
		customuser cu= new customuser(u);
		
		return cu;
	}
}
