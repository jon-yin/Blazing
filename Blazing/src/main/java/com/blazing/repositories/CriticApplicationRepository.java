package com.blazing.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blazing.objects.CriticApplication;

public interface CriticApplicationRepository extends JpaRepository<CriticApplication,Long> {
	
	CriticApplication findApplicationByUserId(long id);

}
