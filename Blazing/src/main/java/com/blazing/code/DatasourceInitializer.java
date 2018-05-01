package com.blazing.code;

import com.blazing.objects.Movie;
import com.blazing.repositories.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DatasourceInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final MovieRepository movieRepository;

    @Autowired
    public DatasourceInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // load json file, add to my sql db
        Movie movie = null;
        try {
            movie = mapJSONtoMovie("test_movie.json");
            movieRepository.save(movie);
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
