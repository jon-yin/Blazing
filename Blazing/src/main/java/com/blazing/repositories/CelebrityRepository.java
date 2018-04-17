package com.blazing.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.Celebrity;

public interface CelebrityRepository extends JpaRepository<Celebrity, Long>{
	
	Optional<Celebrity> findCelebrityByName(String name);
	Optional<Set<Celebrity>> findCelebrityByNameContainingIgnoreCase(String token);

}
