package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.CriticApplication;
import com.blazing.objects.User;

public interface CriticApplicationRepository extends JpaRepository<CriticApplication,Long> {
	
	CriticApplication findApplicationByUser(User id);

}
