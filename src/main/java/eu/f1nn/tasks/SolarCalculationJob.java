package eu.f1nn.tasks;

import eu.f1nn.SolarTimeAlarm;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Created by Finn on 26.12.2020.
 */
public class SolarCalculationJob implements Job {
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    public SolarCalculationJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("SolarCalculationJob executed");
        SolarTimeAlarm.solarCalculationTask.scheduleSunRiseAndSet();
    }
}
