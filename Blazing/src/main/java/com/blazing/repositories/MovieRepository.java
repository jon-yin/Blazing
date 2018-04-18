package com.blazing.repositories;

import java.util.List;

import com.blazing.objects.Movie;

public interface MovieRepository extends GeneralMediaRepository<Movie>{

	List<Movie> findFirst10MoviesById();
	
}
