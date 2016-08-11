package com.checker.miner;

/**
 * A representation a page that we can mine for data
 * 
 * @author craftmaster2190
 *
 */
public class SeleniumResultsPage {
	private String screenshotBase64Image;
	private Object scriptResults;
	private Exception exception;

	public String getScreenshotBase64Image() {
		return screenshotBase64Image;
	}

	public void setScreenshotBase64Image(String screenshotBase64Image) {
		this.screenshotBase64Image = screenshotBase64Image;
	}

	public Object getScriptResults() {
		return scriptResults;
	}

	public void setScriptResults(Object scriptResults) {
		this.scriptResults = scriptResults;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
