package com.zoho.core.ddf.util;

import java.io.File;
import java.util.Date;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		// TODO Auto-generated method stub
		if (extent == null) {
			Date d = new Date();
			String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".html";

			extent = new ExtentReports("F:\\exe\\" + fileName, true, DisplayOrder.NEWEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir") + "//ReportsConfiq.xml"));
			extent.addSystemInfo("Selenium Version", "3.4.0").addSystemInfo("Environment", "QA");
		}
		return extent;
	}

}
