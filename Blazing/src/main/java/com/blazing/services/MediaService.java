package com.blazing.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.blazing.objects.Celebrity;
import com.blazing.objects.CriticReview;
import com.blazing.objects.EditedReviewInfo;
import com.blazing.objects.Media;
import com.blazing.objects.Movie;
import com.blazing.objects.MovieCharacter;
import com.blazing.objects.MovieInfo;
import com.blazing.objects.ReportInfo;
import com.blazing.objects.Review;
import com.blazing.objects.Roles;
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
	@Autowired
	private UserService service;
	@Autowired
	private SessionService sesService;
	@Value("${media.trending}")
	private int numMovies;
	
	@Transactional
	public Movie findMovie(long id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			return movie.get();
		} else {
			return null;
		}
	}

	@Transactional
	public TV findTV(long id) {
		Optional<TV> tv = tvRepo.findById(id);
		return tv.orElse(null);
	}

	@Transactional
	public boolean addToWishlist(User user, long id) {
		//user = sesService.retrieveDatabaseUser(user);
		if (user != null) {
			Optional<Media> media = mediaRepo.findById(id);
			if (media.isPresent()) {
				if (user.getWishlist().contains(media.get())) {
					return false;
				} else {
					user.addWishList(media.get());
					Media foundMedia = media.get();
					foundMedia.getU_Wishlist().add(user);
					mediaRepo.save(foundMedia);
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Transactional
	public boolean removeFromWishlist(User user, long id) {
		if (user != null) {
			Optional<Media> media = mediaRepo.findById(id);
			if (media.isPresent()) {
				if (user.getWishlist().contains(media.get())) {
					user.getWishlist().remove(media.get());
					Media foundMedia = media.get();
					foundMedia.getU_Wishlist().remove(user);
					mediaRepo.save(foundMedia);
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Transactional
	public boolean addToNotInterested(User user, long id) {
		//user = sesService.retrieveDatabaseUser(user);
		if (user != null) {
			Optional<Media> media = mediaRepo.findById(id);
			if (media.isPresent()) {
				if (user.getNotInterested().contains(media.get())) {
					return false;
				} else {
					user.addNotInterested(media.get());
					Media foundMedia = media.get();
					foundMedia.getU_NotInterested().add(user);
					mediaRepo.save(foundMedia);
					return true;
				}
			} else {
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Transactional
	public boolean removeFromNotInterested(User user, long id) {
		Optional<Media> media = mediaRepo.findById(id);
		if (media.isPresent()) {
			if (user.getNotInterested().contains(media.get())) {
				user.getNotInterested().remove(media.get());
				Media foundMedia = media.get();
				foundMedia.getU_NotInterested().remove(user);
				mediaRepo.save(foundMedia);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Transactional
	public boolean addReview(User user, long id, Review review) {
		//user = sesService.retrieveDatabaseUser(user);
		Optional<Media> media = mediaRepo.findById(id);
		if (media.isPresent() && review.isValid()) {
			Media curMedia = media.get();
			if (user.getRole() == Roles.CRITIC)
			{
				curMedia.addReview(review);
			}
			else
			{
				curMedia.addCriticReview((CriticReview)review);
			}
			user.addToReviews(review);
			review.setUser(user);
			review.setSource(curMedia);
			review.setDatetime(LocalDateTime.now());
			review = revRepo.save(review);
			curMedia = mediaRepo.save(curMedia);
			return true;
		} else {
			return false;
			
		}
	}


	public void addMovie(Movie movie) {
		movieRepo.save(movie);
	}

	@Transactional
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

	public void getAllMovies(Model model) {
		List<Movie> movies = movieRepo.findAll();
		model.addAttribute("browseAllMovies", movies);
		LocalDate thisWeek = LocalDate.now().minusDays(7);
		List<Movie> recent = movies.stream().filter(m -> (m.getAirtimes()[0].compareTo(thisWeek) > 0 && m.getAirtimes()[0].compareTo(LocalDate.now())<=0)).collect(Collectors.toList());
		//System.out.println(recent.size());
		model.addAttribute("thisWeek", recent);
		List<Movie> future = movies.stream().filter(m -> (m.getAirtimes()[0].compareTo(LocalDate.now()) > 0)).collect(Collectors.toList());
		model.addAttribute("upcoming", future);
	}

	public void getTrendingMovies(Model model) {
		List<Movie> movies = movieRepo.findAll();
		//List<Movie> trending = movies.subList(0, numMovies);
		model.addAttribute("trendingMovies", movies);
		LocalDate thisWeek = LocalDate.now().minusDays(7);
		LocalDate today = LocalDate.now();
		List<Movie> weeklyMovies = movies.stream().filter(m -> (m.getAirtimes()[0].compareTo(thisWeek) > 0 && m.getAirtimes()[0].compareTo(today)<=0)).collect(Collectors.toList());
		if (weeklyMovies.size() > numMovies)
		{
			weeklyMovies = weeklyMovies.subList(0, numMovies);
		}
		model.addAttribute("thisWeek", weeklyMovies);
		//System.out.println(weeklyMovies.size());
		List<Movie> upcomingMovies = movies.stream().filter(m -> (m.getAirtimes()[0].compareTo(today) > 0)).collect(Collectors.toList());
		if (upcomingMovies.size() > numMovies)
		{
			upcomingMovies = upcomingMovies.subList(0, numMovies);
		}
		model.addAttribute("upcoming", upcomingMovies);
		//System.out.println(upcomingMovies.size());
	}

	@Transactional
	public boolean reportReview(ReportInfo reportData, long userId) {
		Optional<Review> target = revRepo.findById(reportData.getId());
		if (target.isPresent()) {
			Review review = target.get();
			boolean status = review.addReport(userId, reportData.getBody());
			revRepo.save(review);
			return status;
		}
		return false;
	}
	
	public void updateMovieScore(Review newRating)
	{
		
	}
	
	@Transactional
	public boolean editReview(EditedReviewInfo newReview, User user)
	{
		long reviewId = newReview.getReviewId();
		Optional<Review> review = revRepo.findById(reviewId);
		if (review.isPresent())
		{
			Review foundReview = review.get();
			User source = foundReview.getUser();
			Media media = foundReview.getSource();
			if (source.equals(user))
			{
				if (source.getRole() == Roles.CRITIC)
				{
					media.removeCriticReview((CriticReview)foundReview);
				}
				else
				{
					media.removeReview(foundReview);
				}
				foundReview.setBody(newReview.getNewBody());
				foundReview.setDatetime(LocalDateTime.now());
				foundReview.setScore(newReview.getRating());
				if (source.getRole() == Roles.CRITIC)
				{
					CriticReview cReview = (CriticReview) foundReview;
					cReview.setBlazing(newReview.isBlazing());
					media.addCriticReview(cReview);
				}
				else
				{
					media.addReview(foundReview);
				}
				Review savedRev = revRepo.save(foundReview);
				mediaRepo.save(media);
				User updated = service.findUser(user.getId());
				sesService.updateCurrentUser(updated);
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

}
