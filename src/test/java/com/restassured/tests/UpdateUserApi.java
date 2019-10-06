/*
 * @author Satyajit Das
 */

package com.restassured.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.google.gson.JsonObject;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpdateUserApi extends BaseClass{

	@Test
	public void updateUserDetails() {

		String url = prop.getProperty("Endpoint");
		String resource = Resources.userIdDetailsResource(2);
		//Set baseURI
		RestAssured.baseURI= url;
		ExtentTestManager.getTest().log(LogStatus.INFO, "URI is: " +url);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Resource is: " +resource);
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(Payload.PutUsersData())
				.put(resource);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response Body is: " +response.getBody().asString());

		//Validating status code = 200
		Assert.assertEquals(response.statusCode(), 200);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Status Code 200");

		//Get JSONObject instance by passing response as a String to verify keys
		JsonObject jsonObject = ReusableMethods.getJsonObjectInstance(response.asString());

		//Validating the keys presence
		Assert.assertTrue(jsonObject.has("updatedAt"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains \"updatedAt\" Key");
		Assert.assertTrue(jsonObject.has("name"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains \"name\" Key");
		Assert.assertTrue(jsonObject.has("job"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains \"job\" Key");

		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		//Validating the value for the corresponding keys
		Assert.assertEquals(jsonPath.get("name"), "morpheus");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains  \"morpheus\" as \"name\"");
		Assert.assertEquals(jsonPath.get("job"), "zion resident");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains  \"zion resident\" as \"job\"");

		//Validating JSON Schema
		response.then().assertThat().body(matchesJsonSchemaInClasspath("PUT_UpdateUserSchema.json"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "JSON Schema validated successfully");

	}
}
