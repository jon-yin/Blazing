package com.blazing.objects;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorColumn(name ="dcolumn")
@Inheritance(strategy=InheritanceType.JOINED)
public class Review {
	
	private long id;
	private int score;
	private boolean critic;
	private String body;
	private User user;
	private LocalDateTime datetime;
	private Media source;
	private int flagCount;
	private Map<Long, String> userReports;
	
	
	public Review()
	{
		userReports = new HashMap<>();
		score = -1;
		critic=false;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	@Column(columnDefinition="LONGTEXT")
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
	@ManyToOne
	@JoinColumn(name="MEDIA_ID")
	public Media getSource() {
		return source;
	}
	public void setSource(Media source) {
		this.source = source;
	}
	
	@Transient
	public boolean isValid()
	{
		if (score == -1){
			return false;
		}
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
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public String toString()
	{
		return "Rating: " + score + "\n" + "Body: " + body;
	}
	
	public int getFlagCount() {
		return flagCount;
	}

	public void setFlagCount(int flagCount) {
		this.flagCount = flagCount;
	}
	
	@ElementCollection
	@Column(columnDefinition = "LONGTEXT")
	public Map<Long, String> getUserReports() {
		return userReports;
	}

	public void setUserReports(Map<Long, String> userReports) {
		this.userReports = userReports;
	}
	
	@Transient
	public boolean addReport(long id, String body)
	{
		if (userReports.containsKey(id))
		{
			return false;
		}
		else
		{
			userReports.put(id,body);
			flagCount++;
			System.out.println("FlagCountWentUp");
			return true;
		}
	}
	
	@Transient
	public Set<String> getReasons()
	{
		HashSet<String> set = new HashSet<>();
		set.addAll(userReports.values());
		return set;
	}

	public boolean isCritic() {
		return critic;
	}

	public void setCritic(boolean critic) {
		this.critic = critic;
	}


	
	
	
	
}
