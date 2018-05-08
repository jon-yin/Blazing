package com.blazing.objects;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;




@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Media implements Comparable<Media>{

	private long id;
	private String title;
	private String description;
	private String poster;
	private LocalDate[] airtimes;
	private double audienceScore;
	private double blazingScore;
	private Set<File> videos;
	private Set<String> images;
	private Set<MovieCharacter> cast;
	private Set<Review> reviews;
	private Genre genre;
	private Set<User> u_Wishlist;
	private Set<User> u_NotInterested;
	
	public Media()
	{
		audienceScore = 0;
		blazingScore = 0;
		airtimes = new LocalDate[2];
		videos = new HashSet<>();
		images = new HashSet<>();
		cast = new HashSet<>();
		reviews = new HashSet<>();
		u_Wishlist = new HashSet<>();
		u_NotInterested = new HashSet<>();
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
	
	@Column(columnDefinition="LONGTEXT")
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
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
	
	public Set<String> getImages() {
		return images;
	}
	public void setImages(Set<String> images) {
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
	
	@Transient
	public Set<CriticReview> getCriticReviews() {
		Set<CriticReview> criticReviews = new HashSet<CriticReview>();
		for (Review review: reviews)
		{
			if (review.isCritic())
			{
				criticReviews.add((CriticReview)review);
			}
		}
		return criticReviews;
	}
	
	@Transient
	public Set<Review> getAudienceReviews() {
		Set<Review> audreview = new HashSet<Review>();
		for (Review review: reviews)
		{
			if (!review.isCritic())
			{
				audreview.add(review);
			}
		}
		return audreview;
	}

	@Enumerated(EnumType.STRING)
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	@Transient
	public List<Review> nonBlockedAudience(User user)
	{
		List<Review> goodReviews = new ArrayList<>();
		for (Review review: getAudienceReviews())
		{
			if (!user.getBlockList().contains((review.getUser())))
			{
				goodReviews.add(review);
			}
		}
		return goodReviews;
	}
	
	@Transient
	public List<CriticReview> nonBlockedCritic(User user)
	{
		List<CriticReview> goodReviews = new ArrayList<>();
		for (CriticReview review: getCriticReviews())
		{
			if (!user.getBlockList().contains((review.getUser())))
			{
				goodReviews.add(review);
			}
		}
		return goodReviews;
	}
	
	
	@ManyToMany(mappedBy = "wishlist")
	public Set<User> getU_Wishlist() {
		return u_Wishlist;
	}

	public void setU_Wishlist(Set<User> u_Wishlist) {
		this.u_Wishlist = u_Wishlist;
	}

	@ManyToMany(mappedBy = "notInterested")
	public Set<User> getU_NotInterested() {
		return u_NotInterested;
	}

	public void setU_NotInterested(Set<User> u_NotInterested) {
		this.u_NotInterested = u_NotInterested;
	}

	public void calculateBlazingScore(CriticReview review, boolean remove)
	{
		double divisor = getCriticReviews().size();
		double blazingTotal = blazingScore * divisor;
		int reviewScore = review.isBlazing() ? 1 : 0;
		if (remove)
		{
			blazingTotal -= reviewScore;
			reviews.remove(review);
			divisor--;
		}
		else
		{
			blazingTotal += reviewScore;
			reviews.add(review);
			divisor++;
		}
		if (divisor == 0)
		{
			blazingScore=0;
		}
		else
		{
			blazingScore = blazingTotal / divisor;
		}
	}
	
	private void calculateAudienceScore(Review review, boolean remove)
	{
		double divisor = getAudienceReviews().size();
		double totalScore = audienceScore * divisor;
		if (remove)
		{
			totalScore -= review.getScore();
			reviews.remove(review);
			divisor--;
		}
		else
		{
			totalScore += review.getScore();
			reviews.add(review);
			divisor++;
		}
		if (divisor == 0)
		{
			audienceScore = 0;
		}
		else
		{
			audienceScore = totalScore /divisor;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Media other = (Media) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public int compareTo(Media other)
	{
		return title.compareTo(other.getTitle());
	}
	
	public void addCriticReview(CriticReview review)
	{
		calculateBlazingScore(review,false);
	}
	
	public void removeCriticReview(CriticReview review)
	{
		calculateBlazingScore(review,true);
	}
	
	public void addReview(Review review)
	{
		calculateAudienceScore(review,false);
		
	}
	
	public void removeReview(Review review)
	{
		calculateAudienceScore(review,true);
	}

	
}
