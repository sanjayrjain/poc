package runner;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.PickleEventWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
		features = "Features",
		glue= {"stepDefinitions"},
		
		/*
		 * format = { "pretty", "html:target/cucumber-reports/cucumber-pretty",
		 * "json:target/cucumber-reports/CucumberTestReport.json",
		 * "rerun:target/cucumber-reports/rerun.txt" },
		 */ 
			        monochrome = true,
			        dryRun = false,
			        tags= {"@EBCartFunctionality"}
			        /*plugin= { "pretty",
			                "html:target/cucumber-reports/cucumber-pretty"}		  */  
			        
		)
	public class TestRunner extends AbstractTestNGCucumberTests {
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(dataProvider = "features")
    public void feature(PickleEventWrapper eventwrapper,CucumberFeatureWrapper cucumberFeature) throws Throwable {
       // testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    	testNGCucumberRunner.runScenario(eventwrapper.getPickleEvent());
    }

    @DataProvider
    public Object[][] features() {
        //return testNGCucumberRunner.provideFeatures();
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }
       
    }

