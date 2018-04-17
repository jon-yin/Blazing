package com.blazing.objects;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Celebrity {
	private String name;
	private long id;
	private LocalDate birthday;
	private LocalDate death;
	private ImageEntity portrait;
	private Set<Media> filmography;
	private Set<MovieCharacter> characters;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	@OneToOne
	@JoinColumn(name="IMAGE_ID")
	public ImageEntity getPortrait() {
		return portrait;
	}
	public void setPortrait(ImageEntity portrait) {
		this.portrait = portrait;
	}
	
	@Transient
	public Set<Media> getFilmography() {
		return filmography;
	}
	public void setFilmography(Set<Media> filmography) {
		this.filmography = filmography;
	}
	
	@OneToMany(mappedBy="actor")
	public Set<MovieCharacter> getCharacters() {
		return characters;
	}
	public void setCharacters(Set<MovieCharacter> characters) {
		this.characters = characters;
	}
	
	
}
