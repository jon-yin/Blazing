package com.blazing.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserRepository userRepo;
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
	public boolean addApplication(CriticApplication ca)
	{
		User user = findUser(ca.getUserId());
		if (user == null)
		{
			return false;
		}
		if (user.getRole() == Roles.USER)
		{
			ca.setUser(user);
			user.setApplication(ca);
			appRepo.save(ca);
			return true;
		}
		else
		{
			return false;
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
	
	@Transactional
	public List<CriticApplication> retrieveApplications()
	{
		return appRepo.findAll();
	}
	

	@Transactional
	public List<User> getCritics()
	{
		List<User> critics = userRepo.findAll();
		return critics;
	}
	
	public void updateSessionUser(User newUser)
	{
		sesService.updateCurrentUser(newUser);
	}

	public boolean changePassword(String password, String newPass, String confirm, User user) {
		if (user == null)
		{
			System.out.println("user is null");
			return false;
		}
		else
		{
			String encoded = encoder.encode(password);
			if (encoded.equals(user.getPassword()))
			{
				if (password.equals(newPass))
				{
					return false;
				}
				else
				{
					if (confirm.equals(newPass))
					{
						String encodednewPass = encoder.encode(newPass);
						user.setPassword(encodednewPass);
						User newUser = saveUserState(user);
						sesService.updateCurrentUser(newUser);
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			else
			{
				System.out.println("wrong current password");
				System.out.println(encoded);
				System.out.println(user.getPassword());
				return false;
			}
		}
	}

	public boolean blockUser(User target, User user) {
		if (user == null || target == null){
			return false;
		}
		else
		{
			user.getBlockList().add(target);
			user = userRepo.save(user);
			sesService.updateCurrentUser(user);
			return true;
		}
	}

	public boolean unblockUser(User target, User user) {
		if (user == null || target == null){
			return false;
		}
		else
		{
			user.getBlockList().remove(target);
			user = userRepo.save(user);
			sesService.updateCurrentUser(user);
			return true;
		}
	}
	

}
