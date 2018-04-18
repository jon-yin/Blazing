package com.blazing.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.blazing.objects.Celebrity;
import com.blazing.objects.Movie;
import com.blazing.objects.MovieCharacter;
import com.blazing.objects.MovieInfo;
import com.blazing.objects.User;
import com.blazing.repositories.CelebrityRepository;
import com.blazing.repositories.MovieCharacterRepository;
import com.blazing.repositories.MovieRepository;
import com.blazing.repositories.TVRepository;

@Service
public class MediaService {

	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private TVRepository tvRepo;
	@Autowired
	private CelebrityRepository celebRepo;
	@Autowired
	private MovieCharacterRepository mcRepo;

	public Movie findMovie(long id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			return movie.get();
		} else {
			return null;
		}
	}

	public boolean addToWishlist(User user, long id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			if (user.getWishlist().contains(movie.get())) {
				return false;
			} else {
				user.addWishList(movie.get());
				return true;
			}
		} else {
			return false;
		}
	}

	@Transactional
	public void addMovie(Movie movie) {
		movieRepo.save(movie);
	}

	public boolean uploadMovie(MovieInfo info) {
		Movie movie = new Movie();
		String[] airtimes = info.getAirtimes();
		LocalDate[] dates = new LocalDate[airtimes.length];
		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
		for (int i = 0; i < dates.length; i++) {
			dates[i] = LocalDate.parse(airtimes[i], formatter);
		}
		movie.setTitle(info.getTitle());
		movie.setAirtimes(dates);
		movie.setBoxOffice(info.getBoxOffice());
		movie.setDescription(info.getDescription());
		movie.setRunTime(info.getRunTime());
		String castString = info.getCast().trim();
		if (!castString.isEmpty()) {
			String[] cast = castString.split("\n");
			for (String member : cast) {
				String[] characterInfo = member.split(":");
				Optional<Celebrity> celeb = celebRepo.findById(Long.parseLong(characterInfo[0]));
				if (!celeb.isPresent()) {
					return false;
				}
				Celebrity presentCeleb = celeb.get();
				String role = characterInfo[1];
				MovieCharacter character = new MovieCharacter();
				character.setActor(presentCeleb);
				character.setSource(movie);
				mcRepo.save(character);
				celebRepo.save(presentCeleb);
			}
		}
		movieRepo.save(movie);
		return true;
	}
	
	public void getAllMovies(Model model)
	{
		List<Movie> movies = movieRepo.findAll();
		model.addAttribute("browseAllMovies",movies);
	}

	public void getTrendingMovies(Model model) {
		Pageable page = PageRequest.of(0, 10);
		List<Movie> movies =movieRepo.findAll();
		model.addAttribute("trendingMovies", movies);
	}

}
