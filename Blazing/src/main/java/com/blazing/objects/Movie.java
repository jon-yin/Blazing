package com.blazing.objects;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Movie extends Media{

	private long id;
	private String name;
	private int boxOffice;
	private int runTime;
	private Celebrity director;
	private Set<Celebrity> writers;

	public int getBoxOffice() {
		return boxOffice;
	}

	public void setBoxOffice(int boxOffice) {
		this.boxOffice = boxOffice;
	}

	public int getRunTime() {
		return runTime;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}

	@OneToOne
	@JoinColumn(name = "CELEBRITY_ID")
	public Celebrity getDirector() {
		return director;
	}

	public void setDirector(Celebrity director) {
		this.director = director;
	}

	@ManyToMany
	@JoinColumn(name = "CELEBRITY_ID")
	public Set<Celebrity> getWriters() {
		return writers;
	}

	public void setWriters(Set<Celebrity> writers) {
		this.writers = writers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
}
