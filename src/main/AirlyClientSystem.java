package main;

public class AirlyClientSystem {

    public static void main(String[] args) throws Exception {
        try {
            checkArguments(args);
            ArgumentsParser input = new ArgumentsParser(args);
            AirlyProceeder proceeder;
            if (input.getType() == ModeType.sensor)
                proceeder = new AirlyProceeder(input.getKey(),input.getSensor_id(),input.getType());
            else
                proceeder = new AirlyProceeder(input.getKey(),input.getLongitude(),input.getLatitude(),input.getType());
            proceeder.executeRequest();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkArguments(String[] args) throws Exception {
        if (args.length == 0)
            throw new Exception("There are no arguments. Please write appropriate arguments or execute program with option -help.");
        for (String argument : args)
            if (argument.toLowerCase().equals("-help") || argument.toLowerCase().equals("help") || argument.toLowerCase().equals("man")) {
                showHelp();
                System.exit(0);
            }
        if (args.length == 2 && args[0].equals("-k"))
            throw new Exception("Program cannot be called with a single key. Please check your arguments or execute program with option -help.");
        if (!isStructureOfArgumentCorrect(args))
            throw new Exception("Given parameters have incorrect structure. Please check your arguments or execute program with option -help.");
    }

    public static void showHelp() {
        String[] helper = new String[11];
        helper[0] = "This application requires specific format of arguments:";
        helper[1] = "-k _ for specifying API_KEY;";
        helper[2] = "-c _ _ for specifying coordinates, longitude and latitude respectively;";
        helper[3] = "-s _ for specifying sensor;";
        helper[4] = "-h for specifying that the history of measurements should be shown.";
        helper[5] = "API_KEY may be defined either as environmental variable, or provided as an argument to program.";
        helper[6] = "The order of arguments should be like -c, -s, -k, -h.";
        helper[7] = "Correct execution of program requires format of arguments like:";
        helper[8] = "-s 204 -k a53b41056c2c4f6bb39a5541e7b667c2";
        helper[9] = "-c 19.93 50.06 -k a53b41056c2c4f6bb39a5541e7b667c2";
        helper[10] = "-c 19.93 50.06 -k a53b41056c2c4f6bb39a5541e7b667c2 -h";
        for (String help : helper)
            System.out.println(help);
    }

    public static boolean isStructureOfArgumentCorrect (String[] args) {
        if (args.length == 5 && args[0].equals("-c") && args[3].equals("-k"))
            return true;
        if (args.length == 4 && args[0].equals("-s") && args[2].equals("-k"))
            return true;
        if (args.length == 6 && args[0].equals("-c") && args[3].equals("-k") && args[5].equals("-h"))
            return true;
        if (args.length == 2 && args[0].equals("-s"))
            return true;
        if (args.length == 3 && args[0].equals("-s") && args[2].equals("-h"))
            return true;
        return false;
    }
}
