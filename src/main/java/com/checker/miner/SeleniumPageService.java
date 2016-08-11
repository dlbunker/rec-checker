package com.checker.miner;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Service;

/**
 * Get a Selenium page based on a mine request
 * 
 * @author craftmaster2190
 *
 */
@Service
public class SeleniumPageService {

	private FirefoxBinary firefoxBinary;

	public SeleniumPageService() {
		firefoxBinary = new FirefoxBinary();
	}

	public synchronized SeleniumResultsPage get(MineRequest mineRequest) {
		WebDriver driver = new FirefoxDriver(firefoxBinary, null);
		SeleniumResultsPage results = new SeleniumResultsPage();
		try {
			driver.get(mineRequest.getUrl());
			String script = "return  JSON.stringify((function(){" + mineRequest.getScript() + "})());";
			Object scriptResults = ((JavascriptExecutor) driver).executeScript(script);
			String screenshotBase64Image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

			results.setScriptResults(scriptResults);
			// results.setScreenshotBase64Image(screenshotBase64Image);
		} catch (Exception e) {
			results.setException(e);
		} finally {
			driver.quit();
		}
		return results;
	}

}
