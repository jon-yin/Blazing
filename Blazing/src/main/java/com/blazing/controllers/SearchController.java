package com.blazing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blazing.services.SearchService;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String search(@RequestParam(value="q",required=false)String searchQuery, Model model)
	{
		searchService.searchMedia(searchQuery, model);
		return "search";
	}
	
}
