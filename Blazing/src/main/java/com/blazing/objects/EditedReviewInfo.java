package com.blazing.objects;

public class EditedReviewInfo {

	private long reviewId;
	private String newBody;
	private int rating;
	private boolean blazing;
	
	public long getReviewId() {
		return reviewId;
	}
	public void setReviewId(long reviewId) {
		this.reviewId = reviewId;
	}
	public String getNewBody() {
		return newBody;
	}
	public void setNewBody(String newBody) {
		this.newBody = newBody;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public boolean isBlazing() {
		return blazing;
	}
	public void setBlazing(boolean blazing) {
		this.blazing = blazing;
	}
	
	

}
