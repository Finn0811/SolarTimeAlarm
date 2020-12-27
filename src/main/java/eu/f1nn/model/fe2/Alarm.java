package eu.f1nn.model.fe2;

/**
 * Created by Finn on 27.12.2020.
 */
public class Alarm {
    private String message = "SolarTimeAlarm";
    private String address;
    private String type = "ALARM";
    private String sender = "SolarTimeAlarm";
    private String timestamp;
    private AlarmData data;

    public Alarm() {
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public AlarmData getData() {
        return data;
    }

    public void setData(AlarmData data) {
        this.data = data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}