package com.blazing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazing.objects.User;
import com.blazing.repositories.UserRepository;

@Service
public class SessionService {

	@Autowired
	private UserRepository<User> userRepo;

	
	public User retrieveDatabaseUser(User user)
	{
		if (user == null)
		{
			return null;
		}
		else
		{
			return userRepo.findById(user.getId()).get();
		}
	}

}
