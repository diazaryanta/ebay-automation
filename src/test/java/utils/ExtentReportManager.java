package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("eBay Automation Report");
            spark.config().setReportName("UI Automation Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Diaz");
            extent.setSystemInfo("OS", "Mac OS X");
            extent.setSystemInfo("Framework", "Selenium TestNG");
        }
        return extent;
    }
}