package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blazing.services.BrowseService;

@Controller
@RequestMapping("/browse")
public class BrowseReleasesController {

	@Autowired
	private BrowseService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public String browse(Model model, @RequestParam("type") String find)
	{
		service.addToModel(find, model);
		return "browse";
	}
	
}
