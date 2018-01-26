package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonManager {
    private String input;

    public GsonManager (String input) {
        this.input = input;
    }

    public DetailedMeasurement convertMeasurement() throws Exception {
        Gson gson = new GsonBuilder().create();
        DetailedMeasurement measurement = gson.fromJson(input,DetailedMeasurement.class);
        return measurement;
    }

    public Sensor convertSensor() throws Exception {
        Gson gson = new GsonBuilder().create();
        Sensor sensor = gson.fromJson(input,Sensor.class);
        return sensor;
    }
}
