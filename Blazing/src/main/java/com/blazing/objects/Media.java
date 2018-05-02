package com.blazing.objects;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
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
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Media implements Comparable<Media>{

	private long id;
	private String title;
	private String description;
	private ImageEntity poster;
	private LocalDate[] airtimes;
	private double audienceScore;
	private double blazingScore;
	private Set<File> videos;
	private Set<ImageEntity> images;
	private Set<MovieCharacter> cast;
	private Set<CriticReview> criticReviews;
	private Set<Review> reviews;
	private Genre genre;
	
	public Media()
	{
		audienceScore = 0;
		blazingScore = 0;
		videos = new HashSet<>();
		images = new HashSet<>();
		reviews = new HashSet<>();
		cast = new HashSet<>();
		reviews = new HashSet<>();
		criticReviews = new HashSet<>();
		
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="title",nullable=false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="description",nullable=false, columnDefinition="LONGTEXT")
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
	
	
	public LocalDate[] getAirtimes() {
		return airtimes;
	}
	public void setAirtimes(LocalDate[] airtimes) {
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
	@Column(nullable=false)
	public Set<MovieCharacter> getCast() {
		return cast;
	}
	public void setCast(Set<MovieCharacter> cast) {
		this.cast = cast;
	}
	
	@OneToMany(mappedBy="source")
	@Column(nullable=false)
	public Set<Review> getReviews() {
		return reviews;
	}
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	
	@OneToMany(mappedBy="source")
	@Column(nullable=false)
	public Set<CriticReview> getCriticReviews() {
		return criticReviews;
	}

	public void setCriticReviews(Set<CriticReview> criticReviews) {
		this.criticReviews = criticReviews;
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
	
	
	public int compareTo(Media other)
	{
		return title.compareTo(other.getTitle());
	}
	
	public void addReview(Review review)
	{
		reviews.add(review);
	}
	
	public void removeReview(Review review)
	{
		reviews.remove(review);
	}
	
	
}
