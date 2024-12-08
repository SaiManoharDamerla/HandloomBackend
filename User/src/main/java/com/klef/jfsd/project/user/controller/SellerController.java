package com.klef.jfsd.project.user.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.klef.jfsd.project.user.model.Product;
import com.klef.jfsd.project.user.model.Seller;
import com.klef.jfsd.project.user.model.User;
import com.klef.jfsd.project.user.service.SellerService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/seller")
public class SellerController {
	
	@Autowired
	private SellerService sellerService;
	
	@PostMapping("add")
	public ResponseEntity<?> addseller(@RequestBody Seller seller){
		String message = sellerService.addSeller(seller);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}
	
	@PostMapping("/profile/{sellerId}")
    public ResponseEntity<String> updateSellerProfile(@PathVariable int sellerId, @RequestBody Seller updatedProfile) {
        String message = sellerService.updateSellerProfile(sellerId, updatedProfile);
        if (message.equals("User profile updated successfully!")) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.badRequest().body(message); // 400 Bad Request for failure
    }

    // Endpoint to get user profile
    @GetMapping("/profile/{sellerId}")
    public ResponseEntity<Seller> getSellerProfile(@PathVariable int sellerId) {
        Seller seller = sellerService.getSellerProfile(sellerId);
        if (seller == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found if user doesn't exist
        }
        return ResponseEntity.ok(seller);
    }
    
    
    @PostMapping("/addproduct")
    public ResponseEntity<?> addProduct(@RequestParam String  productName,
    									@RequestParam String  description,
    									@RequestParam double  price,
    									@RequestParam int  stockQuantity,
    									@RequestParam String categoryName,
    									@RequestParam int sellerId,
    									@RequestParam MultipartFile image) throws IOException{
    	
    	Product product = new Product();
    	product.setProductName(productName);
    	product.setDescription(description);
    	product.setPrice(price);
    	product.setCategoryName(categoryName);
    	product.setStockQuantity(stockQuantity);
    	product.setSeller(sellerService.getSellerProfile(sellerId));
    	
    	String data=null;
    	if(!image.isEmpty()) {
    		String fileType = image.getContentType();
    		String fileData = Base64.getEncoder().encodeToString(image.getBytes());
        	data = "data:"+fileType +";base64,"+fileData;

    	}
    	product.setImage(data);
    	
    	Product savedProduct = sellerService.addProduct(product);
    	return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
    
    @PostMapping("/updateproduct")
    public String updateProduct(@RequestBody Product product) {
    	sellerService.updateProduct(product);
        return "Product Updated SucessFully";
    }
    
    @GetMapping("/viewproducts")
    public ResponseEntity<?> viewAllProducts(@RequestParam int sellerId) {
    		System.out.println(sellerId);
    	List<Product> products = sellerService.viewAllProducts(sellerId);
    	
    	if(products.isEmpty())
    		return ResponseEntity.noContent().build();
    	
    	return ResponseEntity.ok(products);
    }
    

}
