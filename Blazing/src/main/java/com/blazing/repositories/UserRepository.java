package com.blazing.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.User;


public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findUserByEmailAddress(String emailAddress);
	
}
