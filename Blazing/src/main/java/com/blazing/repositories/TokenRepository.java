package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.VerificationToken;

public interface TokenRepository extends JpaRepository<VerificationToken,Long> {

	VerificationToken getVerificationTokenByToken(String Token);
	
}
