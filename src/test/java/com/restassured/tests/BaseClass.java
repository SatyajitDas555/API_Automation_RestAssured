package com.restassured.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.LogStatus;

public class BaseClass {

	Properties prop=new Properties();
	
	/**
	 * 
	 * @param method
	 * @throws IOException 
	 */
	@BeforeMethod
    public void testSetUp(Method method) throws IOException {
		
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/env.properties");
		prop.load(fis);
		
        ExtentTestManager.startTest(method.getName());
    }
    
	/**
	 * 
	 * @param result
	 */
    @AfterMethod
    protected void testTearDown(ITestResult result)
    {
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + "<td><tr>"+ result.getThrowable().getMessage()+"</tr></td>");
        } else {
            ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
        }
        
        ExtentManager.getReporter().endTest(ExtentTestManager.getTest());        
        ExtentManager.getReporter().flush();
    }
	
}
