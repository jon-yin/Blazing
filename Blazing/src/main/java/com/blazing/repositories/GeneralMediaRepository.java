package com.blazing.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.blazing.objects.Media;
@NoRepositoryBean
public interface GeneralMediaRepository<T extends Media> extends JpaRepository<T, Long>{

	T findByTitle(String name);
	Set<T> findByTitleContainingIgnoreCase(String token);
}
