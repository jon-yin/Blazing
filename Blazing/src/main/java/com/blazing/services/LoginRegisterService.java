package com.blazing.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
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

	@Autowired
	private EntityManager em;
	
	@Transactional
	public boolean reverifyUser(long id)
	{
		Optional<User> user= repository.findById(id);
		if (user.isPresent())
		{
			User foundUser =user.get();
			if (foundUser.isEnabled())
			{
				return false;
			}
			else
			{
				VerificationToken old = tokenRepo.getVerificationTokenByUser(foundUser);
				if (old != null)
				{
					tokenRepo.delete(old);
				}
				VerificationToken token = new VerificationToken(foundUser,UUID.randomUUID().toString());
				tokenRepo.save(token);
				String email = foundUser.getEmailAddress();
				sendVerificationEmail(email, token);
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	
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
			System.out.println(em.contains(foundUser));
			User verified = repository.save(foundUser);
			Hibernate.initialize(verified.getFollowers());
			Hibernate.initialize(verified.getWishlist());
			Hibernate.initialize(verified.getNotInterested());
			Hibernate.initialize(verified.getFollowing());
			Hibernate.initialize(verified.getReviews());
			verified = repository.save(verified);
			session.setAttribute("currentUser", verified);
			status.setSuccess(true);
			status.setUser(verified);
			User detached = (User)session.getAttribute("currentUser");
			return status;
		}
	}
	
	@Transactional
	public boolean loginUser(LoginInfo info, HttpSession session) {
		String emailAddress = info.getEmail();
		User user = repository.findUserByEmailAddress(emailAddress);
		String password = info.getPassword();
		if (user != null) {
			Hibernate.initialize(user.getReviews());
			Hibernate.initialize(user.getFollowers());
			Hibernate.initialize(user.getWishlist());
			Hibernate.initialize(user.getNotInterested());
			Hibernate.initialize(user.getFollowing());
			user = repository.saveAndFlush(user);
			if (encoder.matches(password, user.getPassword()))
			{
				session.setAttribute("currentUser", user);
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
