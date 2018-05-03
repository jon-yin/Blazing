package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blazing.objects.User;
import com.blazing.objects.VerificationToken;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken,Long> {

	VerificationToken getVerificationTokenByToken(String Token);
	
	VerificationToken getVerificationTokenByUser(User user);
}
