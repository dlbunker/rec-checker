package com.checker.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingIntepreterService {
	
	@Autowired
	SettingRepository settingRepository;

	public String getSettingAsString(String name, String def){
		Setting setting = this.settingRepository.findByName(name);
		if(setting == null){
			return def;
		}
		String value = setting.getValue();
		if(value == null){
			return def;
		}
		return value;
	}
	
	public int getSettingAsInt(String name, int def){
		Setting setting = this.settingRepository.findByName(name);
		if(setting == null){
			return def;
		}
		String value = setting.getValue();
		if(value == null){
			return def;
		}
		try{
			return Integer.parseInt(value);
		}catch (NumberFormatException e){
			return def;
		}
	}
	
	public boolean getSettingAsBoolean(String name, boolean def){
		Setting setting = this.settingRepository.findByName(name);
		if(setting == null){
			return def;
		}
		String value = setting.getValue();
		if(value == null){
			return def;
		}
		return Boolean.parseBoolean(value);
	}
	
	public DateRange getSettingAsDateRange(String name, DateRange def){
		Setting setting = this.settingRepository.findByName(name);
		if(setting == null){
			return def;
		}
		String value = setting.getValue();
		if(value == null){
			return def;
		}
		
		return new DateRange(value);
	}
	
	
}
