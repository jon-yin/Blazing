package com.blazing.objects;

public class Episode extends Media{

	private Season season;
	private String description;
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
