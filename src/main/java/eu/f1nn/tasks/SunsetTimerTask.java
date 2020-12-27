package eu.f1nn.tasks;

import eu.f1nn.fe2.Fe2AlarmController;

import java.util.TimerTask;

/**
 * Created by Finn on 26.12.2020.
 */
public class SunsetTimerTask extends TimerTask {
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    private final Fe2AlarmController fe2AlarmController;

    public SunsetTimerTask(Fe2AlarmController fe2AlarmController) {
        this.fe2AlarmController = fe2AlarmController;
    }

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
        this.fe2AlarmController.sendSunsetAlarm();
    }
}
