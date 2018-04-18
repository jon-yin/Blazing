package com.blazing.objects;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class TV extends Media{
	
	private Set<Season> seasons;
	
	public TV()
	{
		seasons = new HashSet<>();
	}
	
	@Column(nullable=false)
	@OneToMany(mappedBy="show")
	public Set<Season> getSeasons()
	{
		return seasons;
	}
	
	public void setSeasons(Set<Season> seasons)
	{
		this.seasons = seasons;
	}
	
	public void addSeason(Season season)
	{
		seasons.add(season);
	}
	
	public double calculateAudienceScore()
	{
		return 0;
	}

}
