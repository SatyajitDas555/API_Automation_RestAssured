/*
 * @author Satyajit Das
 */

package com.restassured.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetUserApi extends BaseClass{

	@Test
	public void getUsersDetails() {

		String url = prop.getProperty("Endpoint");
		String resource = Resources.usersResource();

		//Set baseURI
		RestAssured.baseURI= url;
		ExtentTestManager.getTest().log(LogStatus.INFO, "URI is: " +url);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Resource is: " +resource);
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.get(resource);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Response Body is: " +response.getBody().asString());

		//Validating status code = 200
		Assert.assertEquals(response.statusCode(), 200);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Status Code 200");

		//Passing list of data objects to the reusable methods which will return
		//list size i.e size of data objects
		int sizeOfDataObjects = ReusableMethods.getCountOfDataObjects(response.getBody().jsonPath().getList("data"));
		//Validate the data contains 6 data objects
		Assert.assertEquals(sizeOfDataObjects, 6 , "Data Objects is < or > 6");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains 6 Data Object");

		//Validating JSON Schema
		response.then().assertThat().body(matchesJsonSchemaInClasspath("GET_UserDetailsSchema.json"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "JSON Schema validated successfully");

	}

	@Test
	public void getSpecificUserDetails() {

		String url = prop.getProperty("Endpoint");
		String resource = Resources.userIdDetailsResource(1);

		//Set baseURI
		RestAssured.baseURI= url;
		ExtentTestManager.getTest().log(LogStatus.INFO, "URI is: " +url);
		ExtentTestManager.getTest().log(LogStatus.INFO, "Resource is: " +resource);
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.get(resource);

		ExtentTestManager.getTest().log(LogStatus.INFO, "Response Body is: " +response.getBody().asString());


		//Validating status code = 200
		Assert.assertEquals(response.statusCode(), 200);
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Status Code 200");


		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		//Validating the value for the corresponding keys
		Assert.assertEquals(jsonPath.get("data.id"), 1, "Id not equals to 1");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains 1 in \"id\" key");

		Assert.assertEquals(jsonPath.get("data.email"), "george.bluth@reqres.in", "Email response is incorrect");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains  \"george.bluth@reqres.in\" as \"Email\"");

		Assert.assertEquals(jsonPath.get("data.first_name"), "George", "First Name is incorrect");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains  \"George\" as \"First Name\"");

		Assert.assertEquals(jsonPath.get("data.last_name"), "Bluth", "Last name is incorrect");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains  \"Bluth\" as \"Last Name\"");

		Assert.assertEquals(jsonPath.get("data.avatar"), "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg", "image url doesn't match");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Response Body contains  \"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\" as \"Image Url\"");

		//Validating JSON Schema
		response.then().assertThat().body(matchesJsonSchemaInClasspath("GET_FirstUserDetailsSchema.json"));
		ExtentTestManager.getTest().log(LogStatus.PASS, "JSON Schema validated successfully");
	}

}
