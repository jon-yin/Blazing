package com.blazing.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.blazing.objects.Media;

@NoRepositoryBean
public interface MediaRepository<T extends Media> extends JpaRepository<T, Long>{

	Optional<T> findByTitle(String name);
	Optional<Set<T>> findByTitleContainingIgnoreCase(String token);
}
