package com.blazing.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.Movie;


public interface MovieRepository extends JpaRepository<Movie, Long>{
	
	Optional<Movie> findMovieByName(String name);

}
