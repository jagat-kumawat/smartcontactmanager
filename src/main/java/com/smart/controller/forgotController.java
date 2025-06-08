package com.smart.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.userrepository;
import com.smart.entities.user;
import com.smart.serviceOTP.emailservice;

import jakarta.servlet.http.HttpSession;

@Controller
public class forgotController {
	Random re = new Random(1000);
	@Autowired
	private emailservice emse;
	@Autowired
	private userrepository abc;
	@Autowired
	private BCryptPasswordEncoder bc;

//	email id form open
	@RequestMapping("/forgot")
	public String foratemail() {

		return "forgot_email";
	}

	@PostMapping("/send-OTP")
	public String sendOTP(@RequestParam("email") String email, HttpSession session) {

		System.out.println("Email" + email);
//		generating otp of 4 digit

		int otp = re.nextInt(99999);
		System.out.println("otp " + otp);
		String subject = "OTP from scm";
		String message = ""

				+ "<div style='border:1px solid #e2e2e2;padding:20px'>"

				+ "<h1>"

				+ "OTP=" + "<b>" + otp + "</n>" + "</h1>" + "</div>";

		String to = email;
//otp and email save in session for some time
		boolean flag = this.emse.sendEmail(subject, message, to);

		if (flag) {
			session.setAttribute("myOTP", otp);
			session.setAttribute("email", email);
			return "varify_OTP";

		} else {
			session.setAttribute("message", "check your email id");

			return "forgot_email";
		}
	}

//	verify otp
	@PostMapping("/verify-otp")
	public String verifyotp(@RequestParam("otp") int ootp, HttpSession session) {

		int myotp = (int) session.getAttribute("myOTP");
		String email = (String) session.getAttribute("email");
		if (myotp == ootp) {

//			password change form 
			user u = this.abc.getuserBy(email);
			if (u == null) {

//		send error message

				session.setAttribute("message", "user does not exits with this email!!");
				return "forgot_email";

			} else {
//				send change password form 

			}

			return "password_change";

		} else {
			// Inside your controller method
			session.setAttribute("message", "Sorry, try again!!"); // Set message in session
			return "varify_OTP"; // Redirect ensures session message is only shown once
}
	}
	@PostMapping("/change-password")
	public String changepassword(@RequestParam("newpassword")String newpassword,HttpSession session) {
		
	String email= (String)session.getAttribute("email");
	user u = this.abc.getuserBy(email);
	u.setPassword(this.bc.encode(newpassword));
	this.abc.save(u);
	return "redirect:/signin?change=password changed successfully...";
		
		
		
	}

}
