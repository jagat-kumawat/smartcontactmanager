package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.contactrepository;
import com.smart.dao.userrepository;
import com.smart.entities.user;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/user")
@AllArgsConstructor

public class settingcontroller {
	@Autowired
public BCryptPasswordEncoder bc;
	@Autowired
	public userrepository abc;
	@Autowired
	public contactrepository co;
//	method for adding common data to response

	@ModelAttribute
	public void addCommonData(Model m, Principal per) {

		String userName = per.getName();
		System.out.print("username" + userName);
		user u = abc.getuserBy(userName);

		System.out.print("user" + u);
		m.addAttribute("user", u);
	}
	
	@GetMapping("/settings")
	public String openSettings() {
		return "normaluser/setting";
		
	}
//	change password...handler
	@PostMapping("/change-password")
	public String changepassword(@RequestParam("oldpassword")String oldpassword, @RequestParam("newpassword")String newpassword,Principal per,HttpSession se) {
	System.out.println("old password "+oldpassword);
	System.out.println("new password "+newpassword);
	
	String username=per.getName();
	user u = this.abc.getuserBy(username);
	System.out.println(u.getPassword());
	if(this.bc.matches(oldpassword,u.getPassword())) {
		
//		change the password
		u.setPassword(this.bc.encode(newpassword));
		this.abc.save(u);
		se.setAttribute("message", new Message("your password changed","success"));
	}else {
//		error
		se.setAttribute("message", new Message("wrong old password","danger")); 
		return "redirect:/user/settings";
				
	}
	
		
	return "redirect:/user/index";
		
	}
	}