package com.klef.jfsd.project.user.service;

import java.util.List;
import com.klef.jfsd.project.user.model.Admin;
import com.klef.jfsd.project.user.model.Seller;

public interface AdminService {
    Admin checkLogin(String username, String password);
    List<Seller> getAllSellers(); // Fetch sellers
    boolean deleteUserById(int id);
}
