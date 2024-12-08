package com.klef.jfsd.project.user.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.project.user.model.Cart;
import com.klef.jfsd.project.user.model.User;

import jakarta.transaction.Transactional;

import com.klef.jfsd.project.user.model.Product;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	List<Cart> findByUser(User user);
	
	@Transactional
	@Modifying
	void deleteByUserAndProduct(User user, Product product);

	long countByUser(User user);

	Cart findByUserAndProduct(User user, Product product);

}
