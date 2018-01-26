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
                return;
            }
        if (args.length == 2 && args[0].equals("-k"))
            throw new Exception("Program cannot be called with a single key. Please check your arguments or execute program with option -help.");
        if (!isStructureOfArgumentCorrect(args))
            throw new Exception("Given parameters have incorrect structure. Please check your arguments or execute program with option -help.");
    }

    public static void showHelp() {
        String[] helper = new String[10];
        helper[0] = "This application requires specific format of arguments:";
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
        return false;
    }
}
