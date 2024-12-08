package com.klef.jfsd.project.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.project.user.model.Shipping;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Integer>{

}
