package com.veena.learn.excel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class ActionKeywords {

	private static Logger log = Logger.getLogger(ActionKeywords.class.getName());
	public static WebDriver driver;

	public static void invokeBrowser(String data, String object) {
		log.info("Opening Browser");
		System.out.println("The data here is: " + data);
		try {
			if (data.equals("Firefox")) {
				System.setProperty("webdriver.gecko.driver", "//Users//veena//Downloads//geckodriver");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				log.info("Mozilla browser started");
			} else if (data.equals("Safari")) {
				driver = new SafariDriver();
				driver.manage().window().maximize();
				log.info("Safari browser started");
			} else if (data.equals("Chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-fullscreen");
				driver = new ChromeDriver(options);
				log.info("Chrome browser started");
			}
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
			driver.manage().deleteAllCookies();
		} catch (Exception e) {
			log.info("Not able to open Browser --- " + e.getMessage());
		}
	}

	public static void navigate(String data, String object) {
		try {
			log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
			driver.get(data);
		} catch (Exception e) {
			log.info("Not able to navigate --- " + e.getMessage());
		}
	}

	public static void enterSearchItem(String data, String object) {
		log.info("Entering the text in the search field : " + data);
		try {
			driver.findElement(By.id(object)).clear();
			driver.findElement(By.id(object)).sendKeys(data);
			System.out.println("The OBJECT IS: " + object);
		} catch (Exception e) {
			log.error("Unable to find the search textfield --- " + e.getMessage());
		}
	}

	public static void selectFromAutoComplete(String data, String object) {
		try {
			List<WebElement> optionsToSelect = driver.findElements(By.className(object));
			for (WebElement option : optionsToSelect) {
				if (option.getText().equalsIgnoreCase(data)) {
					option.click();
					break;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void clickSearchBtn(String data, String object) {
		try {
			log.info("Click on the Search Button" + object);
			driver.findElement(By.xpath(object)).click();
		} catch (Exception e) {
			log.error("Unable find the search button --- " + e.getMessage());
		}
	}

	//
	// public static void waitFor(String object, String data) throws Exception {
	// try {
	// log.info("Wait for 5 seconds");
	// Thread.sleep(5000);
	// } catch (Exception e) {
	// log.error("Not able to Wait --- " + e.getMessage());
	// }
	// }
	//
	public static void closeBrowser(String object, String data) {
		try {
			log.info("Closing the browser");
			driver.quit();
		} catch (Exception e) {
			log.error("Not able to Close the Browser --- " + e.getMessage());
		}
	}
}
