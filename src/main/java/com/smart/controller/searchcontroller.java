package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.contactrepository;
import com.smart.dao.userrepository;
import com.smart.entities.contact;
import com.smart.entities.user;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class searchcontroller {
	@Autowired
	private userrepository abc;
	@Autowired
	private contactrepository cr;
//search handler
	@GetMapping("/search/{query}")
public ResponseEntity<?> search(@PathVariable("query") String query,Principal per){
		System.out.println(query);
		user u = this.abc.getuserBy(per.getName());
		List<contact> contacts=this.cr.findByNameContainingAndU(query,u);
		
		
		return ResponseEntity.ok(contacts);
		
	
	}
	
}
