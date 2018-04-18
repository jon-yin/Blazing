package com.blazing.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
			Set<Movie> movies = movieRepo.findByTitleContainingIgnoreCase(token);
			Set<TV> tvs = tvRepo.findByTitleContainingIgnoreCase(token);
			Set<Celebrity> celebs = celebRepo.findCelebrityByNameContainingIgnoreCase(token);
			if (movies != null) {
				for (Movie movie : movies) {
					if (movieRelevance.containsKey(movie)) {
						int newhits = movieRelevance.get(movie) + 1;
						movieRelevance.put(movie, newhits);
					} else {
						movieRelevance.put(movie, 1);
					}
				}
				if (tvs != null) {
					for (TV tv : tvs) {
						if (tvRelevance.containsKey(tv)) {
							int newhits = tvRelevance.get(tv) + 1;
							tvRelevance.put(tv, newhits);
						} else {
							tvRelevance.put(tv, 1);
						}
					}
				}
				if (celebs != null) {
					for (Celebrity celeb : celebs) {
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
		
		
		Set<Entry<Movie,Integer>> relevantMovies = movieRelevance.entrySet();
		Set<Entry<TV,Integer>> relevantTv = tvRelevance.entrySet();
		Set<Entry<Celebrity,Integer>> relevantCelebs = celebRelevance.entrySet();
		List<Entry<Movie,Integer>> sortedMovies = new ArrayList<>();
		List<Entry<TV,Integer>> sortedTvs = new ArrayList<>();
		List<Entry<Celebrity,Integer>> sortedCelebrities = new ArrayList<>();
		sortedMovies.addAll(relevantMovies);
		
		
		
		
		
	}
}
