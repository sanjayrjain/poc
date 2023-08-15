
package com.ecom.Listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public abstract class ExtentReportListeners {

    /*
     * public static ExtentHtmlReporter report = null; public static ExtentReports
     * extent = null; public static ExtentTest test = null;
     *
     * public static ExtentReports setUp() { String reportLocation =
     * "./Reports/Extent_Report.html"; report = new
     * ExtentHtmlReporter(reportLocation);
     * report.config().setDocumentTitle("Automation Test Report");
     * report.config().setReportName("Automation Test Report");
     * report.config().setTheme(Theme.STANDARD);
     * System.out.println("Extent Report location initialized . . .");
     * report.start();
     *
     * extent = new ExtentReports(); extent.attachReporter(report);
     * extent.setSystemInfo("Application", "EB");
     * extent.setSystemInfo("Operating System", System.getProperty("os.name"));
     * extent.setSystemInfo("User Name", System.getProperty("user.name"));
     * System.out.println("System Info. set in Extent Report"); return extent; }
     *
     * public static void testStepHandle(String teststatus,WebDriver
     * driver,ExtentTest extenttest,Throwable throwable) { switch (teststatus) {
     * case "FAIL":
     * extenttest.fail(MarkupHelper.createLabel("Test Case is Failed : ",
     * ExtentColor.RED)); extenttest.error(throwable.fillInStackTrace());
     *
     * try { extenttest.addScreenCaptureFromPath(captureScreenShot(driver)); } catch
     * (IOException e) { e.printStackTrace(); }
     *
     * if (driver != null) { driver.quit(); } break;
     *
     * case "PASS":
     * extenttest.pass(MarkupHelper.createLabel("Test Case is Passed : ",
     * ExtentColor.GREEN)); try {
     * extenttest.addScreenCaptureFromPath(captureScreenShot(driver)); } catch
     * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
     * break;
     *
     * default: break; } }
     *
     * public static String captureScreenShot(WebDriver driver) throws IOException {
     * String filePath = "Screenshot//" + " " + getTimeStamp() + ".png";
     *
     * try { TakesScreenshot screen = (TakesScreenshot) driver; File src =
     * screen.getScreenshotAs(OutputType.FILE); filePath = "Screenshot//" + " " +
     * getTimeStamp() + ".png"; File target = new File(filePath);
     * FileUtils.copyFile(src, target); } catch (Exception e) { e.printStackTrace();
     * } return filePath;
     *
     * }
     */

    public static ExtentHtmlReporter html;
    public static ExtentReports extent;
    public ExtentTest suiteTest;
    public String testCaseName, testNodes, testDescription, category, authors;
    public Boolean appendExisting = true;
    public static String reportFolder = "";
    public static String reportImage = "";


    public void startResult() {
        try {
            reportFolder = System.getProperty("user.dir") + "\\Reports\\";
			reportImage = reportFolder + "\\images\\";
            html = new ExtentHtmlReporter(reportFolder + "\\result.html");
            html.setAppendExisting(appendExisting);
            extent = new ExtentReports();
            extent.attachReporter(html);

            extent.setSystemInfo("Author", "ecom-QE Automation Team");
            extent.setSystemInfo("title", "Mobile Regression Suite - Cart Module");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("OS Version", System.getProperty("os.version"));
            extent.setSystemInfo("OS Architecture", System.getProperty("os.arch"));
            extent.setSystemInfo("OS Bit", System.getProperty("sun.arch.data.model"));
            extent.setSystemInfo("JAVA Version", System.getProperty("java.version"));
            try {
                extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
                extent.setSystemInfo("Host IP Address", InetAddress.getLocalHost().getHostAddress());
            } catch (Exception e) {
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public ExtentTest startTestModule(String testCaseName, String testDescription) {
        suiteTest = extent.createTest(testCaseName, testDescription);
        return suiteTest;
    }


    public ExtentTest startTestCase(String testNodes) {
        suiteTest = suiteTest.createNode(testNodes);
        return suiteTest;
    }

    public abstract long takeSnap();


    public void reportStep(ExtentTest et, String desc, String status, boolean bSnap) {

        MediaEntityModelProvider img = null;
        if (bSnap && !status.equalsIgnoreCase("INFO")) {

            long snapNumber = 100000L;
            snapNumber = takeSnap();
            try {
                img = MediaEntityBuilder.createScreenCaptureFromPath
                        (reportImage + snapNumber + ".jpg").build();
            } catch (IOException e) {
            }
        }
        if (status.equalsIgnoreCase("PASS")) {
            et.pass(desc, img);
        } else if (status.equalsIgnoreCase("FAIL")) {
            et.fail(desc, img);
            //  throw new RuntimeException();
        } else if (status.equalsIgnoreCase("WARNING")) {
            et.warning(desc, img);
        } else if (status.equalsIgnoreCase("INFO")) {
            et.info(desc);
        }
    }

    public void endResult() {
        extent.flush();
    }


    public static String getTimeStamp() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sd.format(new Date());
        String date1 = date.replaceAll("[^0-9]", "");
        return date1;

    }


    private static String getcurrentdateandtime() {
        String str = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
            Date date = new Date();
            str = dateFormat.format(date);
            str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
        } catch (Exception e) {
        }
        return str;
    }


}