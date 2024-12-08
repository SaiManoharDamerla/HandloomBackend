package com.klef.jfsd.project.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.project.user.model.Admin;
import com.klef.jfsd.project.user.model.Cart;
import com.klef.jfsd.project.user.model.Order;
import com.klef.jfsd.project.user.model.Product;
import com.klef.jfsd.project.user.model.Seller;
import com.klef.jfsd.project.user.model.User;
import com.klef.jfsd.project.user.repository.OrderRepository;
import com.klef.jfsd.project.user.repository.UserRepository;
import com.klef.jfsd.project.user.service.AdminService;
import com.klef.jfsd.project.user.service.MailSenderService;
//import com.klef.jfsd.project.user.service.CaptchaService;
import com.klef.jfsd.project.user.service.SellerService;
import com.klef.jfsd.project.user.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private SellerService sellerService;
    
    @Autowired
    private UserRepository  userRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private MailSenderService mailSenderService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String message = userService.register(user);
        return ResponseEntity.status(201).body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.checkLogin(username, password);
        Admin admin = adminService.checkLogin(username, password);
        Seller seller = sellerService.checkLogin(username, password);

        if (user != null) {
            return ResponseEntity.ok(Map.of("data", user, "role", "user"));
        }
        if (admin != null) {
            return ResponseEntity.ok(Map.of("data", admin, "role", "admin"));
        }
        if (seller != null) {
            return ResponseEntity.ok(Map.of("data", seller, "role", "seller"));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> viewAllProducts() {
        List<Product> products = userService.viewAllProjects();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping("/cart/{userId}/{productId}/{quantity}")
    public ResponseEntity<String> addItemToCart(@PathVariable int userId, @PathVariable int productId, @PathVariable int quantity) {
        String message = userService.addItemToCart(productId, userId, quantity);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<Cart>> cartProducts(@PathVariable int userId) {
        List<Cart> cartItems = userService.cartProducts(userId);
        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/cart/remove/{userId}/{productId}")
    public ResponseEntity<List<Cart>> removeItemFromCart(@PathVariable int userId, @PathVariable int productId) {
        List<Cart> updatedCart = userService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/cart/update/{userId}/{productId}/{quantity}")
    public ResponseEntity<List<Cart>> updateCartItemQuantity(
            @PathVariable int userId,
            @PathVariable int productId,
            @PathVariable int quantity) {
        try {
            List<Cart> updatedCart = userService.updateCartItemQuantity(userId, productId, quantity);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/profile/{userId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable int userId, @RequestBody User updatedProfile) {
        String message = userService.updateUserProfile(userId, updatedProfile);
        if (message.equals("User profile updated successfully!")) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.badRequest().body(message);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable int userId) {
        User user = userService.getUserProfile(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String category) {
        List<Product> products = userService.viewProducts(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getUserOrders(userId));
    }
    
    @PostMapping("/save/{userId}/{productname}/{quantity}/{totalPrice}/{productPrice}")
    public ResponseEntity<?> saveOrder(
        @PathVariable User userId,
        @PathVariable String productname,
        @PathVariable Integer quantity,
        @PathVariable Double totalPrice,
        @PathVariable Double productPrice
    ) {
        System.out.println("Received Values:");
        System.out.println("UserId: " + userId);
        System.out.println("ProductName: " + productname);
        System.out.println("Quantity: " + quantity);
        System.out.println("TotalPrice: " + totalPrice);
        System.out.println("ProductPrice: " + productPrice);

        // Validate data
        if (productname == null || quantity == null || totalPrice == null || productPrice == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data received.");
        }

        // Save Order
        try {
            Order order = new Order();
            order.setUser(userId);
            order.setProductname(productname);
            order.setQuantity(quantity);
            order.setProductprice(productPrice);
            order.setTotalAmount(totalPrice);

            orderRepository.save(order);
            return ResponseEntity.ok("Order saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving order: " + e.getMessage());
        }
    }



    @GetMapping("/cartcount/{userId}")
    public ResponseEntity<Long> cartCount(@PathVariable int userId) {
        Long count = userService.getCartCount(userId);
        return ResponseEntity.ok(count);
    }
    
    @PostMapping("/forgetPassword/{email}")
    public ResponseEntity<?> forgetPassword(@PathVariable String email) {
        User user = userRepository.findByEmail(email);

        // Check if the user exists
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with the given email does not exist.");
        }

        // Generate and send OTP
        String otp = mailSenderService.sendMail(email);
        return ResponseEntity.ok(otp);
    }

    @PostMapping("changepassword/{email}/{password}")
    public ResponseEntity<?> changepassword(@PathVariable String email,@PathVariable String password){
    	User user =  userRepository.findByEmail(email);
    	user.setPassword(password);
    	userRepository.save(user);
    	
    	return ResponseEntity.ok("Password Changed SucessFully");
    }
}