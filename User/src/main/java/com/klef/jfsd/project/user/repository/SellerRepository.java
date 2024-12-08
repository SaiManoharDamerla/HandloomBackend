package com.klef.jfsd.project.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.project.user.model.Seller;



@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer>{
	
 public Seller findByEmailAndPassword(String email, String password);
 
}
