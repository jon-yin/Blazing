package com.blazing.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blazing.objects.LoginInfo;
import com.blazing.objects.RegisterInfo;
import com.blazing.objects.User;
import com.blazing.repositories.UserRepository;

@Service
public class LoginRegisterService {

	@Autowired
	private UserRepository<User> repository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional
	public boolean registerUser(RegisterInfo info, HttpSession session)
	{
		System.out.println("Registering user");
		Long conflicts = repository.countUsersByEmailAddress(info.getEmail());
		if (conflicts == 0)
		{
			User newUser = new User();
			newUser.setFirstName(info.getFirstName());
			newUser.setLastName(info.getLastName());
			newUser.setEmailAddress(info.getEmail());
			newUser.setJoinDate(LocalDate.now());
			newUser.setPassword(encoder.encode(info.getPassword()));
			session.setAttribute("currentUser", newUser);
			repository.save(newUser);
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean loginUser(LoginInfo info, HttpSession model) {
		String emailAddress = info.getEmail();
		User user = repository.findUserByEmailAddress(emailAddress);
		String password = info.getPassword();
		if (user != null) {
			User validUser = user;
			if (encoder.matches(password, validUser.getPassword()))
			{
				model.setAttribute("currentUser", validUser);
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}

}
