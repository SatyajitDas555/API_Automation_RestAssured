/*
 * @author Satyajit Das
 */

package com.restassured.tests;

public class Resources {

	/**
	 * 
	 * @return
	 */
	public static String usersResource() {
		
		String res="/api/users";
		return res;
	}
	
	/**
	 * This can use for POST and PUT
	 * @param userId
	 * @return the resource of user details
	 */
	public static String userIdDetailsResource(int userId) {
		
		String res="/api/users/"+userId;
		return res;
	}
	
	/**
	 * This will be use to create an account
	 * @return  the resource for registration
	 */
	public static String registerResource() {
		
		String res="/api/register";
		return res;
	}
	
}
