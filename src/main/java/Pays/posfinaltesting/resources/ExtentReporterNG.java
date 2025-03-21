package Pays.posfinaltesting.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG {
	 static ExtentReports extent;
	 private static long startTime;
	 public static ExtentReports getReporterObject() {
		  startTime = System.currentTimeMillis();
		 String path = System.getProperty("user.dir") + "\\reports\\index.html";
			ExtentSparkReporter reporter = new ExtentSparkReporter(path);
	        reporter.config().setTheme(Theme.DARK);
			reporter.config().setReportName("Pays POS ");
			reporter.config().setDocumentTitle("Test Results");
			reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'"); // Adds timestamp format
			if (extent != null) {
	            long endTime = System.currentTimeMillis();
	            long durationInSeconds = (endTime - startTime) / 1000;
	            extent.setSystemInfo("Execution Time", durationInSeconds + " seconds");
	        }

			extent = new ExtentReports();
			extent.attachReporter(reporter);
			return extent;
	 }
}
