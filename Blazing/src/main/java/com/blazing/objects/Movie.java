package com.blazing.objects;

import javax.persistence.Entity;

@Entity
public class Movie extends Media{

	private int boxOffice;
	private int runTime;

	public int getBoxOffice() {
		return boxOffice;
	}

	public void setBoxOffice(int boxOffice) {
		this.boxOffice = boxOffice;
	}

	public int getRunTime() {
		return runTime;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}
	
	
}
