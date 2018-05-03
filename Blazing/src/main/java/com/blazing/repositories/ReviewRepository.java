package com.blazing.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blazing.objects.Media;
import com.blazing.objects.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	List<Review> getReviewsBySource(Media media);

}
