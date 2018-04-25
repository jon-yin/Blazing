package com.blazing.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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

	private Set<String> commonWords;
	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private TVRepository tvRepo;
	@Autowired
	private CelebrityRepository celebRepo;

	public SearchService()
	{
		commonWords = new HashSet<String>();
		InputStream stream = getClass().getClassLoader().getResourceAsStream("dictionary.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		try {
			while ((line = reader.readLine()) != null)
			{
				commonWords.add(line);
			}
		} catch (IOException e) {
		}
	}
	
	public void searchMedia(String searchQuery, Model model) {
		Map<Movie, Integer> movieRelevance = new HashMap<>();
		Map<TV, Integer> tvRelevance = new HashMap<>();
		Map<Celebrity, Integer> celebRelevance = new HashMap<>();
		String[] tokens = searchQuery.split("\\s+");
		tokens = sortOutCommonWords(tokens);
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

		Set<Entry<Movie, Integer>> relevantMovies = movieRelevance.entrySet();
		Set<Entry<TV, Integer>> relevantTv = tvRelevance.entrySet();
		Set<Entry<Celebrity, Integer>> relevantCelebs = celebRelevance.entrySet();
		List<Movie> mostRelevantMovies = this.<Movie>getMostRelevant(relevantMovies);
		List<TV> mostRelevantTVs = this.<TV>getMostRelevant(relevantTv);
		List<Celebrity> mostRelevantCelebrities = this.<Celebrity>getMostRelevant(relevantCelebs);
		model.addAttribute("relevantMovies", mostRelevantMovies);
		model.addAttribute("relevantTV", mostRelevantTVs);
		model.addAttribute("relevantCelebs", mostRelevantCelebrities);
	}

	private <T> List<T> getMostRelevant(Set<Entry<T, Integer>> relevantMovies) {
		List<Entry<T, Integer>> entries = new ArrayList<>();
		entries.addAll(relevantMovies);
		entries.sort(Comparator.comparing(Entry<T, Integer>::getValue).reversed());
		List<T> mostRelevant = new ArrayList<>();
		for (Entry<T, Integer> elem : entries) {
			mostRelevant.add(elem.getKey());
		}
		return mostRelevant;
	}

	private String[] sortOutCommonWords(String[] tokens) {
		Set<String> words = new HashSet<>();
		for (String token : tokens)
		{
			if (!commonWords.contains(token))
			{
				words.add(token);
			}
		}
		String[] filteredTokens = new String[words.size()];
		filteredTokens = words.toArray(filteredTokens);
		if (filteredTokens.length == 0)
		{
			return tokens;
		}
		else
		{
			return filteredTokens;
		}
	}

}
