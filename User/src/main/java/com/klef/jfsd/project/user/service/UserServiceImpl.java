package com.klef.jfsd.project.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.project.user.model.Cart;
import com.klef.jfsd.project.user.model.Order;
import com.klef.jfsd.project.user.model.Product;
import com.klef.jfsd.project.user.model.User;
import com.klef.jfsd.project.user.repository.CartRepository;
import com.klef.jfsd.project.user.repository.OrderRepository;
import com.klef.jfsd.project.user.repository.ProductRepository;
import com.klef.jfsd.project.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public String register(User user) {
        userRepository.save(user);
        return "User registered successfully!";
    }

    @Override
    public User checkLogin(String username, String password) {
        User user = userRepository.findByEmail(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // Login failed
    }

    @Override
    public List<Product> viewAllProjects() {
        return productRepository.findAll();
    }

    @Override
    public String addItemToCart(int productId, int userId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (productOptional.isPresent() && userOptional.isPresent()) {
            Product product = productOptional.get();
            User user = userOptional.get();

            // Check if the product is already in the user's cart
            Cart existingCart = cartRepository.findByUserAndProduct(user, product);
            if (existingCart != null) {
                // Update the quantity
                existingCart.setQuantity(existingCart.getQuantity() + quantity);
                cartRepository.save(existingCart);
                return "Quantity updated successfully!";
            } else {
                // Add a new item to the cart
                Cart cart = new Cart();
                cart.setProduct(product);
                cart.setUser(user);
                cart.setQuantity(quantity);
                cartRepository.save(cart);
                return "Item added to cart successfully!";
            }
        }
        return "Failed to add item to cart. Product or User not found.";
    }

    @Override
    public List<Cart> cartProducts(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        return cartRepository.findByUser(user);
    }

    @Override
    public List<Cart> removeItemFromCart(int userId, int productId) {
        Product product = productRepository.findById(productId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        cartRepository.deleteByUserAndProduct(user, product);

        // Fetch updated cart items after deletion
        return cartRepository.findByUser(user);
    }

    @Override
    public String updateUserProfile(int userId, User updatedProfile) {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            // Update profile fields
            existingUser.setName(updatedProfile.getName());
            existingUser.setEmail(updatedProfile.getEmail());
            existingUser.setPhone(updatedProfile.getPhone());
            existingUser.setAddress(updatedProfile.getAddress());

            userRepository.save(existingUser); // Save updated user
            return "User profile updated successfully!";
        }
        return "User not found!";
    }

    @Override
    public User getUserProfile(int userId) {
        return userRepository.findById(userId).orElse(null); // Fetch user profile or return null
    }

    @Override
    public List<Product> viewProducts(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    public List<Order> getUserOrders(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return orderRepository.findByUser(user);
    }
    

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public long getCartCount(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        return cartRepository.countByUser(user);
    }

    @Override
    public List<Cart> updateCartItemQuantity(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        Cart cart = cartRepository.findByUserAndProduct(user, product);
        cart.setQuantity(quantity);
        
        cartRepository.save(cart);
        return cartRepository.findByUser(user);
        
    }
}
