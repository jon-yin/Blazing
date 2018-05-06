package com.blazing.objects;

import javax.persistence.Entity;

@Entity
public class CriticReview extends Review {

	private boolean isBlazing;
	private String customScore;
	private String publication;
	
	public CriticReview()
	{
		super();
	}
	
	public CriticReview(Review review)
	{
		setBody(review.getBody());
		setScore(review.getScore());
	}
	
	public boolean isBlazing() {
		return isBlazing;
	}
	public void setBlazing(boolean isBlazing) {
		this.isBlazing = isBlazing;
	}
	public String getCustomScore() {
		return customScore;
	}
	public void setCustomScore(String customScore) {
		this.customScore = customScore;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	
}
