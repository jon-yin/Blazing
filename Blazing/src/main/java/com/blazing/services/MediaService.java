package com.blazing.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.blazing.objects.Celebrity;
import com.blazing.objects.Media;
import com.blazing.objects.Movie;
import com.blazing.objects.MovieCharacter;
import com.blazing.objects.MovieInfo;
import com.blazing.objects.Review;
import com.blazing.objects.TV;
import com.blazing.objects.User;
import com.blazing.repositories.CelebrityRepository;
import com.blazing.repositories.MediaRepository;
import com.blazing.repositories.MovieCharacterRepository;
import com.blazing.repositories.MovieRepository;
import com.blazing.repositories.ReviewRepository;
import com.blazing.repositories.TVRepository;


@Service
public class MediaService {


	@Autowired
	private MediaRepository mediaRepo;
	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private TVRepository tvRepo;
	@Autowired
	private CelebrityRepository celebRepo;
	@Autowired
	private MovieCharacterRepository mcRepo;
	@Autowired
	private ReviewRepository revRepo;
	
	
	public Movie findMovie(long id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			return movie.get();
		} else {
			return null;
		}
	}
	
	public TV findTV(long id)
	{
		Optional<TV> tv = tvRepo.findById(id);
		return tv.orElse(null);
	}

	public boolean addToWishlist(User user, long id) {
		Optional<Media> media = mediaRepo.findById(id);
		if (media.isPresent()) {
			if (user.getWishlist().contains(media.get())) {
				return false;
			} else {
				user.addWishList(media.get());
				return true;
			}
		} else {
			return false;
		}
	}
	
	public boolean removeFromWishlist(User user, long id)
	{
		Optional<Media> media = mediaRepo.findById(id);
		if (media.isPresent())
		{
			if (user.getWishlist().contains(media.get()))
			{
				user.removeWishList(media.get());
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public boolean addToNotInterested(User user, long id) {
		Optional<Media> media = mediaRepo.findById(id);
		if (media.isPresent()) {
			if (user.getNotInterested().contains(media.get())) {
				return false;
			} else {
				user.addNotInterested(media.get());
				return true;
			}
		} else {
			return false;
		}
	}
	
	public boolean removeFromNotInterested(User user, long id) {
		Optional<Media> media = mediaRepo.findById(id);
		if (media.isPresent()) {
			if (user.getNotInterested().contains(media.get())) {
				user.getNotInterested().remove(media.get());
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Transactional
	public boolean addReview(User user, long id, Review review)
	{
		Optional<Media> media = mediaRepo.findById(id);
		if (media.isPresent() && review.isValid())
		{
			Media curMedia = media.get();
			curMedia.addReview(review);
			user.addToReviews(review);
			review.setUser(user);
			review.setSource(curMedia);
			review.setDatetime(LocalDateTime.now());
			review = revRepo.save(review);
			curMedia = mediaRepo.save(curMedia);
			return true;
		}
		else
		{
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
		Page<Movie> moviesPage =movieRepo.findAll(page);
		List<Movie> movies = moviesPage.getContent();
		model.addAttribute("trendingMovies", movies);
	}

	public boolean reportReview(long reviewID) {

		Optional<Review> target = revRepo.findById(reviewID);
		if (target.isPresent())
		{
			Review review = target.get();
			review.setFlagged(true);
			revRepo.save(review);
			return true;
		}
		return false;
	}

}
