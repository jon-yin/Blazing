package com.blazing.repositories;

import org.springframework.stereotype.Repository;

import com.blazing.objects.Administrator;

@Repository
public interface AdminRepository extends UserRepository<Administrator>{

}
