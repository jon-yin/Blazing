package com.blazing.services;

import java.time.LocalDate;
import java.util.Comparator;
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
			model.addAttribute("browseTitle", "Blazing Movie Picks");
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
		case "award-winners":
			List<Movie> movies = findAwardWinningMovies();
			List<TV> tvs = findAwardWinningTVs();
			model.addAttribute("tvs", tvs);
			model.addAttribute("movies", movies);
			model.addAttribute("browseTitle", "Award Winning Media");
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
		model.addAttribute("popularTvs", findPopularTVReleases());
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
		if (tv == null)
			return tv;
		LocalDate monthsAgo = LocalDate.now().minusMonths(2);
		List<TV> filtered = tv.parallelStream().filter(Tv -> (Tv.LatestEpisodeDate().compareTo(monthsAgo) > 0)).collect(Collectors.toList());
		return filtered;
	}
	@Cacheable("TVs")
	public List<TV> findAllTVReleases()
	{
		return tvrepo.findAll();
	}
	
	@Cacheable("Movies")
	public List<Movie> findAwardWinningMovies()
	{
		List<Movie> awardMovies =  movieRepo.findMoviesByAwardedTrue();
		awardMovies.sort(new Comparator<Movie>()
		{

			@Override
			public int compare(Movie o1, Movie o2) {
				return (o1.getAirtimes()[0].compareTo(o2.getAirtimes()[0]));
			}
		});
		return awardMovies;
	}
	
	@Cacheable("TVs")
	public List<TV> findAwardWinningTVs()
	{
		List<TV> tvs =  tvrepo.findTvByAwardedTrue();
		tvs.sort(new Comparator<TV>()
				{
					@Override
					public int compare(TV o1, TV o2) {
						return o1.LatestEpisodeDate().compareTo(o2.LatestEpisodeDate());
					}
			
				});
		return tvs;
	}
}
