package com.smart.entities;

import jakarta.persistence.Entity;


import jakarta.persistence.*;
import jakarta.persistence.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.*;

@Entity
@Table(name = "user")
public class user {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int id;
	@NotBlank(message ="name field is required")
	@Size(min=2,max=20,message ="user name must be between 3 to 15 charactors!!")
	private String name;
	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "invalid Email!!")
	private String email;
	
	@NotBlank(message ="please enter the password")

	private String password;
	private String role;
	
	
	private boolean enabled;
	
	private String imageurl;
	@Column(length = 500)
	private String about;
	
@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	 private  List<contact> contacts= new ArrayList<>();
	 
	public List<contact> getContact() {
		return contacts;
	}

	public void setContacts(List<contact> contact) {
		this.contacts = contact;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public user() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean equals(Object obj)
	{
	return this.id==((user)obj).getId();	
	}

	@Override
	public String toString() {
		return "user [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", enabled=" + enabled + ", imageurl=" + imageurl + ", about=" + about + ", contacts=" + contacts
				+ "]";
	}

}
