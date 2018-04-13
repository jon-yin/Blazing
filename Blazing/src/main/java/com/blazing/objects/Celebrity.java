package com.blazing.objects;

import java.awt.Image;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Celebrity {
	@Id
	@GeneratedValue
	private int id;
	private LocalDate birthday;
	private LocalDate death;
	private Image portrait;
	@OneToMany
	private Set<Media> filmography;
	@OneToMany
	private Set<MovieCharacter> characters;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	public LocalDate getDeath() {
		return death;
	}
	public void setDeath(LocalDate death) {
		this.death = death;
	}
	public Image getPortrait() {
		return portrait;
	}
	public void setPortrait(Image portrait) {
		this.portrait = portrait;
	}
	public Set<Media> getFilmography() {
		return filmography;
	}
	public void setFilmography(Set<Media> filmography) {
		this.filmography = filmography;
	}
	public Set<MovieCharacter> getCharacters() {
		return characters;
	}
	public void setCharacters(Set<MovieCharacter> characters) {
		this.characters = characters;
	}
	
	
}
