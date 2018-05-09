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
			model.addAttribute("tvs", null);
			model.addAttribute("browseTitle", "Blazing Movies Picks");
			break;
		case "opening-movies":
			List<Movie> weeklyMovies = findWeeklyMovieReleases();
			model.addAttribute("movies", weeklyMovies);
			model.addAttribute("tvs", null);
			model.addAttribute("browseTitle", "Opening This Week");
			break;
		case "coming-soon":
			List<Movie> upcomingMovies = findUpcomingMovieReleases();
			model.addAttribute("movies", upcomingMovies);
			model.addAttribute("tvs", null);
			model.addAttribute("browseTitle", "Coming Soon To Theaters");
			break;
		case "all-movies":
			List<Movie> Movies = findAllMoviesReleases();
			model.addAttribute("movies", Movies);
			model.addAttribute("tvs", null);
			model.addAttribute("browseTitle", "All Movies");
			break;
		case "blazing-tv":
			List<TV> blazingTV = findBlazingTVReleases();
			model.addAttribute("tvs",blazingTV);
			model.addAttribute("movies", null);
			model.addAttribute("browseTitle", "Blazing TV Picks");
			break;
		case "new-tv":
			List<TV> newTV = findWeeklyTVReleases();
			model.addAttribute("tvs", newTV);
			model.addAttribute("movies", null);
			model.addAttribute("browseTitle", "New TV Tonight");
			break;
		case "all-tv":
			List<TV> TVs = findAllTVReleases();
			model.addAttribute("tvs", TVs);
			model.addAttribute("movies", null);
			model.addAttribute("browseTitle", "All TV");
			break;
		case "popular-tv":
			List<TV> popularTV = findPopularTVReleases();
			model.addAttribute("tvs", popularTV);
			model.addAttribute("movies", null);
			model.addAttribute("browseTitle", "Most Popular TV");
			break;
		}
	}
	
	public void getHomePage(Model model)
	{
		model.addAttribute("upcomingMovies", findUpcomingMovieReleases());
		model.addAttribute("blazingMovies", findBlazingMovieReleases());
		model.addAttribute("weeklyMovies", findWeeklyMovieReleases());
		model.addAttribute("allMovie", findAllMoviesReleases());
		model.addAttribute("upcomingTvs", findWeeklyTVReleases());
		model.addAttribute("blazingTvs", findBlazingTVReleases());
		model.addAttribute("allTV", findAllTVReleases());
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
		List<TV> filtered = tv.stream().filter(Tv -> (Tv.getAirtimes()[0].compareTo(monthsAgo) > 0)).collect(Collectors.toList());
		return filtered;
	}
	@Cacheable("TVs")
	public List<TV> findAllTVReleases()
	{
		return tvrepo.findAll();
	}
}
