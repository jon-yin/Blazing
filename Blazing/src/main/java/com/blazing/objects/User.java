package com.blazing.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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

import org.springframework.stereotype.Component;

@Entity
@Component
public class User {

	private String firstName;
	private String lastName;
	private String emailAddress;
	private String password;
	private long id;
	private LocalDate joinDate;
	private List<Media> wishlist;
	private List<Media> notInterested;
	private List<Review> reviews;
	private List<Critic> favCritics;
	private ImageEntity profilePic;
	private Set<User> followers;
	private Set<User> following;
	
	
	public User()
	{
		wishlist = new ArrayList<>();
		notInterested = new ArrayList<>();
		reviews = new ArrayList<>();
		favCritics = new ArrayList<>();
		followers = new HashSet<>();
		following = new HashSet<>();
	}
	
	@OneToMany
	@JoinColumn(name="FOLLOWERS_IDS")
	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}
	
	@OneToMany
	@JoinColumn(name="FOLLOWING_IDS")
	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public List<Critic> getFavCritics() {
		return favCritics;
	}
	public void setFavCritics(List<Critic> favCritics) {
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
	
	@ManyToMany
	@JoinColumn(name="N_MEDIA_ID")
	public List<Media> getNotInterested() {
		return notInterested;
	}

	public void setNotInterested(List<Media> notInterested) {
		this.notInterested = notInterested;
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
	
	public void addNotInterested(Media media)
	{
		notInterested.add(media);
	}
	
	public void removeNotInterested(Media media)
	{
		notInterested.remove(media);
	}
	
	public void reportReview(int reviewid)
	{
		
	}
	
	public boolean addFollower(User user)
	{
		return followers.add(user);
	}
	
	public boolean removeFollower(User user)
	{
		return followers.remove(user);
	}
	
	public boolean addFollowing(User user)
	{
		return following.add(user);
	}
	
	public boolean removeFollowing(User user)
	{
		return following.remove(user);
	}
	
	
	
}
