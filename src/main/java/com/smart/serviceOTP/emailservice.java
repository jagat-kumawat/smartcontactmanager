package com.smart.serviceOTP;

import java.io.File;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class emailservice {
	public boolean sendEmail(String subject,String message,String to) {
		
		boolean  f = false;
		String from = "jagatteharpuria@gmail.com";
		
//		variable for gmail
		String host = "smtp.gmail.com";
//		get the system properties
		Properties per = System.getProperties();
		System.out.println("properties" + per);
//	setting important information to properties object
//	host set
		per.put("mail.smtp.host", host);
		per.put("mail.smtp.port", "465");

		per.put("mail.smtp.ssl.enable", "true");
		per.put("mail.smtp.auth", "true");
//	step 1: to get the session object...
		Session session = Session.getInstance(per, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("jagatteharpuria@gmail.com","dgev awzc hxdl ajzf ");
			}

		});
		session.setDebug(true);

//	 step 2: compose the message [text,multi media]
		MimeMessage mem = new MimeMessage(session);
		try {
//		 from email
			mem.setFrom(from);
			
//	 adding  recippient to message 
			mem.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

//		 adding subject to message
			mem.setSubject(subject);
			
			
//////		 adding test to message
////	*		mem.setText(message);
//			
//			
////			attachement........
////			jb message me file ya image etc. send krni ho tb 
//			 String path = "C:\\Users\\Expert\\Downloads\\image\\1.jfif";
//			 File file = new File(path);
//			 
//			 MimeMultipart mimlti = new MimeMultipart();
//			 
////			 text
//			 MimeBodyPart textmime = new MimeBodyPart();
//			 
//			 
////			 file
//			 MimeBodyPart filemime = new MimeBodyPart();
//			 
//			 try {
//				 textmime.setText(message);
//				 
//				 filemime.attachFile(file);
//				 
//				 mimlti.addBodyPart(textmime);
//				 mimlti.addBodyPart(filemime);
//				 
//			 }catch (Exception e) {
//				// TODO: handle exception
//				 e.printStackTrace();
//			}
			 
//			 mem.setContent(mimlti);
//			
			
			
			mem.setContent(message,"text/html");
			
//			send 
//			step 3: send the message using transport class
			 Transport.send(mem);
			 System.out.println("sent success.................");
			 f = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	

}
