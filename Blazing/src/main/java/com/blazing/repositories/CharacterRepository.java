package com.blazing.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blazing.objects.MovieCharacter;

@Repository
public interface CharacterRepository extends JpaRepository<MovieCharacter, Long>{

	Optional<MovieCharacter> findMovieCharacterByName(String name);
	
}
