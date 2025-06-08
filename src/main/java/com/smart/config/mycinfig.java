package com.smart.config;

import org.springframework.context.annotation.Bean;



import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;



@Configuration
@EnableWebSecurity

public class mycinfig   {
	
	@Bean
	public UserDetailsService getUserDetailService() {
		return new  userdetailimplements();
		
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	
//	password encoder
	@Bean
	public DaoAuthenticationProvider getauthenticationProvider() {
		DaoAuthenticationProvider daoau = new DaoAuthenticationProvider();
		daoau.setUserDetailsService(this.getUserDetailService());
		daoau.setPasswordEncoder(passwordEncoder());
		
		return daoau;
	}
//////  configure method
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.authenticationProvider(getauthenticationProvider());
//	}
//	configure method
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
		return configuration.getAuthenticationManager();
		
		
	}
	
//	 protected void configure(HttpSecurity http) throws Exception {
//	        http
//	            .authorizeRequests()
//	                .antMatchers("/admin/**").hasRole("ADMIN")
//	                .antMatchers("/user/**").hasRole("USER")
//	                .antMatchers("/**").permitAll()
//	                .and()
//	            .formLogin()
//	                .and()
//	            .csrf().disable();
//	    }
	@Bean
	  public SecurityFilterChain scjg(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> {
            	authorize.requestMatchers("/user/**").hasRole("USER");
                authorize.requestMatchers("/admin/**").hasRole("ADMIN");
                authorize.requestMatchers("/**").permitAll();
                authorize.anyRequest().permitAll();
            });
           
           http.formLogin(fromLogin -> {
        	   fromLogin.loginPage("/signin")
        	   .loginProcessingUrl("/dologin")
        	  .defaultSuccessUrl("/user/index");
        	   // import  login fail page 
//        	   .failureUrl("/loginfail");
           });
      
      

        return http.build();
    }

}
