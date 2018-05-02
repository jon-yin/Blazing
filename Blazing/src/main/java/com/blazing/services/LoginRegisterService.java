package com.blazing.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blazing.objects.LoginInfo;
import com.blazing.objects.RegisterInfo;
import com.blazing.objects.SuccessStatus;
import com.blazing.objects.User;
import com.blazing.objects.VerificationToken;
import com.blazing.repositories.TokenRepository;
import com.blazing.repositories.UserRepository;

@Service
public class LoginRegisterService {

	@Autowired
	private UserRepository<User> repository;
	
	@Autowired
	private TokenRepository tokenRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional
	public boolean registerUser(RegisterInfo info)
	{
		Long conflicts = repository.countUsersByEmailAddress(info.getEmail());
		if (conflicts == 0)
		{
			User newUser = new User();
			newUser.setFirstName(info.getFirstName());
			newUser.setLastName(info.getLastName());
			newUser.setEmailAddress(info.getEmail());
			newUser.setJoinDate(LocalDate.now());
			newUser.setPassword(encoder.encode(info.getPassword()));
			VerificationToken token = new VerificationToken(newUser,UUID.randomUUID().toString());
			tokenRepo.save(token);
			repository.save(newUser);
			sendVerificationEmail(info.getEmail(), token);
			return true;
		}
		else
		{
			return false;
		}
	}

	private void sendVerificationEmail(String email, VerificationToken token) {
		emailService.sendEmail(email, token);
	}
	
	@Transactional
	public SuccessStatus verifyUser(String verify, HttpSession session)
	{
		SuccessStatus status = new SuccessStatus();
		VerificationToken token =tokenRepo.getVerificationTokenByToken(verify);
		if (token == null)
		{
			status.setSuccess(false);
			return status;
		}
		else{
			User foundUser = token.getUser();
			Optional<User> undetachedUser = repository.findById(foundUser.getId());
			foundUser = undetachedUser.get();
			foundUser.setEnabled(true);
			tokenRepo.delete(token);
			User verified = repository.save(foundUser);
			Hibernate.initialize(verified.getFollowers());
			Hibernate.initialize(verified.getWishlist());
			Hibernate.initialize(verified.getNotInterested());
			Hibernate.initialize(verified.getFollowing());
			Hibernate.initialize(verified.getFavCritics());
			session.setAttribute("currentUser", verified);
			status.setSuccess(true);
			status.setUser(verified);
			return status;
		}
	}
	
	public boolean loginUser(LoginInfo info, HttpSession model) {
		String emailAddress = info.getEmail();
		User user = repository.findUserByEmailAddress(emailAddress);
		String password = info.getPassword();
		if (user != null) {
			User validUser = user;
			Hibernate.initialize(validUser.getFollowers());
			Hibernate.initialize(validUser.getWishlist());
			Hibernate.initialize(validUser.getNotInterested());
			Hibernate.initialize(validUser.getFollowing());
			Hibernate.initialize(validUser.getFavCritics());
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
