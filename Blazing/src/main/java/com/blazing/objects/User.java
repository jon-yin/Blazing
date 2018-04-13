package com.blazing.objects;

import java.awt.Image;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class User {

	private String firstName;
	private String lastName;
	private String emailAddress;
	private int id;
	private int views;
	private LocalDate joinDate;
	private List<Media> wishlist;
	private List<Review> reviews;
	private Set<Critic> favCritics;
	private Image profilePic;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public LocalDate getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}
	public List<Media> getWishlist() {
		return wishlist;
	}
	public void setWishlist(List<Media> wishlist) {
		this.wishlist = wishlist;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public Set<Critic> getFavCritics() {
		return favCritics;
	}
	public void setFavCritics(Set<Critic> favCritics) {
		this.favCritics = favCritics;
	}
	public Image getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(Image profilePic) {
		this.profilePic = profilePic;
	}
	
	public void addToReviews(Review review)
	{
		reviews.add(review);
	}
	
	public void removeFromReviews(Review review)
	{
		reviews.remove(review);
	}
	
	public void favCritic(Critic critic)
	{
		favCritics.add(critic);
	}
	
	public void unfavCritic(Critic critic)
	{
		favCritics.remove(critic);
	}
	
	public void addWishList(Media media)
	{
		wishlist.add(media);
	}
	
	public void removeWishList(Media media)
	{
		wishlist.remove(media);
	}
	
	public void reportReview(int reviewid)
	{
		
	}
	
	
	
}
