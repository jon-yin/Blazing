package com.blazing.controllers;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advice {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public boolean failedToParse()
	{
		return false;
	}
	
}
