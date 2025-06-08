package com.smart.dao;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.smart.entities.contact;
import com.smart.entities.user;
@EnableJpaRepositories
public interface contactrepository extends JpaRepository<contact , Integer> {
//	pegination method ko implement krna h  	
	@Query("SELECT c FROM contact c WHERE c.u.id =:u_id")
	Page<contact> findContactById(@Param("u_id") int u_id, Pageable pe);
//@Modifying(clearAutomatically= true,flushAutomatically = true)
//@Query(value = "DELETE FROM c WHERE c.u.id =cid",nativeQuery = true)
//void deleteById(Integer  cid);



//search 
 public List<contact>findByNameContainingAndU(String name,user u);


	

}
