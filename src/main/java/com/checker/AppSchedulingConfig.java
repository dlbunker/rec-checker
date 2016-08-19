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
	ApplicationProperties applicationProperties;

	@Autowired
	SeleniumReservationService seleniumReservationService;

	@Autowired
	SettingIntepreterService settingIntepreterService;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(this.taskExecutor());
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				AppSchedulingConfig.this.seleniumReservationService.run();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				Calendar nextExecutionTime = new GregorianCalendar();
				Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
				nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
				int delayInt = AppSchedulingConfig.this.settingIntepreterService.getSettingAsInt("delay", 10);
				nextExecutionTime.add(Calendar.SECOND, delayInt);
				return nextExecutionTime.getTime();
			}
		});
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}
}