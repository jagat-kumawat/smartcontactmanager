package com.smart.entities;

import jakarta.persistence.Entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact")
public class contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	@NotBlank(message ="name field is required")
	@Size(min=2,max=20,message ="user name must be between 3 to 15 charactors!!")
	private String name;
	private String nickname;
	private String work;

	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "invalid Email!!")
	private String email;
	private String phone;
	private String image;
	
@Column(length = 5000)
	private String description;
@ManyToOne
@JsonIgnore
private user u;


public user getU() {
	return u;
}

public void setU(user u) {
	this.u = u;
}

public contact() {
	super();
	// TODO Auto-generated constructor stub
}

// gettter and setters
public int getCid() {
	return cid;
}
public void setCid(int cid) {
	this.cid = cid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getNickname() {
	return nickname;
}
public void setNickname(String nickname) {
	this.nickname = nickname;
}
public String getWork() {
	return work;
}
public void setWork(String work) {
	this.work = work;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
@Override
public boolean equals(Object obj)
{
return this.cid==((contact)obj).getCid();	
}

}




