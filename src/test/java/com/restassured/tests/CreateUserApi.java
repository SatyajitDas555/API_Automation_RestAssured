/*
 * @author Satyajit Das
 */

package com.restassured.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateUserApi extends BaseClass{

	@Test
	public void createUser() {
		
		//Set baseURI
		RestAssured.baseURI= prop.getProperty("Endpoint");
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(Payload.postUsersData())
				.post(Resources.usersResource());

		//Validating status code = 201
		Assert.assertEquals(response.statusCode(), 201);

		//Get JSONObject instance by passing response as a String to verify keys
		JsonObject jsonObject = ReusableMethods.getJsonObjectInstance(response.asString());
		//Validating the keys presence
		Assert.assertTrue(jsonObject.has("name"));
		Assert.assertTrue(jsonObject.has("job"));
		Assert.assertTrue(jsonObject.has("id"));
		Assert.assertTrue(jsonObject.has("createdAt"));

		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		//Validating the value for the corresponding key
		Assert.assertEquals(jsonPath.get("name"), "morpheus");
		//Validating the value for the corresponding key
		Assert.assertEquals(jsonPath.get("job"), "zion resident");
	}

	@Test
	public void registerUser() {

		//Set baseURI
		RestAssured.baseURI= prop.getProperty("Endpoint");
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(Payload.postRegisterData())
				.post(Resources.registerResource());

		//Validating status code = 400
		Assert.assertEquals(response.statusCode(), 400);

		//Get JSONObject instance by passing response as a String to verify keys
		JsonObject jsonObject = ReusableMethods.getJsonObjectInstance(response.asString());
		//Validating the keys presence
		Assert.assertTrue(jsonObject.has("error"));

		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		//Validating the value for the corresponding key
		Assert.assertEquals(jsonPath.get("error"), "Missing password");

	}

}
