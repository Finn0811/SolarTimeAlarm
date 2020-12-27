package eu.f1nn.tasks;

import eu.f1nn.config.Config;
import eu.f1nn.fe2.Fe2AlarmController;
import net.time4j.MachineTime;
import net.time4j.Moment;
import net.time4j.PlainDate;
import net.time4j.TemporalType;
import net.time4j.calendar.astro.SolarTime;

import java.util.Optional;
import java.util.Timer;

/**
 * Created by Finn on 26.12.2020.
 */
public class SolarCalculationTask {
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    private final Fe2AlarmController fe2AlarmController;
    private final Config config;

    public SolarCalculationTask(Config config) {
        this.config = config;
        this.fe2AlarmController = new Fe2AlarmController(this.config);
    }

    public void scheduleSunRiseAndSet() {
        SolarTime location = SolarTime.ofLocation(config.lat, config.lng);
        Optional<Moment> sunriseResult = PlainDate.nowInSystemTime().get(location.sunrise());
        Optional<Moment> sunsetResult = PlainDate.nowInSystemTime().get(location.sunset());
        Moment currentMoment = Moment.nowInSystemTime();

        Moment sunriseWithDelay = sunriseResult.get().plus(MachineTime.ofSISeconds(this.config.sunriseDelay * 60));
        Moment sunsetWithDelay = sunsetResult.get().plus(MachineTime.ofSISeconds(this.config.sunsetDelay * 60));

        logger.info("Location lat: " + config.lat);
        logger.info("Location lng: " + config.lng);
        logger.info("Current Moment: " + currentMoment.toZonalTimestamp(() -> "Europe/Berlin"));
        logger.info("Sunrise: " + sunriseResult.get().toZonalTimestamp(() -> "Europe/Berlin").toString());
        logger.info("Sunrise with delay: " + sunriseWithDelay.toZonalTimestamp(() -> "Europe/Berlin").toString());
        logger.info("Sunset: " + sunsetResult.get().toZonalTimestamp(() -> "Europe/Berlin").toString());
        logger.info("Sunset: with delay: " + sunsetWithDelay.toZonalTimestamp(() -> "Europe/Berlin").toString());

        if (sunriseWithDelay.isAfter(currentMoment)) {
            logger.info("Scheduling SunriseTimerTask at " + sunriseWithDelay.toZonalTimestamp(() -> "Europe/Berlin").toString());
            Timer sunriseTimer = new Timer();
            sunriseTimer.schedule(new SunriseTimerTask(this.fe2AlarmController), TemporalType.JAVA_UTIL_DATE.from(sunriseWithDelay));
        }

        if (sunsetWithDelay.isAfter(currentMoment)) {
            logger.info("Scheduling SunsetTimerTask at " + sunsetWithDelay.toZonalTimestamp(() -> "Europe/Berlin").toString());
            Timer sunsetTimer = new Timer();
            sunsetTimer.schedule(new SunsetTimerTask(this.fe2AlarmController), TemporalType.JAVA_UTIL_DATE.from(sunsetWithDelay));
        }
    }
}
