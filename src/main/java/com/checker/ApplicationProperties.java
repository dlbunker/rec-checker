package com.checker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationProperties {
	
	@Value("${com.checker.default.delay}")
	private int defaultDelay;

	@Value("${com.checker.default.dates}")
	private String defaultDates;

	@Value("${com.checker.default.ids}")
	private String defaultIds;

	public int getDefaultDelay() {
		return defaultDelay;
	}

	public void setDefaultDelay(int defaultDelay) {
		this.defaultDelay = defaultDelay;
	}

	public String getDefaultDates() {
		return defaultDates;
	}

	public void setDefaultDates(String defaultDates) {
		this.defaultDates = defaultDates;
	}

	public String getDefaultIds() {
		return defaultIds;
	}

	public void setDefaultIds(String defaultIds) {
		this.defaultIds = defaultIds;
	}
}
