/*
 * @author Satyajit Das
 */

package com.restassured.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetUserApi extends BaseClass{

	@Test
	public void getUsersDetails() {

		//Set baseURI
		RestAssured.baseURI= prop.getProperty("Endpoint");
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.get(Resources.usersResource());

		//Validating status code = 200
		Assert.assertEquals(response.statusCode(), 200);
		
		//Passing list of data objects to the reusable methods which will return
		//list size i.e size of data objects
		int sizeOfDataObjects = ReusableMethods.getCountOfDataObjects(response.getBody().jsonPath().getList("data"));
		//Validate the data contains 6 data objects
		Assert.assertEquals(sizeOfDataObjects, 6 , "Data Objects is < or > 6");
	}

	@Test
	public void getSpecificUserDetails() {

		//Set baseURI
		RestAssured.baseURI= prop.getProperty("Endpoint");
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.get(Resources.userIdDetailsResource(1));

		//Validating status code = 200
		Assert.assertEquals(response.statusCode(), 200);

		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		//Validating the value for the corresponding keys
		Assert.assertEquals(jsonPath.get("data.id"), 1, "Data Objects not equals to 1");
		Assert.assertEquals(jsonPath.get("data.email"), "george.bluth@reqres.in", "Email response is incorrect");
		Assert.assertEquals(jsonPath.get("data.first_name"), "George", "First Name is incorrect");
		Assert.assertEquals(jsonPath.get("data.last_name"), "Bluth", "Last name is incorrect");
		Assert.assertEquals(jsonPath.get("data.avatar"), "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg", "image url doesn't match");

	}

}
