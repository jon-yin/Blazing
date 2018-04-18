package com.blazing.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.blazing.objects.Media;
import com.blazing.objects.TV;

public interface GeneralMediaRepository<T extends Media> extends JpaRepository<T, Long>{

	T findByTitle(String name);
	Set<T> findByTitleContainingIgnoreCase(String token);
}
