package com.blazing.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.blazing.controllers.IndexController;
import com.blazing.objects.Movie;
import com.blazing.repositories.MovieRepository;

@SpringBootApplication(scanBasePackageClasses={BlazingApplication.class, IndexController.class,
		MovieRepository.class})
@EntityScan(basePackageClasses={Movie.class})
@EnableJpaRepositories(basePackageClasses={MovieRepository.class})
public class BlazingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlazingApplication.class, args);
	}
}
