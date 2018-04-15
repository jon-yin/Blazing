package com.blazing.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.MovieCharacter;

public interface CharacterRepository extends JpaRepository<MovieCharacter, Long>{

	Optional<MovieCharacter> getMovieCharacterByName();
	
}
