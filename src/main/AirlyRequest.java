package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URL;

public class AirlyRequest {
    private final String measurementsURL="https://airapi.airly.eu/v1/mapPoint/measurements?";
    private final String sensorDataURL="https://airapi.airly.eu/v1/sensor/measurements?";
    private final String sensorInfoURL="https://airapi.airly.eu/v1/sensors/";

    private String apikey = null;
    private String query;

    public AirlyRequest(String apikey, Double longitude, Double latitude, String maxDistance) {
        this.apikey = apikey;
        query = measurementsURL+"apikey="+apikey+"&latitude="+latitude+"&longitude="+longitude+"&maxDistance="+maxDistance;
    }

    public AirlyRequest(String apikey, String sensor_id, ModeType type) {
        this.apikey = apikey;
        query = sensorInfoURL+sensor_id+"?apikey="+apikey;
    }

    public AirlyRequest(String apikey, String sensor_id) {
        this.apikey = apikey;
        query = sensorDataURL+"sensorId="+sensor_id+"&apikey="+apikey;
    }

    public String runRequest() throws Exception {
        System.out.println("query: "+query);
        URLConnection urlConnection = new URL(query).openConnection();
        urlConnection.setDoInput(true);

        checkServerConnection(urlConnection);

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String json = "";
        String line = reader.readLine();
        do {
            json += line;
            line = reader.readLine();
        }
        while (line != null);

        System.out.println("Json: "+json);
        return json;
    }

    private void checkServerConnection(URLConnection urlConnection) throws Exception {
        int status = ((HttpURLConnection)urlConnection).getResponseCode();

        switch (status) {
            case 400:
                throw new Exception("Error 400: Input validation error. Please check your arguments.");
            case 401:
                throw new Exception("Error 401: Unauthorized. Please check your authorization.");
            case 403:
                throw new Exception("Error 403: Forbidden. Please check your apikey.");
            case 404:
                throw new Exception("Error 404: Not Found.");
            case 500:
                throw new Exception("Error 500: Unexpected error.");
        }
    }
}
