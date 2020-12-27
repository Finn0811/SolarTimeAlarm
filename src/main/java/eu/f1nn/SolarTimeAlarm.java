package eu.f1nn;

import eu.f1nn.tasks.SolarCalculationJob;
import eu.f1nn.tasks.SolarCalculationTask;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by Finn on 26.12.2020.
 */
public class SolarTimeAlarm {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SolarTimeAlarm.class);
    public static SolarCalculationTask solarCalculationTask;

    public static void main(String[] args) {
        ConfigManager configManager = new ConfigManager();
        configManager.saveDefaultConfig(SolarTimeAlarm.class.getResourceAsStream("/config.yml"));
        configManager.loadConfig();

        solarCalculationTask = new SolarCalculationTask(configManager.getConfig());
        solarCalculationTask.scheduleSunRiseAndSet();

        try {
            scheduleSolarCalculationTask();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private static void scheduleSolarCalculationTask() throws SchedulerException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();

        JobDetail jobDetail = newJob(SolarCalculationJob.class).withIdentity("job1", "group1").build();
        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(dailyAtHourAndMinute(0, 15)).build();

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();

        logger.info("Scheduling daily SolarCalculationTask (Next execution: " + trigger.getNextFireTime() + ")");
    }
}
