package com.blazing.objects;

import java.net.URL;

import javax.persistence.Entity;

@Entity
public class Critic extends User{

	private String publication;
	
	public Critic(String publication) {
		super();
		this.publication = publication;
	}
	
	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}

	public boolean linkReview(URL url)
	{
		return true;
	}
	
	
	
	
}
