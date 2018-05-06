package com.blazing.objects;

import javax.persistence.Entity;

@Entity
public class Movie extends Media{

	private int boxOffice;
	private String runTime;

	public int getBoxOffice() {
		return boxOffice;
	}

	public void setBoxOffice(int boxOffice) {
		this.boxOffice = boxOffice;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}
	
	
}
