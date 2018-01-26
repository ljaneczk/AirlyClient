package main;

//import org.apache.http.client.HttpResponseException

public class AirlyProceeder  {
    private ModeType type = null;
    private String apikey = null;
    private String sensor_id = null;
    private Double longitude = null;
    private Double latitude = null;

    public AirlyProceeder(String apikey, Double longitude, Double latitude, ModeType type) {
        this.apikey = apikey;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
    }

    //apikey, sensorid, mode
    public AirlyProceeder(String apikey, String sensor_id, ModeType type) {
        this.apikey = apikey;
        this.sensor_id = sensor_id;
        this.type = type;
    }

    public void executeRequest() throws Exception {
        if (type == ModeType.sensor) {
            launchSensorRequest();
        }
        else {
            launchMeasurementRequest();
        }
    }

    private void launchSensorRequest() throws Exception {
        AirlyRequest request = new AirlyRequest(apikey, sensor_id);
        String dataJson = request.runRequest();
        DetailedMeasurement measurement = new GsonManager(dataJson).convertMeasurement();

        AirlyRequest infoRequest = new AirlyRequest(apikey,sensor_id,ModeType.sensor);
        String infoJson = infoRequest.runRequest();
        Sensor sensor = new GsonManager(infoJson).convertSensor();

        System.out.println("Found sensor="+sensor_id+"and key="+apikey+" "+type);
        //Parse arguments and show data
    }

    private void launchMeasurementRequest() throws Exception {
        AirlyRequest request = new AirlyRequest(apikey, longitude, latitude, "200");
        String dataJson = request.runRequest();
        DetailedMeasurement measurement = new GsonManager(dataJson).convertMeasurement();

        System.out.println("Found coord="+longitude+" "+latitude+" and key="+apikey+" "+type);
        //Parse arguments and show data
    }
}
