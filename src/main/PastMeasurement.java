package main;

public class PastMeasurement {

    private String fromDateTime;
    private Measurement measurements;
    private String tillDateTime;


    public String getFromDateTime() {
        return fromDateTime;
    }

    public Measurement getMeasurement() {
        return measurements;
    }

    public String getTillDateTime() {
        return tillDateTime;
    }


    public void setFromDateTime(String fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurements = measurement;
    }

    public void setTillDateTime(String tillDateTime) {
        this.tillDateTime = tillDateTime;
    }

}
