package com.blazing.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blazing.objects.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("select u from User u where u.role = com.blazing.objects.Roles.CRITIC order by size(u.reviews) desc")
	List<User> findMostPopularCritics();
	
	Long countUsersByEmailAddress(String emailAddress);
	
	User findUserByEmailAddress(String emailAddress);
	
}
