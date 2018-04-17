package com.blazing.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.blazing.objects.User;
import com.blazing.repositories.UserRepository;

public class UserService {

	@Autowired
	private UserRepository<User> userRepo;
	
	public User findUser(long id)
	{
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent())
		{
			return user.get();
		}
		else
		{
			return null;
		}
	}
	
	@Transactional
	public void saveUserState(User user)
	{
		userRepo.save(user);
	}
	
	
}
