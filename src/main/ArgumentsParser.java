package main;

import java.util.regex.Pattern;

public class ArgumentsParser {

    private ModeType type = null;
    private String key = null;
    private String sensor_id = null;
    private Double longitude = null;
    private Double latitude = null;
    private String[] inputArguments;

    ArgumentsParser (String[] args) throws Exception {
        inputArguments = args;
        for (int index = 0; index < inputArguments.length; ) {
            String argument = inputArguments[index];
            switch (argument) {
                case "-c":
                    setCoordinates(index);
                    index += 3;
                    break;
                case "-s":
                    setSensor_id(index);
                    index += 2;
                    break;
                case "-k":
                    setKey(index);
                    index += 2;
                    break;
                case "-h":
                    index += 1;
                    break;
                default:
                    throw new Exception("Given parameters have incorrect syntax. Please check your arguments or execute program with option -help.");
            }
        }
        if (key == null)
            setKeyFromEnvironment();
        setType();
    }

    public String getKey() {
        return key;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getCoordinates() {
        return (getLongitude().toString()+" "+getLatitude().toString());
    }

    public ModeType getType() {
        return type;
    }

    private void setCoordinates(int index) throws Exception {
        if (index + 2 >= inputArguments.length)
            throw new Exception("Not enough coordinates in the input after -c.");
        String pattern = "^[+-]?[0-9]*\\.?[0-9]+$";
        if (!Pattern.matches(pattern,inputArguments[index+1]))
            throw new Exception("First coordinate (longitude) is not a double number.");
        if (!Pattern.matches(pattern,inputArguments[index+2]))
            throw new Exception("Second coordinate (latitude) is not a double number.");
        longitude = Double.parseDouble(inputArguments[index+1]);
        latitude  = Double.parseDouble(inputArguments[index+2]);
        if(! (longitude > 14.125 && longitude < 23.940 && latitude < 54.804 && latitude > 49.178))
            throw new Exception("Coordinates out of Poland are not expected to be proceeded correctly.");
    }

    private void setSensor_id(int index) throws Exception {
        if (index + 1 >= inputArguments.length)
            throw new Exception("There is no sensor_id after -s.");
        sensor_id = inputArguments[index+1];
    }

    private void setKey(int index) throws Exception {
        if (index + 1 >= inputArguments.length)
            throw new Exception("There is no key after -k.");
        key = inputArguments[index+1];
    }

    private void setType() throws Exception {
        for (String argument : inputArguments)
            if (argument.equals("-h"))
                type = ModeType.history;
        if (type == null) {
            for (String argument : inputArguments)
                if (argument.equals("-c"))
                    type = ModeType.current;
        }
        if (type == null) {
            for (String argument : inputArguments)
                if (argument.equals("-s"))
                    type = ModeType.sensor;
        }
        if (type == null)
            throw new Exception("The program could not resolve action from given arguments. ");
    }

    private void setKeyFromEnvironment() throws Exception {
        if(System.getenv("API_KEY") != null)
            key = System.getenv("API_KEY");
        else
            throw new Exception("There is no API_KEY in arguments or system variables.");
    }
}
