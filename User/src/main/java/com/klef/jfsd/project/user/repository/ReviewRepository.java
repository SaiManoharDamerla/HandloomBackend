package com.klef.jfsd.project.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.project.user.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
