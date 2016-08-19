package com.checker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationProperties {

	@Value("${com.checker.default.dates:null}")
	private String defaultDates;

	@Value("${com.checker.default.delay:180}")
	private int defaultDelay;

	@Value("${com.checker.default.searches:[]}")
	private String defaultSearches;

	@Value("${com.checker.rest.ridb.apikey:null}")
	private String ridbApiKey;

	@Value("${com.checker.selenium.usehtmlunit:true}")
	private boolean useHTMLUnit;

	@Value("${com.checker.selenium.usexvfb:false}")
	private boolean useXvfb;

	@Value("${com.checker.selenium.xvfb.display.port:null}")
	private String xvfbDisplayPort;

	public String getDefaultDates() {
		return this.defaultDates;
	}

	public int getDefaultDelay() {
		return this.defaultDelay;
	}

	public String getDefaultSearches() {
		return this.defaultSearches;
	}

	public String getRidbApiKey() {
		return this.ridbApiKey;
	}

	public String getXvfbDisplayPort() {
		return this.xvfbDisplayPort;
	}

	public boolean isUseHTMLUnit() {
		return this.useHTMLUnit;
	}

	public boolean isUseXvfb() {
		return this.useXvfb;
	}

	public void setDefaultDates(String defaultDates) {
		this.defaultDates = defaultDates;
	}

	public void setDefaultDelay(int defaultDelay) {
		this.defaultDelay = defaultDelay;
	}

	public void setDefaultSearches(String defaultSearches) {
		this.defaultSearches = defaultSearches;
	}

	public void setRidbApiKey(String ridbApiKey) {
		this.ridbApiKey = ridbApiKey;
	}

	public void setUseHTMLUnit(boolean useHTMLUnit) {
		this.useHTMLUnit = useHTMLUnit;
	}

	public void setUseXvfb(boolean useXvfb) {
		this.useXvfb = useXvfb;
	}

	public void setXvfbDisplayPort(String xvfbDisplayPort) {
		this.xvfbDisplayPort = xvfbDisplayPort;
	}

}
