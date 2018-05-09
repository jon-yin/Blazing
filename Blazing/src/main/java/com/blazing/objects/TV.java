package com.blazing.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.Transient;

@Entity
public class TV extends Media{
	
	private List<Season> seasons;
	private String network;
	
	public TV()
	{
		seasons = new ArrayList<>();
	}
	
	@Column(nullable=false)
	@OneToMany(mappedBy="show")
	public List<Season> getSeasons()
	{
		return seasons;
	}
	
	public void setSeasons(List<Season> seasons)
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
	
	@Column
	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}
	
	@Transient
	public LocalDate LatestEpisodeDate()
	{
		if (seasons == null || seasons.size() == 0)
		{
			return LocalDate.MIN;
		}
		//seasons.sort(Comparator.comparing(Season::getSeasonNumber));
		Season season = seasons.get(seasons.size()-1);
		//season.getEpisodes().sort(Comparator.comparing(Episode::getEpisodeNumber));
		if (season.getEpisodes().size() != 0) {
			Episode latest = season.getEpisodes().get(season.getEpisodes().size() - 1);
			return latest.getAirtimes()[0];
		}
		else {
			return LocalDate.MIN;
		}
	}
}
