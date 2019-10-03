/*
 * @author Satyajit Das
 */

package com.restassured.tests;

import java.io.IOException;
import java.util.List;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {
	
	/**
	 * 
	 * @param res
	 * @return JsonPath instance
	 */
	public static JsonPath rawToJson(Response res)
	{ 
		String response=res.asString();
		JsonPath x=new JsonPath(response);
		return x;
	}
	
	/**
	 * 
	 * @return the status code
	 * @throws IOException
	 */
	public static int getStatusCode() throws IOException {
		
		return 0;
	}
	
	/**
	 * 
	 * @param body
	 * @return JsonObject
	 */
	public static JsonObject getJsonObjectInstance(String body) {
		
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(body);
		return json;
		
	}
	
	/**
	 * 
	 * @param list
	 * @return size of data objects
	 */
	public static int getCountOfDataObjects(List<Object> list) {
		
		return list.size();
	}

	

}
