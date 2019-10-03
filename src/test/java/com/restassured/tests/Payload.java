
/*
 * @author Satyajit Das
 */
package com.restassured.tests;


public class Payload {

	/**
	 * 
	 * @return users payload
	 */
	public static String postUsersData()
	{

		String usersData =" {\n" + 
				"    \"name\": \"morpheus\",\n" + 
				"    \"job\": \"zion resident\"\n" + 
				"}";


		return usersData;
	}

	/**
	 * 
	 * @return register payload
	 */
	public static String postRegisterData()
	{

		String registerData ="{\n" + 
				"    \"email\": \"sydney@fife\" \n" + 
				"  }";


		return registerData;
	}

	/**
	 * 
	 * @return users data needs to be updated
	 */
	public static String PutUsersData() {

		String updateUserData ="{\n" + 
				"    \"name\": \"morpheus\",\n" + 
				"    \"job\": \"zion resident\"\n" + 
				"}";


		return updateUserData;

	}


}
