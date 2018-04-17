package com.blazing.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.Movie;
import com.blazing.objects.TV;

public interface TVRepository extends MediaRepository<TV>{

}
