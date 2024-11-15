package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.user;

public interface userrepository extends JpaRepository<user,Integer> {
@Query("select u from user u where u.email =  :email")
public user getuserBy(@Param("email")String email);


} 