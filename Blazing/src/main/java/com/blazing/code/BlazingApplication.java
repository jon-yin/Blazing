package com.blazing.code;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.blazing.controllers.IndexController;
import com.blazing.objects.Movie;
import com.blazing.objects.User;
import com.blazing.repositories.ReviewRepository;
import com.blazing.repositories.TestRepository;
import com.blazing.services.LoginRegisterService;

@SpringBootApplication(scanBasePackageClasses={BlazingApplication.class, IndexController.class,
		TestRepository.class,LoginRegisterService.class, User.class})
@EntityScan(basePackageClasses={Movie.class})
@EnableJpaRepositories(basePackageClasses={ReviewRepository.class})
@PropertySource("classpath:blazing.props")
public class BlazingApplication {

	@Value("${bcrypt.strength}")
	private int BCRYPT_STRENGTH;
	
	public static void main(String[] args) {
		SpringApplication.run(BlazingApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder getEncoder()
	{
		return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
	}
	
}
