package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.User;


public interface UserRepository<T extends User> extends JpaRepository<T, Long>{

	
	Long countUsersByEmailAddress(String emailAddress);
	T findUserByEmailAddress(String emailAddress);
	
}
