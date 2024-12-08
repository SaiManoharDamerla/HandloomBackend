package com.klef.jfsd.project.user.service;

import java.util.List;

import com.klef.jfsd.project.user.model.Cart;
import com.klef.jfsd.project.user.model.Order;
import com.klef.jfsd.project.user.model.Product;
import com.klef.jfsd.project.user.model.User;

public interface UserService {
    String register(User user);
    User checkLogin(String username, String password);
    List<Product> viewAllProjects();
    String addItemToCart(int productId, int userId, int quantity);
    List<Cart> cartProducts(int userId);
    List<Cart> removeItemFromCart(int userId, int productId);
    String updateUserProfile(int userId, User updatedProfile);
    User getUserProfile(int userId);
    List<Product> viewProducts(String categoryName);
    List<Order> getUserOrders(int userId);
    long getCartCount(int userId);
    List<Cart> updateCartItemQuantity(int userId, int productId, int quantity);
	Order saveOrder(Order order);
}
