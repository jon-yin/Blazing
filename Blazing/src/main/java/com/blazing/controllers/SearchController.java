package com.blazing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

	@RequestMapping(method=RequestMethod.GET)
	public String search(@RequestParam(value="q",required=false)String query)
	{
		System.out.println(query);
		return "search";
	}
	
}
