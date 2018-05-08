package com.blazing.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.blazing.objects.Movie;
import com.blazing.objects.TV;
import com.blazing.repositories.MovieRepository;
import com.blazing.repositories.TVRepository;

@Service
public class BrowseService {

	@Autowired
	public TVRepository tvrepo;
	
	@Autowired
	public MovieRepository movieRepo;
	
	public void addToModel(String data, Model model)
	{
		switch(data)
		{
		case "blazing-movies":
			List<Movie> findMovies = findBlazingMovieReleases();
			model.addAttribute("movies", findMovies);
			break;
		case "opening-movies":
			List<Movie> weeklyMovies = findWeeklyMovieReleases();
			model.addAttribute("movies", weeklyMovies);
			break;
		case "coming-soon":
			List<Movie> upcomingMovies = findUpcomingMovieReleases();
			model.addAttribute("movies", upcomingMovies);
			break;
		case "blazing-tv":
			List<TV> blazingTV = findBlazingTVReleases();
			model.addAttribute("tvs",blazingTV);
			break;
		case "new-tv":
			List<TV> newTV = findWeeklyTVReleases();
			model.addAttribute("tvs", newTV);
			break;
		case "all-tv":
			List<TV> Tvs = findAllTVReleases();
			model.addAttribute("tvs", Tvs);
			break;
		case "popular-tv":
			List<TV> popularTV = findPopularTVReleases();
			model.addAttribute("tvs", popularTV);
			break;
		}
	}
	
	@Cacheable("Movies")
	public List<Movie> findUpcomingMovieReleases()
	{
		List<Movie> movies = findAllMoviesReleases();
		LocalDate today = LocalDate.now();
		List<Movie> filtered = movies.parallelStream().filter(movie -> movie.getAirtimes()[0].compareTo(today) > 0).collect(Collectors.toList());
		return filtered;
	}
	
	@Cacheable("Movies")
	public List<Movie> findBlazingMovieReleases()
	{
		return movieRepo.findBlazingMovies();
	}
	@Cacheable("Movies")
	public List<Movie> findWeeklyMovieReleases()
	{
		List<Movie> movies = findAllMoviesReleases();
		LocalDate today = LocalDate.now();
		LocalDate weekAgo = today.minusDays(7);
		List<Movie> filtered = movies.parallelStream().filter(movie -> (movie.getAirtimes()[0].compareTo(weekAgo) > 0) &&
				(movie.getAirtimes()[0].compareTo(today) <= 0)).collect(Collectors.toList());
		return filtered;
	}
	@Cacheable("Movies")
	public List<Movie> findAllMoviesReleases()
	{
		return movieRepo.findAll();
	}
	@Cacheable("TVs")
	public List<TV> findPopularTVReleases()
	{
		List<TV> tvs = tvrepo.findPopularTV();
		return tvs;
	}
	@Cacheable("TVs")
	public List<TV> findBlazingTVReleases()
	{
		return tvrepo.findBlazingTV();
	}
	@Cacheable("TVs")
	public List<TV> findWeeklyTVReleases()
	{
		List<TV> tv = findAllTVReleases();
		LocalDate monthsAgo = LocalDate.now().minusMonths(2);
		if (tv == null)
			return tv;
		List<TV> filtered = tv.parallelStream().filter(Tv -> (Tv.getAirtimes()[0].compareTo(monthsAgo) > 0)).collect(Collectors.toList());
		return filtered;
	}
	@Cacheable("TVs")
	public List<TV> findAllTVReleases()
	{
		return tvrepo.findAll();
	}
}
