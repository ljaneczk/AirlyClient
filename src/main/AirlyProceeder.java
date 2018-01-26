package main;

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

    public AirlyProceeder(String apikey, String sensor_id, ModeType type) {
        this.apikey = apikey;
        this.sensor_id = sensor_id;
        this.type = type;
    }

    public void executeRequest() throws Exception {
        if (type == ModeType.sensor)
            launchSensorRequest();
        else
            launchMeasurementRequest();
    }

    private void launchSensorRequest() throws Exception {
        AirlyRequest request = new AirlyRequest(apikey, sensor_id);
        String dataJson = request.runRequest();
        DetailedMeasurement detailedMeasurement = new GsonManager(dataJson).convertMeasurement();

        AirlyRequest infoRequest = new AirlyRequest(apikey,sensor_id,ModeType.sensor);
        String infoJson = infoRequest.runRequest();
        Sensor sensor = new GsonManager(infoJson).convertSensor();

        checkIfDataWasProvided(detailedMeasurement);

        Displayer displayer = new Displayer(detailedMeasurement,sensor,type);
        displayer.printCurrentResponse();
        displayer.printSensorResponse();
    }

    private void launchMeasurementRequest() throws Exception {
        AirlyRequest request = new AirlyRequest(apikey, longitude, latitude, "1000");
        String dataJson = request.runRequest();
        DetailedMeasurement detailedMeasurement = new GsonManager(dataJson).convertMeasurement();

        checkIfDataWasProvided(detailedMeasurement);

        Displayer displayer = new Displayer(detailedMeasurement,type);
        if (type == ModeType.current)
            displayer.printCurrentResponse();
        if (type == ModeType.history)
            displayer.printHistoryResponse();
    }

    public void checkIfDataWasProvided(DetailedMeasurement detailedMeasurement) throws Exception {
        Measurement measurement = detailedMeasurement.getCurrentMeasurement();
        if(measurement.getAirQualityIndex() != null)
            return;
        if(measurement.getHumidity() != null)
            return;
        if(measurement.getMeasurementTime() != null)
            return;
        if(measurement.getPm1() != null)
            return;
        if(measurement.getPm10() != null)
            return;
        if(measurement.getPm25() != null)
            return;
        if(measurement.getPollutionLevel() != null)
            return;
        throw new Exception("Data was not provided. Check your localization parameters (longitude from <14.125,23.940> and latitude from <49.178,54.804>.");
    }
}
