package com.blazing.objects;

import javax.persistence.Entity;

@Entity
public class Administrator extends Editor{

	public Administrator()
	{
		super();
		setRole(Roles.ADMIN);
	}
	
	public void banUser(int userID)
	{
		
	}
	
	public void unbanUser(int userID)
	{
		
	}
	
	public void approveCritic(int userID)
	{
		
	}
	
}
