package com.klef.jfsd.project.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.project.user.model.Order;
import com.klef.jfsd.project.user.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByUser(User user);

}
