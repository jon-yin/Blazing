package com.blazing.objects;

import java.util.Set;

public class MovieCharacter {

	private int id;
	private String name;
	private Celebrity actor;
	private Media source;
	private Set<Quotes> quotes;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Celebrity getActor() {
		return actor;
	}
	public void setActor(Celebrity actor) {
		this.actor = actor;
	}
	public Media getSource() {
		return source;
	}
	public void setSource(Media source) {
		this.source = source;
	}
	public Set<Quotes> getQuotes() {
		return quotes;
	}
	public void setQuotes(Set<Quotes> quotes) {
		this.quotes = quotes;
	}
	
}
