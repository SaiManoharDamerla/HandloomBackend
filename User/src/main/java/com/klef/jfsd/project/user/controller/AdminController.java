package com.klef.jfsd.project.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.jfsd.project.user.model.Admin;
import com.klef.jfsd.project.user.model.Seller;
import com.klef.jfsd.project.user.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*") // Allow cross-origin for React
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/sellers")
    public List<Seller> getAllSellers() {
        return adminService.getAllSellers(); // Fetch only sellers
    }

    @DeleteMapping("/sellers/{id}")
    public String deleteSeller(@PathVariable int id) {
        boolean isDeleted = adminService.deleteUserById(id); // Reuse delete logic
        return isDeleted ? "Seller deleted successfully." : "Seller not found.";
    }
}
