package com.ecom.base;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class SuiteListener implements IInvokedMethodListener {

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			try {
				WebDriver driver = BaseFactory.createInstance();
				ScriptHelper.setWebDriver(driver);
				ExtentTest test = BaseFactory.createTestInstance(method.getTestMethod().getMethodName());
				ScriptHelper.setTest(test);
				System.out.println("#### MESSAGE::WebDriver-Instance created successfully. Hashcode:" + driver.hashCode());
			} catch (Exception e) {
				System.out.println("#### EXCEPTION:: "+e.fillInStackTrace());
			}
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (!testResult.isSuccess()) {
			 
			CoreBase.reportVP(Status.FAIL.toString(), "Exception occured." + testResult.getThrowable());
		}
		
		if (method.isTestMethod()) {
			WebDriver driver = ScriptHelper.getDriver();
			if (driver != null) {
				System.out
						.println("#### MESSAGE::WebDriver-Instance closed successfully. Hashcode:" + driver.hashCode());
				driver.quit();
				BaseFactory.proxy.stop();
			}
		}
		
		
	}
}
