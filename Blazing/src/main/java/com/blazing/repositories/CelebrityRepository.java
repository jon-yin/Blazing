package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.Celebrity;

public interface CelebrityRepository extends JpaRepository<Celebrity, Long>{

}
