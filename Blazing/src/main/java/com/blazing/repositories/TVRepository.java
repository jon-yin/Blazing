package com.blazing.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blazing.objects.TV;

@Repository
public interface TVRepository extends GeneralMediaRepository<TV>{

	@Query("select t from TV t where t.blazingScore > 0.8 order by t.blazingScore DESC, size(t.reviews) DESC")
	List<TV> findBlazingTV();
	
	@Query("select t from TV t where t.audienceScore > 0.8 order by t.audienceScore DESC, size(t.reviews) DESC")
	List<TV> findPopularTV();
	
}
