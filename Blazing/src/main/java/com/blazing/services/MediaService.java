package com.blazing.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazing.objects.Movie;
import com.blazing.objects.User;
import com.blazing.repositories.MovieRepository;
import com.blazing.repositories.TVRepository;

@Service
public class MediaService {
	
	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private TVRepository tvRepo;
	
	public Movie findMovie(long id)
	{
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent())
		{
			return movie.get();
		}
		else
		{
			return null;
		}
	}
	
	public boolean addToWishlist(User user, long id)
	{
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent())
		{
			if (user.getWishlist().contains(movie.get()))
			{
				return false;
			}
			else
			{
				user.addWishList(movie.get());
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	
}
