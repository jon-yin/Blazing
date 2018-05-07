package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blazing.objects.Celebrity;
import com.blazing.services.CelebrityService;

@Controller
@RequestMapping("/viewceleb/{celebid}")
public class CelebrityController {

	@Autowired
	private CelebrityService celebService;
	
	@RequestMapping(method=org.springframework.web.bind.annotation.RequestMethod.GET)
	public String viewCelebrity(@PathVariable("celebid") long celebid, Model model)
	{
		Celebrity celeb = celebService.findCelebrity(celebid);
		model.addAttribute("celebrity", celeb);
		return "celebrity-details";
	}
}
