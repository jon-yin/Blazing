package com.blazing.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blazing.objects.Test1;
import com.blazing.objects.Test2;
import com.blazing.repositories.Test2Repository;
import com.blazing.repositories.TestRepository;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@RequestMapping(method=RequestMethod.GET)
	public String home()
	{
		return "home";
	}
	

}
