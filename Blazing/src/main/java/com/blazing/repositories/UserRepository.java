package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
