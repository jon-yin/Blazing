package com.blazing.repositories;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blazing.objects.Celebrity;
@Repository
public interface CelebrityRepository extends JpaRepository<Celebrity, Long>{
	
	Celebrity findCelebrityByName(String name);
	Set<Celebrity> findCelebrityByNameContainingIgnoreCase(String token);

}
