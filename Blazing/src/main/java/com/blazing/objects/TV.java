package com.blazing.objects;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class TV extends Media{
	
	private Set<Season> seasons;
	
	@OneToMany(mappedBy="show")
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
