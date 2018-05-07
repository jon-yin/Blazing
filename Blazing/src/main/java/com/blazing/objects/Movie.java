package com.blazing.objects;

import javax.persistence.Entity;

@Entity
public class Movie extends Media{

	private String Genres;
	private String Director;
	private String Writer;
	private String rating;
	private int boxOffice;
	private String runTime;
	private String studio;

	public int getBoxOffice() {
		return boxOffice;
	}

	public void setBoxOffice(int boxOffice) {
		this.boxOffice = boxOffice;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getGenres() {
		return Genres;
	}

	public void setGenres(String genres) {
		Genres = genres;
	}

	public String getDirector() {
		return Director;
	}

	public void setDirector(String director) {
		Director = director;
	}

	public String getWriter() {
		return Writer;
	}

	public void setWriter(String writer) {
		Writer = writer;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}
	
	
}
