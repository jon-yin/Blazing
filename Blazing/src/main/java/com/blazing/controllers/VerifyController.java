package com.blazing.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blazing.objects.SuccessStatus;
import com.blazing.services.LoginRegisterService;

@Controller
@RequestMapping(path="/verify")
public class VerifyController {
	
	@Autowired
	private LoginRegisterService service;

	@RequestMapping(path="/{verification}",method=RequestMethod.GET)
	public String verifyUser(@PathVariable("verification") String verificationString, HttpSession session, RedirectAttributes model)
	{
		SuccessStatus ss = service.verifyUser(verificationString, session);
		if (ss.isSuccess())
		{
			return "redirect:/viewprofile/" + ss.getUser().getId();
		}
		else{
			model.addFlashAttribute("verificationError", true);
			return "redirect:/";
		}
	}
}
