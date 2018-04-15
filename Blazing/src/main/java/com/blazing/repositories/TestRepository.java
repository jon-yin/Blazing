package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.Test1;

public interface TestRepository extends JpaRepository<Test1, Long>{

}
