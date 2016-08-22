package com.checker.settings;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SettingRepository extends  PagingAndSortingRepository<Setting, Long>{
	public Setting findByName(@Param(value = "name") String name);
	
}
