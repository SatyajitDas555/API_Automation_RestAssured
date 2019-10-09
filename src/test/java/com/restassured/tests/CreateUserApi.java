/*
 * @author Satyajit Das
 */

package com.restassured.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.google.gson.JsonObject;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateUserApi extends BaseClass{

	@Test
	public void createUser() {

		//Set baseURI
		String url = prop.getProperty("Endpoint");
		String resource = Resources.usersResource();

		RestAssured.baseURI= url;

		ExtentTestManager.getTest().log(LogStatus.INFO, "URI is: " +url);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Resource is: " +resource);
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(Payload.postUsersData())
				.post(resource);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response Body is: " +response.getBody().asString());
		//Validating status code = 201
		Assert.assertEquals(response.statusCode(), 201);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Status Code Matched");

		//Get JSONObject instance by passing response as a String to verify keys
		JsonObject jsonObject = ReusableMethods.getJsonObjectInstance(response.asString());
		//Validating the keys presence
		Assert.assertTrue(jsonObject.has("name"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains \"name\" Key");
		Assert.assertTrue(jsonObject.has("job"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains \"job\" Key");
		Assert.assertTrue(jsonObject.has("id"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains \"id\" Key");
		Assert.assertTrue(jsonObject.has("createdAt"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains \"createdAt\" Key");

		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		//Validating the value for the corresponding key
		Assert.assertEquals(jsonPath.get("name"), "morpheus");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response body contains \"morpheus\" as \"name\"");
		//Validating the value for the corresponding key
		Assert.assertEquals(jsonPath.get("job"), "zion resident");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response body contains \"zion resident\" as \"job\"");

		//Validating JSON Schema
		response.then().assertThat().body(matchesJsonSchemaInClasspath("POST_CreateUserSchema.json"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "JSON Schema validated successfully");
	}

	@Test
	public void registerUser() {

		String url = prop.getProperty("Endpoint");
		String resource = Resources.registerResource();

		//Set baseURI
		RestAssured.baseURI= url;
		ExtentTestManager.getTest().log(LogStatus.INFO, "URI is: " +url);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Resource is: " +resource);
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(Payload.postRegisterData())
				.post(resource);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response Body is: " +response.getBody().asString());

		//Validating status code = 400
		Assert.assertEquals(response.statusCode(), 400);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Status Code Matched");

		//Get JSONObject instance by passing response as a String to verify keys
		JsonObject jsonObject = ReusableMethods.getJsonObjectInstance(response.asString());
		//Validating the keys presence
		Assert.assertTrue(jsonObject.has("error"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains \"error\" Key");
		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		//Validating the value for the corresponding key
		Assert.assertEquals(jsonPath.get("error"), "Missing password");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response body contains \"Missing password\" as \"error\"");

		//Validating JSON Schema
		response.then().assertThat().body(matchesJsonSchemaInClasspath("POST_RegisterSchema.json"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "JSON Schema validated successfully");

	}

	// Negative Scenario by passing expected wrong name value 
	
	@Test
	public void Negative_createUser() {

		//Set baseURI
		String url = prop.getProperty("Endpoint");
		String resource = Resources.usersResource();

		RestAssured.baseURI= url;

		ExtentTestManager.getTest().log(LogStatus.INFO, "URI is: " +url);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Resource is: " +resource);
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(Payload.postUsersData())
				.post(resource);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response Body is: " +response.getBody().asString());
		
		//Validating status code = 201
		Assert.assertEquals(response.statusCode(), 201);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Status Code Matched");

		
		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		
		//Validating the value for the corresponding key
		Assert.assertEquals(jsonPath.get("name"), "morpheus123","Response body does not contain morpheus as name");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response body contains \"morpheus\" as \"name\"");
		
		//Validating the value for the corresponding key
		Assert.assertEquals(jsonPath.get("job"), "zion resident","Response body does not contain zion resident as job");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response body contains \"zion resident\" as \"job\"");

		//Validating JSON Schema
		response.then().assertThat().body(matchesJsonSchemaInClasspath("POST_CreateUserSchema.json"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "JSON Schema validated successfully");
	}
	
	// Negative Scenario for register Api by passing wrong expected error message
	
	@Test
	public void Negative_registerUser() {

		String url = prop.getProperty("Endpoint");
		String resource = Resources.registerResource();

		//Set baseURI
		RestAssured.baseURI= url;
		ExtentTestManager.getTest().log(LogStatus.INFO, "URI is: " +url);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Resource is: " +resource);
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(Payload.postRegisterData())
				.post(resource);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response Body is: " +response.getBody().asString());

		//Validating status code = 400
		Assert.assertEquals(response.statusCode(), 400);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Status Code Matched");

		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		
		//Validating the value for the corresponding key
		Assert.assertEquals(jsonPath.get("error"), "Missing123 password","Error message doesn't match");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response body contains \"Missing password\" as \"error\"");

		//Validating JSON Schema
		response.then().assertThat().body(matchesJsonSchemaInClasspath("POST_RegisterSchema.json"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "JSON Schema validated successfully");

	}
}
