package com.ecom.Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;

public class ITestListenerImpl extends ExtentReportListeners implements ITestListener{

	private static ExtentReports extent;
	
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("PASS");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("FAIL");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("SKIP");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		System.out.println("Execution started on Production env...");
		//extent= setUp();
		
	}

	public void onFinish(ITestContext context) {
		System.out.println("Execution completed on Production env...");
		extent.flush();		
		System.out.println("Generated Report. . .");	
		
	}

	@Override
	public long takeSnap() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
