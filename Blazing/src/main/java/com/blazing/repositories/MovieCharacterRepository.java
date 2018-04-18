package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.MovieCharacter;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long>{

}
