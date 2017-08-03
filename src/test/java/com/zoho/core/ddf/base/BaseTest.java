package com.zoho.core.ddf.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;

import com.qtpselenum.core.ddf.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {
	public WebDriver driver;
	public  Properties prop;
	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;

	public void openbrowser(String btype) {

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\{test.environment}.properties");
				prop.load(fis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (btype.equals("Mozila")) {
			System.setProperty("webdriver.gecko.driver", prop.getProperty("geckodriver_exe"));
			driver = new FirefoxDriver();

		} else if (btype.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedrive_exe"));
			driver = new ChromeDriver();
		}

	}

	public void navigate(String urlkey) {
		driver.get(prop.getProperty(urlkey));
	}

	public void click(String xpathfile) {
		getElement(xpathfile).click();
	}

	public void type(String xpathfile, String data) {
		getElement(xpathfile).sendKeys(data);

	}

	public WebElement getElement(String Locatorkey) {
		WebElement e = null;
		try {
			if (Locatorkey.endsWith("_xpath")) {
				e = driver.findElement(By.xpath(prop.getProperty(Locatorkey)));
			} else if (Locatorkey.endsWith("_id")) {
				e = driver.findElement(By.id((prop.getProperty(Locatorkey))));
			} else {
				reportFailure("locator not correct" + Locatorkey);
			}
		} catch (Exception ex) {
			reportFailure(ex.getMessage());
			ex.printStackTrace();
			Assert.fail("failed the test " + ex.getMessage());
		}
		return e;
	}

	public boolean verifyText(String expected, String locaterkey) {
		String actual = getElement(locaterkey).getText().trim();
		String expectedtext = prop.getProperty(expected);
		if (actual.equals(expectedtext))
			return true;
		else
			return false;
	}

	public boolean verifyTitle() {
		return false;
	}

	public boolean isElementPresent(String loactorkey) {
		List<WebElement> element = null;
		try {
			if (loactorkey.endsWith("_xpath")) {
				element = driver.findElements(By.xpath(prop.getProperty(loactorkey)));
			} else if (loactorkey.endsWith("_id")) {
				element = driver.findElements(By.id(prop.getProperty(loactorkey)));
			} else if (loactorkey.endsWith("_id")) {
				element = driver.findElements(By.id(prop.getProperty(loactorkey)));
			} else {
				reportFailure("locator not correct" + loactorkey);
			}
		} catch (Exception e) {
			Assert.fail("loactor not found " + e.getStackTrace());

		}

		if (element.size() == 0)
			return false;
		else
			return true;
	}

	public void reportPass(String msg) {

	}

	public void takeScreenshot() {
		Date d = new Date();
		String screenshotfile = d.toString().replace(":", "_").replace(" ", "_") + ".png";

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "//screenshots//" + screenshotfile));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.INFO,
				test.addScreenCapture(System.getProperty("user.dir") + "//screenshots//" + screenshotfile));
	}

	public void reportFailure(String msg) {
		test.log(LogStatus.FAIL, "Test Failed");
		takeScreenshot();
		Assert.fail(msg);
		
	}
	public void init()
	{
		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\projectconfiq.properties");
				prop.load(fis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean isrunnable()
	{
		
		return false;
		
	}
	@AfterTest
	public void stop() {

		if (null != driver) {
			driver.quit();
		}
	}
}
