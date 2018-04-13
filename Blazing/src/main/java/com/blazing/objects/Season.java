package com.blazing.objects;

import java.util.List;
import java.util.Set;

public class Season extends Media{
	
	private TV show;
	private int seasonNumber;
	private List<Episode> episodes;
	private String network;
	private Set<Celebrity> creators;
	private Set<Celebrity> executives;
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
	public List<Episode> getEpisodes() {
		return episodes;
	}
	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public Set<Celebrity> getCreators() {
		return creators;
	}
	public void setCreators(Set<Celebrity> creators) {
		this.creators = creators;
	}
	public Set<Celebrity> getExecutives() {
		return executives;
	}
	public void setExecutives(Set<Celebrity> executives) {
		this.executives = executives;
	}
	
	
	
}
