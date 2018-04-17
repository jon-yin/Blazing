package com.blazing.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.blazing.objects.Celebrity;
import com.blazing.objects.Movie;
import com.blazing.objects.TV;
import com.blazing.repositories.CelebrityRepository;
import com.blazing.repositories.MovieRepository;
import com.blazing.repositories.TVRepository;

@Service
public class SearchService {

	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private TVRepository tvRepo;
	@Autowired
	private CelebrityRepository celebRepo;

	public void searchMedia(String searchQuery, Model model) {
		Map<Movie, Integer> movieRelevance = new HashMap<>();
		Map<TV, Integer> tvRelevance = new HashMap<>();
		Map<Celebrity, Integer> celebRelevance = new HashMap<>();
		String[] tokens = searchQuery.split("\\s+");
		for (String token : tokens) {
			Optional<Set<Movie>> movies = movieRepo.findByTitleContainingIgnoreCase(token);
			Optional<Set<TV>> tvs = tvRepo.findByTitleContainingIgnoreCase(token);
			Optional<Set<Celebrity>> celebs = celebRepo.findCelebrityByNameContainingIgnoreCase(token);
			if (movies.isPresent()) {
				Set<Movie> presentMovies = movies.get();
				for (Movie movie : presentMovies) {
					if (movieRelevance.containsKey(movie)) {
						int newhits = movieRelevance.get(movie) + 1;
						movieRelevance.put(movie, newhits);
					} else {
						movieRelevance.put(movie, 1);
					}
				}
				if (tvs.isPresent()) {
					Set<TV> presentTV = tvs.get();
					for (TV tv : presentTV) {
						if (tvRelevance.containsKey(tv)) {
							int newhits = tvRelevance.get(tv) + 1;
							tvRelevance.put(tv, newhits);
						} else {
							tvRelevance.put(tv, 1);
						}
					}
				}
				if (celebs.isPresent()) {
					Set<Celebrity> presentCeleb = celebs.get();
					for (Celebrity celeb : presentCeleb) {
						if (celebRelevance.containsKey(celeb)) {
							int newhits = celebRelevance.get(celeb) + 1;
							celebRelevance.put(celeb, newhits);
						} else {
							celebRelevance.put(celeb, 1);
						}
					}
				}
			}
		}
		
	}
}
