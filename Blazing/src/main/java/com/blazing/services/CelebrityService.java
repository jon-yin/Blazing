package com.blazing.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blazing.objects.Celebrity;
import com.blazing.repositories.CelebrityRepository;

@Service
public class CelebrityService {

	@Autowired
	private CelebrityRepository celebRepo;
	
	@Transactional
	public Celebrity findCelebrity(long id)
	{
		Optional<Celebrity> celeb = celebRepo.findById(id);
		if (celeb.isPresent())
		{
			return celeb.get();
		}
		else
		{
			return null;
		}
	}
	
}
