package com.blazing.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blazing.objects.Season;
import com.blazing.objects.TV;

@Repository
public interface SeasonRepository extends GeneralMediaRepository<Season>{

	@Query("select t from Season t where t.blazingScore > 0.7 order by t.blazingScore DESC, size(t.reviews) DESC")
	List<Season> findBlazingSeason();
	
	@Query("select t from Season t where t.criticScore > 0.7 order by t.criticScore DESC, size(t.reviews) DESC")
	List<Season> findPopularSeason();
	
}
