package com.klef.jfsd.project.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.project.user.model.Product;
import com.klef.jfsd.project.user.model.Seller;
import com.klef.jfsd.project.user.repository.ProductRepository;
import com.klef.jfsd.project.user.repository.SellerRepository;

@Service
public class SellerServiceImpl implements SellerService{
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Seller checkLogin(String email, String password) {
		System.out.println(email +" "+ password);
		Seller seller = sellerRepository.findByEmailAndPassword(email, password);
		return seller!=null ? seller : null;
	}

	@Override
	public String addSeller(Seller seller) {
		sellerRepository.save(seller);
		return "Seller Added SucessFully";
	}

	@Override
	public String updateSellerProfile(int sellerId, Seller updatedProfile) {
	    Optional<Seller> existingSellerOptional = sellerRepository.findById(sellerId);
	    if (existingSellerOptional.isPresent()) {
	        Seller existingSeller = existingSellerOptional.get();
	        // Update profile fields
	        existingSeller.setStoreName(updatedProfile.getStoreName());
	        existingSeller.setEmail(updatedProfile.getEmail());
	        existingSeller.setGstNo(updatedProfile.getGstNo());

	        sellerRepository.save(existingSeller); // Save updated seller profile
	        return "Seller profile updated successfully!";
	    }
	    return "Seller not found!";
	}

	@Override
	public Seller getSellerProfile(int sellerId) {
	    return sellerRepository
	    		.findById(sellerId).orElse(null); // Fetch seller profile or return null
	}

	@Override
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public List<Product> viewAllProducts(int sellerId) {
		Seller seller = sellerRepository.findById(sellerId).orElse(null);
		System.out.println(seller.getEmail());
		
		List<Product> products = productRepository.findBySeller(seller);
		System.out.println(products.size());
		return products;
	}

	@Override
	public Product updateProduct(Product product) {
		Product p = productRepository.findById(product.getProduct_id()).orElse(null);
		p.setPrice(product.getPrice());
		p.setStockQuantity(product.getStockQuantity());
		
		return productRepository.save(p);
	}

	

}
