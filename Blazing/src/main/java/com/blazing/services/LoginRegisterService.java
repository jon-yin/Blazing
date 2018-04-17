package com.blazing.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
	public boolean registerUser(RegisterInfo info, Model model)
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
			model.addAttribute("currentUser", newUser);
			repository.save(newUser);
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean loginUser(LoginInfo info, Model model) {
		String emailAddress = info.getEmail();
		Optional<User> user = repository.findUserByEmailAddress(emailAddress);
		String password = info.getPassword();
		if (user.isPresent()) {
			User validUser = user.get();
			if (encoder.matches(password, validUser.getPassword()))
			{
				model.addAttribute("currentUser", validUser);
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
