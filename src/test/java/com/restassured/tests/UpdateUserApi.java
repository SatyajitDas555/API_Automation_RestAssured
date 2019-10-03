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

public class UpdateUserApi extends BaseClass{

	@Test
	public void updateUserDetails() {

		//Set baseURI
		RestAssured.baseURI= prop.getProperty("Endpoint");
		//To get the raw response
		Response response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(Payload.PutUsersData())
				.put(Resources.userIdDetailsResource(2));

		//Validating status code = 200
		Assert.assertEquals(response.statusCode(), 200);

		//Get JSONObject instance by passing response as a String to verify keys
		JsonObject jsonObject = ReusableMethods.getJsonObjectInstance(response.asString());

		//Validating the keys presence
		Assert.assertTrue(jsonObject.has("updatedAt"));
		Assert.assertTrue(jsonObject.has("name"));
		Assert.assertTrue(jsonObject.has("job"));

		//Get JSONPath instance by pass raw response to rawToJson reusable method
		JsonPath jsonPath = ReusableMethods.rawToJson(response);
		//Validating the value for the corresponding keys
		Assert.assertEquals(jsonPath.get("name"), "morpheus");
		Assert.assertEquals(jsonPath.get("job"), "zion resident");

	}
}
