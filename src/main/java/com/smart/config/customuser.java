
 package com.smart.config;

 import java.util.Collection;
 import java.util.List;

 import org.springframework.security.core.GrantedAuthority;
 import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.security.core.userdetails.UserDetails;

 import com.smart.entities.user;

  public class customuser  implements UserDetails{


 private user u;
 	public customuser(user u) {
 	super();
 	this.u = u;
 }

 	@Override
 	public Collection<? extends GrantedAuthority> getAuthorities() {
 	SimpleGrantedAuthority simabc = new SimpleGrantedAuthority(u.getRole());
 	return List.of(simabc);
 	}

 	@Override
 	public String getPassword() {
 		
 		return u.getPassword();
 	}

 	@Override
 	public String getUsername() {
 		
 		return u.getEmail();
 	}

 	@Override
 	public boolean isAccountNonExpired() {
 		// TODO Auto-generated method stub
 		return true;
 	}

 	@Override
 	public boolean isAccountNonLocked() {
 		// TODO Auto-generated method stub
 		return true;
 	}


 	@Override
 	public boolean isEnabled() {
 		// TODO Auto-generated method stub
 		return true;
 	}

 }
