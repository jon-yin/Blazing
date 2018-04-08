package com.blazing.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blazing.controllers.IndexController;

@SpringBootApplication(scanBasePackageClasses={BlazingApplication.class, IndexController.class})
public class BlazingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlazingApplication.class, args);
	}
}
