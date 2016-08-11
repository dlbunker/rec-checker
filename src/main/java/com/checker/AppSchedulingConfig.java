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
import com.checker.settings.Setting;
import com.checker.settings.SettingRepository;

@Configuration
@EnableScheduling
public class AppSchedulingConfig implements SchedulingConfigurer {

	@Autowired
	SeleniumReservationService seleniumReservationService;

	@Autowired
	SettingRepository settingRepository;

	private final static int DEFAULT_DELAY = 30;

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
				seleniumReservationService.get();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				Calendar nextExecutionTime = new GregorianCalendar();
				Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
				nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
				Setting delaySetting = settingRepository.findByName("delay");
				String delaySettingValue = delaySetting == null ? Integer.toString(DEFAULT_DELAY)
						: delaySetting.getValue();
				int delayInt = Integer.parseInt(delaySettingValue);
				nextExecutionTime.add(Calendar.SECOND, delayInt);
				System.err.println("Next execution time: " + nextExecutionTime.getTime());
				return nextExecutionTime.getTime();
			}
		});
	}
}