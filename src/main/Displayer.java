package main;

import java.util.regex.Pattern;

public class Displayer {
    private DetailedMeasurement detailedMeasurement = null;
    private Sensor sensor = null;
    private ModeType type;
    private int lengthOfLine = 47;

    Displayer(DetailedMeasurement measurement, Sensor sensor, ModeType type){
        this.detailedMeasurement = measurement;
        this.sensor = sensor;
        this.type = type;
    }

    Displayer(DetailedMeasurement measurement, ModeType type){
        this.detailedMeasurement = measurement;
        this.type = type;
    }

    public void setDetailedMeasurement(DetailedMeasurement detailedMeasurement) {
        this.detailedMeasurement = detailedMeasurement;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setType(ModeType type) {
        this.type = type;
    }


    public void printCurrentResponse() throws Exception{
        if (detailedMeasurement == null)
            throw new Exception("There is no available measurement to be displayed.");
        printAirly();
        drawBeginningFrame();
        printValue("   Air Quality    ", detailedMeasurement.getCurrentMeasurement().getAirQualityIndex(),"");
        printValue("    Humidity      ", detailedMeasurement.getCurrentMeasurement().getHumidity()," [%]");
        printValue("     Pm1          ", detailedMeasurement.getCurrentMeasurement().getPm1()," [μg/m3]");
        printValue("     Pm10         ", detailedMeasurement.getCurrentMeasurement().getPm10()," [μg/m3]");
        printValue("     Pm25         ", detailedMeasurement.getCurrentMeasurement().getPm25()," [μg/m3]");
        printValue(" Pollution Level  ", detailedMeasurement.getCurrentMeasurement().getPollutionLevel(),"");
        drawEndingFrame();
    }

    public void printHistoryResponse() {
        printAirly();
        drawBeginningFrame();
        boolean anythingDrownBefore = false;
        for (PastMeasurement data : detailedMeasurement.getHistory()) {
            if (data.getMeasurement() != null) {
                if (anythingDrownBefore)
                    drawEmptyLineWith("===============================================");
                drawEmptyLineWith("  "+data.getFromDateTime() + " - " + data.getTillDateTime());
                printValue("   Air Quality    ", data.getMeasurement().getAirQualityIndex(), "");
                printValue("    Humidity      ", data.getMeasurement().getHumidity(), " [%]");
                printValue("     Pm1          ", data.getMeasurement().getPm1(), " [μg/m3]");
                printValue("     Pm10         ", data.getMeasurement().getPm10()," [μg/m3]");
                printValue("     Pm25         ", data.getMeasurement().getPm25()," [μg/m3]");
                printValue(" Pollution Level  ", data.getMeasurement().getPollutionLevel(), "");
                anythingDrownBefore = true;
            } else {
                drawEmptyLineWith("Airly did not provide full data of history.");
                break;
            }
        }
        drawEndingFrame();
    }

    public void printSensorResponse() throws Exception{
        if (sensor == null)
            throw new Exception("Sensor data is not available, because sensor is not specified.");
        this.lengthOfLine = 35;
        drawBeginningFrame();
        printValue("   Sensor ID    ", Integer.toString(sensor.getId()),"");
        printValue("     Area       ", sensor.getAddress().getLocality(),"");
        printValue("    Street      ", sensor.getAddress().getRoute(),"");
        printValue("    Country     ", sensor.getAddress().getCountry(),"");
        printValue("    Vendor      ", sensor.getVendor(),"");
        printValue("   Longitude    ", Double.toString(sensor.getLocation().getLongitude()),"°");
        printValue("   Latitude     ", Double.toString(sensor.getLocation().getLatitude()),"°");
        drawEndingFrame();
    }


    private void printAirly() {
        System.out.println("     ___       __  .______       __      ____    ____ ");
        System.out.println("    /   \\     |  | |   _  \\     |  |     \\   \\  /   / ");
        System.out.println("   /  ^  \\    |  | |  |_)  |    |  |      \\   \\/   /  ");
        System.out.println("  /  /_\\  \\   |  | |      /     |  |       \\_    _/   ");
        System.out.println(" /  _____  \\  |  | |  |\\  \\----.|  `----.    |  |     ");
        System.out.println("/__/     \\__\\ |__| | _| `._____||_______|    |__|     ");
        System.out.println("                                                      ");
    }

    private void printValue(String name, String value, String unit) {
        if (value == null) {
            value = "Information was not provided.";
            unit = "";
        }
        value = roundIfDouble(value);
        String informationToShow = "║"+name+value+unit;
        System.out.print(informationToShow);
        for (int i = informationToShow.length(); i <= lengthOfLine; i++)
            System.out.print(" ");
        System.out.print("║\n");
    }

    private void drawBeginningFrame() {
        System.out.print("╔");
        for (int i = 0; i < lengthOfLine; i++)
            System.out.print("=");
        System.out.print("╗\n");
    }

    private void drawEndingFrame() {
        System.out.print("╚");
        for (int i = 0; i < lengthOfLine; i++)
            System.out.print("=");
        System.out.print("╝\n");
    }

    private void drawEmptyLineWith(String line) {
        System.out.print("║"+line);
        for (int i = 0; i < lengthOfLine - line.length(); i++)
            System.out.print(" ");
        System.out.print("║\n");
    }

    private String roundIfDouble(String value) {
        if(Pattern.matches("^[-+]?[0-9]*\\.?[0-9]+$", value) && !Pattern.matches("^[-+]?[0-9]*$", value)){
            Double rounding = Math.round(100 * Double.parseDouble(value))/100.0;
            return rounding.toString();
        }
        else
            return value;
    }
}
