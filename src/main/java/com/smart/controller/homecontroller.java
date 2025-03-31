//-------------------//-/-------------------------------
//    userrepository ka use  krne pr jb hm data ko mysql me transfer krate h tb 
//package com.smart.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.smart.dao.userrepository;
//import com.smart.entities.user;
//
//@Controller
//public class homecontroller {
//@Autowired
//	private userrepository abc;
//	
//	@GetMapping("/open")
//	@ResponseBody
//	public String test() {
//		user a = new user();
//		a.setName("jatin");
//		
//		a.setEmail("jatin@gmail.com");
//		abc.save(a);
//		return"working";
//		
//	
//	}
//}
//------------------------------//-------------//-----------------//------/---------------//----------------//--------------//----------------------

//use for html in chorme use
package com.smart.controller;



import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.smart.dao.userrepository;
import com.smart.entities.user;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class homecontroller {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private userrepository abc;
		
	
	@GetMapping("/")
	public String  hello(Model m ) {
		
		m.addAttribute("title","home- smartcontact manager");
		
		return "home";
	}
	@RequestMapping("/about")
	public String about() {
		return "about";
	
	}

	
	@GetMapping("/signup")
	public String singup(Model m) {
		m.addAttribute("title","register - smart contact manager");
		m.addAttribute("u",new user());
		
		return "signup";
	}
//	handler for registering for
	@PostMapping("/do_register")
	public String registeruser(@Valid @ModelAttribute("u")user a ,BindingResult re, @RequestParam(value="agreement",defaultValue="false")boolean agreement, 
			Model m,HttpSession session ) {

		try {
			  
			if(!agreement)
			{
				System.out.println("you have not agreed the terms and conditions");
				throw new Exception("you have not agreed the terms and condition");
			}
			
			if(re.hasErrors()) 
			{
				System.out.println("error"+re.toString());
				m.addAttribute("u",a);
				return"signup";
			}

			a.setRole("ROLE_USER");
			a.setEnabled(true);
			a.setImageurl("default.png");
			a.setPassword(passwordEncoder.encode(a.getPassword()));
			
			System.out.println("Agreement="+agreement);
			System.out.println("user"+ a);
			
	 user result = this.abc.save(a);
			
//			m.addAttribute("u",new user());	
			
			session.setAttribute("message",new Message("successfully registered","alert-success"));
			return "signup";
			
		}catch(Exception e) {
			e.printStackTrace();
			m.addAttribute("u",a);
			session.setAttribute("message",new Message("something went wrong"+e.getMessage(),"alert-danger"));
			
			
			return "signup";
			
		}
		
		
	
		
		
	}
//	
	@GetMapping("/signin")
	
	public String costeamlogin(Model m)
	{
		m.addAttribute("title","signin page");
		return "login";
	}
	@RequestMapping("/loginfail")
	public String loginfails() {
		return "loginfail";
	
	}
	@RequestMapping("/dologin")
	public String process() {
		return "dologin";
	
	}
}