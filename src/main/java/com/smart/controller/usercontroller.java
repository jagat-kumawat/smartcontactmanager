package com.smart.controller;

import java.io.*;



import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.data.domain.Page;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart.dao.contactrepository;
import com.smart.dao.userrepository;
import com.smart.entities.contact;
import com.smart.entities.user;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.razorpay.*;

@Controller
@RequestMapping("/user")
@AllArgsConstructor

public class usercontroller {
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

//	dashboard home

	@RequestMapping("/index")

//	get the user using username(Email)	
	public String dashboard(Model m, Principal per) {

		m.addAttribute("title", "user dashboard");

		return "normaluser/user_dashboard";

	}

//	open add form handler
//	public String
	@GetMapping("/add-contect")
	public String profile(Model m) {
		m.addAttribute("title", "addcontect");
		m.addAttribute("contact", new contact());
		return "normaluser/add_contect";
	}
//	 processing add contact form

	@PostMapping("/process-contact")

	public String processcontact(@ModelAttribute contact cont, @RequestParam("profileImage") MultipartFile file,
			Principal per, HttpSession session) {

		try {
			String name = per.getName();

			user u = this.abc.getuserBy(name);

//processing and uploading file
			if (file.isEmpty()) {
//		if the file is empty then try our message
				System.out.println("file not uploaded");
				cont.setImage("contact.png");

			} else {
//		file the file to folder and update the name to contact
				cont.setImage(file.getOriginalFilename());
				File savef = new ClassPathResource("static/image").getFile();
				Path p = Paths.get(savef.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), p, StandardCopyOption.REPLACE_EXISTING);
				System.out.println("image is uploaded");

			}

			u.getContact().add(cont);
			cont.setU(u);

			this.abc.save(u);

			System.out.println("data" + cont);
			System.out.println("Added to bata base");

			session.setAttribute("message", new Message("you contact is added!! add more", "success"));
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new Message("some went wrong !! try again", "danger"));

		}

		return "normaluser/add_contect";
	}

//	show contact
	@GetMapping("/show-contact/{page}")
	public String showcontacts(@PathVariable("page") Integer pag, Model m, Principal p) {

//		contact ki list ko send krna h 
//		String userName = p.getName();
//		user u = this.abc.getuserBy(userName);
//		List<contact> c = u.getContact(); 
//		pagerequest ,pageable ki child class h
		Pageable pageable = PageRequest.of(pag, 5);

		String userName = p.getName();

		user u = this.abc.getuserBy(userName);
		Page<contact> conts = this.co.findContactById(u.getId(), pageable);

//		add contact
		m.addAttribute("contacts", conts);
//	hm kis page pr h (current page)
		m.addAttribute("currentpage", pag);
//		 total page
		m.addAttribute("totalpages", conts.getTotalPages());

		return "normaluser/show_contact";

	}
	

//	showing particular contact details
	@RequestMapping("/{cid}/contacts")
	public String showcontact(@PathVariable("cid") Integer cid, Model m, Principal pe) {
		System.out.println("CID" + cid);
		Optional<contact> contactOptional = this.co.findById(cid);
		contact contact1 = contactOptional.get();
		String uname = pe.getName();
		user u = this.abc.getuserBy(uname);

		if (u.getId() == contact1.getU().getId()) {
			m.addAttribute("model", contact1);
			m.addAttribute("title", contact1.getName());
		}

		return "normaluser/contact_detail";
	}

//	delete contact handler

	@GetMapping("/delete/{cid}")
	public String deletecontact(@PathVariable("cid") Integer cid, Model m, Principal per, HttpSession session) {

		contact c1 = this.co.findById(cid).get();
//			System.out.println("contact" + c1.getCid());
//			c1.setU(null);
//			this.co.delete(c1);
		user u = this.abc.getuserBy(per.getName());
		u.getContact().remove(c1);

		this.abc.save(u);

//			user u = this.abc.findById(cid).get();

//			u.getContact().remove(c1);
//			this.abc.save(u);

//			u.getContact().removeIf(c -> c.getCid()==c1.getCid());

//			this.abc.save(u);
//			this.co.delete(c1);
		try {
			File deleteimage = null;
			deleteimage = new ClassPathResource("static/image").getFile();
			File f2 = new File(deleteimage, c1.getImage());
			f2.delete();

			
			c1.setImage(null);
			this.co.delete(c1);

			session.setAttribute("message", new Message("deleted success", "success"));
		} catch (Exception e) {

			session.setAttribute("message", new Message("some went wrong !! try again", "danger"));

		}

		return "redirect:/user/show-contact/0";

	}

