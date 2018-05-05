package com.blazing.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blazing.objects.CriticApplication;
import com.blazing.objects.Review;
import com.blazing.objects.Roles;
import com.blazing.objects.User;
import com.blazing.objects.VerificationToken;
import com.blazing.repositories.CriticApplicationRepository;
import com.blazing.repositories.ReviewRepository;
import com.blazing.repositories.TokenRepository;
import com.blazing.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository<User> userRepo;
	@Autowired
	private ReviewRepository revRepo;
	@Autowired
	private TokenRepository tokenRepo;
	@Autowired
	private SessionService sesService;
	@Autowired
	private CriticApplicationRepository appRepo;

	@Transactional
	public User findUser(long id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

	public User saveUserState(User user) {
		return userRepo.save(user);
	}

	@Transactional
	public boolean followUser(User curUser, Long id) {
		Optional<User> followTarget = userRepo.findById(id);
		User target = followTarget.orElse(null);
		if (curUser == null || target == null) {
			return false;
		}
		if (target.addFollower(curUser) && curUser.addFollowing(target)) {
			sesService.updateCurrentUser(saveUserState(curUser));
			saveUserState(target);
			return true;
		} else {
			return false;
		}

	}

	@Transactional
	public boolean unfollowUser(User curUser, Long id) {
		Optional<User> followTarget = userRepo.findById(id);
		User target = followTarget.orElse(null);
		if (curUser == null || target == null) {
			return false;
		}
		if (target.removeFollower(curUser) && curUser.removeFollowing(target)) {
			sesService.updateCurrentUser(saveUserState(curUser));
			saveUserState(target);
			return true;
		} else {
			return false;
		}

	}

	@Transactional
	public boolean removeUser(User target) {
		List<Review> reviews = target.getReviews();
		if (!target.isEnabled())
		{
			VerificationToken token = tokenRepo.getVerificationTokenByUser(target);
			if (token != null)
				tokenRepo.delete(token);
		}
		userRepo.deleteById(target.getId());
		revRepo.deleteAll(reviews);
		return true;
	}
	
	@Transactional
	public void addApplication(CriticApplication ca)
	{
		User user = findUser(ca.getUserId());
		if (user.getRole() == Roles.USER)
		{
			appRepo.save(ca);
	
		}
	}
	
	@Transactional
	public void upgradeUser(long userId, Roles newRole)
	{
		User user = findUser(userId);
		if (user != null)
		{
			user.setRole(newRole);
			saveUserState(user);
		}
	}
	
	@Transactional
	public void removeApplication(CriticApplication application)
	{
		appRepo.delete(application);
	}
	

}
