package com.klef.jfsd.project.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.project.user.model.Admin;
import com.klef.jfsd.project.user.model.Seller;
import com.klef.jfsd.project.user.repository.AdminRepository;
import com.klef.jfsd.project.user.repository.SellerRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public Admin checkLogin(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public List<Seller> getAllSellers() {
        // Assuming a "seller" role is predefined in the database
        return sellerRepository.findAll();
    }

    @Override
    public boolean deleteUserById(int id) {
    	Optional<Seller> seller = sellerRepository.findById(id);
    	if(seller.isPresent()) {
    		sellerRepository.delete(seller.get());
            return true;
    	}
        return false;
    }
}
