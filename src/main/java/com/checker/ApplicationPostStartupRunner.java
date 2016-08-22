package com.checker;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.checker.settings.DateRange;
import com.checker.settings.SettingIntepreterService;

@Component
public class ApplicationPostStartupRunner {

	@Autowired
	ApplicationProperties applicationProperties;

	@Autowired
	SettingIntepreterService settingIntepreterService;

	@PostConstruct
	public void init() {
		this.settingIntepreterService.setSettingString("on", "false");
		this.settingIntepreterService.setSettingString("delay", this.applicationProperties.getDefaultDelay() + "");
		this.settingIntepreterService.setSettingString("dates",
				new DateRange(this.applicationProperties.getDefaultDates()).toString());
		this.settingIntepreterService.setSettingString("searches", this.applicationProperties.getDefaultSearches());
	}
}
