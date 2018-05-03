package com.blazing.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.blazing.objects.Review;
import com.blazing.objects.User;
import com.blazing.repositories.ReviewRepository;
import com.blazing.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository<User> userRepo;
	@Autowired
	private ReviewRepository revRepo;
	@Autowired
	private SessionService sesService;

	@Transactional
	public User findUser(long id) {
		Optional<User> user = userRepo.findById(id);
		//System.out.println("WISHLIST: " + user.get().getWishlist().size());
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
	public boolean removeUser(User target, boolean removeReviews) {
		List<Review> reviews = target.getReviews();
		userRepo.deleteById(target.getId());
		if (removeReviews) {
			revRepo.deleteAll(reviews);
		}
		return true;
	}

}
