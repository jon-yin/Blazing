package com.blazing.objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Line {

	private int id;
	private MovieCharacter speaker;
	private String line;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="CHARACTER_ID")
	public MovieCharacter getSpeaker() {
		return speaker;
	}
	public void setSpeaker(MovieCharacter speaker) {
		this.speaker = speaker;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	
	
}
