package com.blazing.objects;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Test2 {
	private long id;
	private String name;
	private List<Test1> involved;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(mappedBy="group")
	public List<Test1> getInvolved() {
		return involved;
	}
	public void setInvolved(List<Test1> involved) {
		this.involved = involved;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	

}
