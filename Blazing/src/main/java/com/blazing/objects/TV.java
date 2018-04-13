package com.blazing.objects;

import java.util.Set;

public class TV extends Media{
	
	private Set<Season> seasons;
	
	public Set<Season> getSeasons()
	{
		return seasons;
	}
	
	public void setSeasons(Set<Season> seasons)
	{
		this.seasons = seasons;
	}
	
	public double calculateAudienceScore()
	{
		return 0;
	}

}
