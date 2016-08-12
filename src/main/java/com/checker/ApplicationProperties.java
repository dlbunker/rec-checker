package com.checker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationProperties {
	
	@Value("${com.checker.default.delay:180}")
	private int defaultDelay;

	@Value("${com.checker.default.dates:null}")
	private String defaultDates;

	@Value("${com.checker.default.ids:null}")
	private String defaultIds;
	
	@Value("${com.checker.selenium.usexvfb:false}")
	private boolean useXvfb;
	
	@Value("${com.checker.selenium.xvfb.display.port:null}")
	private String xvfbDisplayPort;
	
	@Value("${com.checker.selenium.usehtmlunit:true}")
	private boolean useHTMLUnit;

	public int getDefaultDelay() {
		return this.defaultDelay;
	}

	public void setDefaultDelay(int defaultDelay) {
		this.defaultDelay = defaultDelay;
	}

	public String getDefaultDates() {
		return this.defaultDates;
	}

	public void setDefaultDates(String defaultDates) {
		this.defaultDates = defaultDates;
	}

	public String getDefaultIds() {
		return this.defaultIds;
	}

	public void setDefaultIds(String defaultIds) {
		this.defaultIds = defaultIds;
	}

	public boolean isUseXvfb() {
		return this.useXvfb;
	}

	public void setUseXvfb(boolean useXvfb) {
		this.useXvfb = useXvfb;
	}

	public String getXvfbDisplayPort() {
		return this.xvfbDisplayPort;
	}

	public void setXvfbDisplayPort(String xvfbDisplayPort) {
		this.xvfbDisplayPort = xvfbDisplayPort;
	}

	public boolean isUseHTMLUnit() {
		return this.useHTMLUnit;
	}

	public void setUseHTMLUnit(boolean useHTMLUnit) {
		this.useHTMLUnit = useHTMLUnit;
	}

}
