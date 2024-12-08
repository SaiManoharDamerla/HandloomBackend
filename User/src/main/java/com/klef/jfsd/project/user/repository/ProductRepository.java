package com.klef.jfsd.project.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.project.user.model.Product;
import com.klef.jfsd.project.user.model.Seller;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	
	List<Product> findBySeller(Seller seller);
	List<Product> findByCategoryName(String categoryName);


}
