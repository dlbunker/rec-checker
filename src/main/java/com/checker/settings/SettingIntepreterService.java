package com.checker.settings;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@Service
public class SettingIntepreterService {

	Logger logger = Logger.getLogger(SettingIntepreterService.class);

	@Autowired
	SettingRepository settingRepository;

	public boolean getSettingAsBoolean(String name, boolean def) {
		Setting setting = this.settingRepository.findByName(name);
		if (setting == null) {
			return def;
		}
		String value = setting.getValue();
		if (value == null) {
			return def;
		}
		return Boolean.parseBoolean(value);
	}

	public DateRange getSettingAsDateRange(String name, DateRange def) {
		Setting setting = this.settingRepository.findByName(name);
		if (setting == null) {
			return def;
		}
		String value = setting.getValue();
		if (value == null) {
			return def;
		}

		return new DateRange(value);
	}

	public int getSettingAsInt(String name, int def) {
		Setting setting = this.settingRepository.findByName(name);
		if (setting == null) {
			return def;
		}
		String value = setting.getValue();
		if (value == null) {
			return def;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public String getSettingAsString(String name, String def) {
		Setting setting = this.settingRepository.findByName(name);
		if (setting == null) {
			return def;
		}
		String value = setting.getValue();
		if (value == null) {
			return def;
		}
		return value;
	}

	public String[] getSettingAsStringArray(String name, String[] def) {
		Setting setting = this.settingRepository.findByName(name);
		if (setting == null) {
			return def;
		}
		String value = setting.getValue();
		if (value == null) {
			return def;
		}
		ObjectMapper mapper = new ObjectMapper();
		String[] sortings;
		try {
			sortings = mapper.readValue(value, TypeFactory.defaultInstance().constructArrayType(String.class));
		} catch (IOException e) {
			this.logger.log(Level.ERROR, e);
			return def;
		}
		return sortings;
	}

	public void setSettingString(String name, String value) {
		Setting setting = this.settingRepository.findByName(name);
		if (setting == null) {
			setting = new Setting();
			setting.setName(name);
		}
		setting.setValue(value);
		this.settingRepository.save(setting);
	}
}
