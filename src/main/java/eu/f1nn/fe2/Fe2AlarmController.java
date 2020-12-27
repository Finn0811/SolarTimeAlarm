package eu.f1nn.fe2;

import com.google.gson.Gson;
import eu.f1nn.SolarTimeAlarm;
import eu.f1nn.config.Config;
import eu.f1nn.model.fe2.Alarm;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by Finn on 27.12.2020.
 */
public class Fe2AlarmController {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SolarTimeAlarm.class);
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final Config config;

    public Fe2AlarmController(Config config) {
        this.config = config;
    }

    private void sendAlarmToFe2(Alarm alarm) {
        String toSend = new Gson().toJson(alarm);
        logger.info("Sending " + toSend + " to FE2 Server");

        RequestBody body = RequestBody.create(JSON, toSend);
        okhttp3.Request request = new okhttp3.Request.Builder().url(config.alamosFe2ExternalUri).post(body).build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            logger.info("[Status " + response.code() + "] Sent Alarm to FE2-Server");

            String respBody = response.body().string();
            logger.info(respBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSunriseAlarm() {
        Alarm alarm = new Alarm();
        alarm.setAddress(this.config.sunriseAddress);

        this.sendAlarmToFe2(alarm);
    }

    public void sendSunsetAlarm() {
        Alarm alarm = new Alarm();
        alarm.setAddress(this.config.sunsetAddress);

        this.sendAlarmToFe2(alarm);
    }
}
