package com.blazing.objects;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Test1 {
	private long id;
	private String name;
	private Test2 specific;
	private List<Test2> group;
	
	@GeneratedValue
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne
	public Test2 getSpecific() {
		return specific;
	}
	public void setSpecific(Test2 specific) {
		this.specific = specific;
	}
	
	@ManyToMany
	@JoinTable(
			name="T_TEST1_TO_TEST2",
			joinColumns = {@JoinColumn(name="Test2ID", referencedColumnName = "ID") },
			inverseJoinColumns = {@JoinColumn(name="Test1ID", referencedColumnName="ID")}
			)
	public List<Test2> getGroup() {
		return group;
	}
	
	public void setGroup(List<Test2> group) {
		this.group = group;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
