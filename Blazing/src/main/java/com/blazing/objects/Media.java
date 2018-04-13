package com.blazing.objects;

import java.awt.Image;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

public abstract class Media {

	private String title;
	private String description;
	private Image poster;
	private LocalDateTime[] airtimes;
	private double audienceScore;
	private double blazingScore;
	private Set<File> videos;
	private Set<File> images;
	private Set<MovieCharacter> cast;
	private Set<Review> reviews;
	private Set<Quotes> quotes;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Image getPoster() {
		return poster;
	}
	public void setPoster(Image poster) {
		this.poster = poster;
	}
	public LocalDateTime[] getAirtimes() {
		return airtimes;
	}
	public void setAirtimes(LocalDateTime[] airtimes) {
		this.airtimes = airtimes;
	}
	public double getAudienceScore() {
		return audienceScore;
	}
	public void setAudienceScore(double audienceScore) {
		this.audienceScore = audienceScore;
	}
	public double getBlazingScore() {
		return blazingScore;
	}
	public void setBlazingScore(double blazingScore) {
		this.blazingScore = blazingScore;
	}
	public Set<File> getVideos() {
		return videos;
	}
	public void setVideos(Set<File> videos) {
		this.videos = videos;
	}
	public Set<File> getImages() {
		return images;
	}
	public void setImages(Set<File> images) {
		this.images = images;
	}
	public Set<MovieCharacter> getCast() {
		return cast;
	}
	public void setCast(Set<MovieCharacter> cast) {
		this.cast = cast;
	}
	public Set<Review> getReviews() {
		return reviews;
	}
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	public Set<Quotes> getQuotes() {
		return quotes;
	}
	public void setQuotes(Set<Quotes> quotes) {
		this.quotes = quotes;
	}
	
	public double calculateBlazingScore()
	{
		return 0;
	}
	
	public boolean isBlazing()
	{
		return true;
	}
	
}
