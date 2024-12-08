package com.klef.jfsd.project.user.service;

import java.util.List;

import com.klef.jfsd.project.user.model.Product;
import com.klef.jfsd.project.user.model.Seller;

public interface SellerService {
	public Seller checkLogin(String username, String password);
	public String addSeller(Seller seller);
	public Seller getSellerProfile(int sellerId);
	public String updateSellerProfile(int sellerId, Seller updatedProfile);
	
	
	public Product addProduct(Product product);
	
	public List<Product> viewAllProducts(int sellerId);
	
	public Product updateProduct(Product product);
	


}
