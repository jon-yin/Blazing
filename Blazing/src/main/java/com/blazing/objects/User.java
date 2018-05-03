package com.blazing.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
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
	private boolean enabled;
	private Roles role;
	
	public User()
	{
		wishlist = new ArrayList<>();
		notInterested = new ArrayList<>();
		reviews = new ArrayList<>();
		favCritics = new ArrayList<>();
		followers = new HashSet<>();
		following = new HashSet<>();
		enabled = false;
		role = Roles.USER;
	}

	
	@OneToMany
	@JoinColumn(name="FOLLOWING_ID", referencedColumnName="ID")
	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}
	
	
	@Enumerated(EnumType.STRING)
	public Roles getRole() {
		return role;
	}


	public void setRole(Roles role) {
		this.role = role;
	}


	@OneToMany(fetch=FetchType.EAGER)
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
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name="USER_MEDIA_WISHLIST", joinColumns=@JoinColumn(name="USER_ID",referencedColumnName="ID"), inverseJoinColumns=@JoinColumn(name="MEDIA_ID",referencedColumnName="ID"))
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
	
	@OneToMany
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
	
	@OneToMany
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
		this.getWishlist().add(media);
	}
	
	public void removeWishList(Media media)
	{
		this.getWishlist().remove(media);
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


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	@Override
	public String toString()
	{
		return "ID: " + id + "\n" + "email: " + emailAddress; 
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((joinDate == null) ? 0 : joinDate.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		User other = (User) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (enabled != other.enabled)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (joinDate == null) {
			if (other.joinDate != null)
				return false;
		} else if (!joinDate.equals(other.joinDate))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	
	
	
}
