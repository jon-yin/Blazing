package com.blazing.objects;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;



@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class Media {

	private long id;
	private String title;
	private String description;
	private ImageEntity poster;
	private LocalDateTime[] airtimes;
	private double audienceScore;
	private double blazingScore;
	private Set<File> videos;
	private Set<ImageEntity> images;
	private Set<MovieCharacter> cast;
	private Set<Review> reviews;
	private Set<Quotes> quotes;
	private Genre genre;
	
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	
	@OneToOne
	@JoinColumn(name="POSTER")
	public ImageEntity getPoster() {
		return poster;
	}
	public void setPoster(ImageEntity poster) {
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
	
	@Transient
	public Set<File> getVideos() {
		return videos;
	}
	public void setVideos(Set<File> videos) {
		this.videos = videos;
	}
	
	@OneToMany
	@JoinColumn(name="IMAGES")
	public Set<ImageEntity> getImages() {
		return images;
	}
	public void setImages(Set<ImageEntity> images) {
		this.images = images;
	}
	
	@OneToMany(mappedBy="source")
	public Set<MovieCharacter> getCast() {
		return cast;
	}
	public void setCast(Set<MovieCharacter> cast) {
		this.cast = cast;
	}
	
	@OneToMany(mappedBy="source")
	public Set<Review> getReviews() {
		return reviews;
	}
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	
	@OneToMany
	public Set<Quotes> getQuotes() {
		return quotes;
	}
	public void setQuotes(Set<Quotes> quotes) {
		this.quotes = quotes;
	}
	
	@Enumerated(EnumType.STRING)
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	public double calculateBlazingScore()
	{
		return 0;
	}
	

	@Transient
	public boolean isBlazing()
	{
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Media other = (Media) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