// open update form handler
	@PostMapping("/update-contact/{cid}")
	public String updatecontact(@PathVariable("cid") Integer cid, Model m) {
		m.addAttribute("title", "update-contact");
		contact c2 = this.co.findById(cid).get();
		m.addAttribute("modelco", c2);

		return "normaluser/update_form";
	}

//	update contact handler

	@PostMapping("/process-update")

	public String updatecontact(@ModelAttribute contact conts, @RequestParam("profileImage") MultipartFile file,
			Principal per, HttpSession session) {
		try {

//			old contact details
			contact oldconts = this.co.findById(conts.getCid()).get();

			if (!file.isEmpty()) {

//				delete old photo

				File deleteimage = new ClassPathResource("static/image").getFile();
				File f2 = new File(deleteimage, oldconts.getImage());
				f2.delete();

//				update photo
				File savef = new ClassPathResource("static/image").getFile();
				Path p = Paths.get(savef.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), p, StandardCopyOption.REPLACE_EXISTING);
				conts.setImage(file.getOriginalFilename());

			} else {

				conts.setImage(oldconts.getImage());

			}

			user u = this.abc.getuserBy(per.getName());
			conts.setU(u);
			this.co.save(conts);
			session.setAttribute("message", new Message("your contact is updated", "success"));

		} catch (Exception e) {

			e.printStackTrace();

		}

		return "redirect:/user/" + conts.getCid() + "/contacts";
	}

//your profile handler
	
	@GetMapping("/profiles")
	public String yourprofile(Model m, Principal per) {
		m.addAttribute("title", "your profile");
		user u = this.abc.getuserBy(per.getName());
		m.addAttribute("u", u);
		u.setImageurl("userlogo1.png");
		
		return "normaluser/profile";
		

	}
//	@GetMapping("/profiles")
//	public String viewmyprofile(Model m , HttpServletRequest re, Principal per) {
//		
//		
//		m.addAttribute("title","myprofile");
//		user u = this.abc.getuserBy(per.getName());
//		m.addAttribute("u", u);
//	u.setImageurl("userlogo1.png");
//		m.addAttribute("servletPath",re.getServletPath());
//		return "normaluser/profile";
//		
//	}
//	@RequestMapping("/profiles")
//	public String showprofile(Model m,Principal per) {
//		
//	
//		user u =  new user();
//		String uname = per.getName();
//		u.setImageurl("userlogo1.png");
//		
//		m.addAttribute("u", u);
//		m.addAttribute("title", u.getName());
//		
//		return "normaluser/profile";
//	}
//	
	

	@PostMapping("/delete/{id}")
	public String deleteuser(@PathVariable("id") Integer userid,Model m ,HttpSession session,Principal per,HttpServletRequest re,HttpServletResponse res) {
			
//		String email = per.getName();
//		user u = this.abc.getuserBy(email);
//		
//		
//		if(u==null||(u.getId()!=userid)) {
//			
//			session.setAttribute("message", new Message("deleted success", "success"));
//		
//		
//		return"redirect:/user/show-contact/0";
//		
//	}
//		
//	
//		abc.deleteById(userid);
//		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
//		if(auth!=null) {
		
	
		user u = this.abc.findById(userid).get();
		
		u.setEmail(null);
		this.abc.delete(u);
		this.abc.save(u);
		
			
			try {
			File deleteimg = null;
			deleteimg  = new ClassPathResource("static/image").getFile();
			File f2 = new File(deleteimg,u.getImageurl());
			f2.delete();
			
			u.setImageurl(null);
			this.abc.delete(u);
			
			session.setAttribute("message", new Message("deleted success", "success"));
//			new SecurityContextLogoutHandler().logout(re, res, auth);
			
//			}
		
		
		
		
	}catch(Exception e) {
		
		e.printStackTrace();
		session.setAttribute("message", new Message("some went wrong !! try again", "danger"));
		
		
	}
	return "redirect:/signin?logout";
			
	

	}
	
//	creating order for payment
	@PostMapping("/create_order")
	@ResponseBody
	public String createorder(@RequestBody Map<String,Object>data) throws Exception {
//	System.out.println("hey order function ex.");
		System.out.println(data);
		int amt=Integer.parseInt(data.get("amount").toString());
		
				
			var client =	new RazorpayClient("860870ea00b2d6d1f37d813df701803f22718e268df115fe727d748c56d842e1","67ca1dcaba96ccc62b440a047f1118360d9b0ae4b301f11f60a522e67b5b10a2");
				
				JSONObject ob = new JSONObject(); 
				ob.put("amount", amt*100);
				ob.put("currency", "INR");
				ob.put("receipt", "txn_343432");
//				creating new order
				 Order ore =   client.Orders.create(ob);
				 System.out.println(ore);
				
				
		return "done";
		
	}
	
}
