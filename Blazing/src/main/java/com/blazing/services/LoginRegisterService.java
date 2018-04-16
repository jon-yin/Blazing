package com.blazing.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blazing.objects.LoginInfo;
import com.blazing.objects.RegisterInfo;
import com.blazing.objects.User;
import com.blazing.repositories.UserRepository;

@Service
@Scope("session")
public class LoginRegisterService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private User user;

	public boolean registerUser(RegisterInfo info)
	{
		Optional<User> user = repository.findUserByEmailAddress(info.getEmail());
		if (user.isPresent())
		{
			return false;
		}
		else
		{
			User newUser = new User();
			newUser.setFirstName(info.getFirstName());
			newUser.setLastName(info.getLastName());
			newUser.setEmailAddress(info.getEmail());
			newUser.setJoinDate(LocalDate.now());
			newUser.setPassword(encoder.encode(info.getPassword()));
			repository.save(newUser);
			this.user = newUser;
			return true;
		}
	}

	public boolean loginUser(LoginInfo info) {
		String emailAddress = info.getEmail();
		Optional<User> user = repository.findUserByEmailAddress(emailAddress);
		String password = info.getPassword();
		if (user.isPresent()) {
			if (encoder.encode(password).equals(user.get().getPassword())) {
				this.user = user.get();
				return true;
			}
		}
		return false;
	}

}
