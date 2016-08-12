package com.checker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.checker.miner.SeleniumReservationService;
import com.checker.settings.SettingIntepreterService;

@Configuration
@EnableScheduling
public class AppSchedulingConfig implements SchedulingConfigurer {

	@Autowired
	SeleniumReservationService seleniumReservationService;

	@Autowired
	SettingIntepreterService settingIntepreterService;

	@Autowired
	ApplicationProperties applicationProperties;

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				AppSchedulingConfig.this.seleniumReservationService.get();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				Calendar nextExecutionTime = new GregorianCalendar();
				Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
				nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
				int delayInt = AppSchedulingConfig.this.settingIntepreterService.getSettingAsInt("delay", applicationProperties.getDefaultDelay());
				nextExecutionTime.add(Calendar.SECOND, delayInt);
				return nextExecutionTime.getTime();
			}
		});
	}
}