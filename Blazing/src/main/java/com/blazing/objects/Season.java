package com.blazing.objects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Season extends Media{
	
	private TV show;
	private int seasonNumber;
	private List<Episode> episodes;
	private String network;
	
	public Season()
	{
		super();
		episodes = new ArrayList<>();
	}
	
	@ManyToOne
	@JoinColumn(name = "TV_ID")
	public TV getShow() {
		return show;
	}
	public void setShow(TV show) {
		this.show = show;
	}
	public int getSeasonNumber() {
		return seasonNumber;
	}
	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}
	
	@Column(nullable = false)
	@OneToMany(mappedBy="season")
	public List<Episode> getEpisodes() {
		return episodes;
	}
	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
	
	@Column(nullable=false)
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	
	public void addEpisode(Episode episode)
	{
		episodes.add(episode);
	}
	
	
	
	
}
