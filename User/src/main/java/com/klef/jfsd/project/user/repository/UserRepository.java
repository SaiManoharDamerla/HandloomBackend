package com.klef.jfsd.project.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.project.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	
	User findByEmail(String email);

}
