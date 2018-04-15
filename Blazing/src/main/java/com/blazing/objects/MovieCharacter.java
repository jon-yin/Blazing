package com.blazing.objects;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class MovieCharacter {

	private long id;
	private String name;
	private Celebrity actor;
	private Media source;
	private Set<Quotes> quotes;
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
	@JoinColumn(name="CELEBRITY_ID")
	public Celebrity getActor() {
		return actor;
	}
	public void setActor(Celebrity actor) {
		this.actor = actor;
	}
	
	@ManyToOne
	@JoinColumn(name="MEDIA_ID")
	public Media getSource() {
		return source;
	}
	
	public void setSource(Media source) {
		this.source = source;
	}
	
	@OneToMany
	@JoinColumn(name="QUOTE_ID")
	public Set<Quotes> getQuotes() {
		return quotes;
	}
	public void setQuotes(Set<Quotes> quotes) {
		this.quotes = quotes;
	}
	
}
