package com.blazing.repositories;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blazing.objects.Movie;

@Repository
public interface MovieRepository extends GeneralMediaRepository<Movie>{

	@Query("select m from Movie m where m.blazingScore > 0.7 order by m.blazingScore DESC, size(m.reviews) DESC")
	List<Movie> findBlazingMovies();

	@CacheEvict(value = "Movies", allEntries=true)
	 <S extends Movie> S save(S entity);
	
	
}
