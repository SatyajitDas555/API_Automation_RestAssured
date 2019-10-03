package com.restassured.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass {

	Properties prop=new Properties();

	@BeforeMethod
	public void testSetUp() throws IOException {
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/env.properties");
		prop.load(fis);
	}

	@AfterMethod
	public void testTearDown() {

	}
	
}
