package com.blazing.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.blazing.objects.Movie;
import com.blazing.objects.Roles;
import com.blazing.objects.User;
import com.blazing.repositories.MovieRepository;
import com.blazing.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DatasourceInitializer implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
    private MovieRepository movieRepository;
    
	@Autowired
    private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // load json file, add to my sql db
        Movie movie = null;
        try {
            movie = mapJSONtoMovie("data/test_movie.json");
            movieRepository.save(movie);
            User user = new User();
            user.setEmailAddress("a@a.com");
            user.setPassword(encoder.encode("a"));
            user.setEnabled(true);
            user.setRole(Roles.ADMIN);
            user.setFirstName("Admin");
            user.setLastName("guy");
            userRepo.save(user);
            User testUser = new User();
            testUser.setEmailAddress("b@b.com");
            testUser.setPassword(encoder.encode("b"));
            testUser.setEnabled(true);
            testUser.setFirstName("User");
            testUser.setLastName("guy");
            testUser.setRole(Roles.USER);
            userRepo.save(testUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Movie mapJSONtoMovie(String jsonFileName) throws IOException {
        Path jsonFilePath = Paths.get(jsonFileName);
        String jsonString = new String(Files.readAllBytes(jsonFilePath.toAbsolutePath()));
        ObjectMapper om = new ObjectMapper();
        return om.readValue(jsonString, Movie.class);
    }

}
