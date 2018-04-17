package com.blazing.objects;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component
public class User {

	private String firstName;
	private String lastName;
	private String emailAddress;
	private String password;
	private int id;
	private int views;
	private LocalDate joinDate;
	private List<Media> wishlist;
	private List<Review> reviews;
	private Set<Critic> favCritics;
	private ImageEntity profilePic;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(nullable=false)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(nullable=false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(nullable=false)
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
	
	@ManyToMany
	@JoinColumn(name="MEDIA_ID")
	public List<Media> getWishlist() {
		return wishlist;
	}
	public void setWishlist(List<Media> wishlist) {
		this.wishlist = wishlist;
	}
	
	@OneToMany(mappedBy="user")
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	@ManyToMany
	@JoinColumn(name="CRITIC_ID")
	public Set<Critic> getFavCritics() {
		return favCritics;
	}
	public void setFavCritics(Set<Critic> favCritics) {
		this.favCritics = favCritics;
	}
	
	@OneToOne
	@JoinColumn(name="IMAGE_ID")
	public ImageEntity getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(ImageEntity profilePic) {
		this.profilePic = profilePic;
	}
	
	@Column(nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
