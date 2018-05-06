package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blazing.objects.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	
	Long countUsersByEmailAddress(String emailAddress);
	User findUserByEmailAddress(String emailAddress);
	
}
