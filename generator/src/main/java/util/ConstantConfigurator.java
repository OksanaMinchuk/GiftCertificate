package util;

import java.util.ResourceBundle;

public class ConstantConfigurator {

    private static ResourceBundle bundle;

    static {
        createConstant();
    }

    public ConstantConfigurator() {
    }

    public static String MAIN_DIRECTORY_PATH;
    public static String ERRORS_DIRECTORY_PATH;
    public static String RESUME_FILE_PATH;
    public static int FILES_COUNT;
    public static double PERIOD_TIME;
    public static int SUBFOLDERS_COUNT;
    public static double SCAN_DELAY;
    public static double SCAN_PERIOD;
    public static int THREAD_COUNT;
    public static int INPUT_DATA_WAIT_PERIOD;
    public static int CERTIFICATES_COUNT;
    public static int GENERATE_DELAY;
    public static int GENERATE_PERIOD;
    public static int GENERATE_ITERATION_COUNT;

    public static void createConstant() {

        bundle = ResourceBundle.getBundle("config");

        MAIN_DIRECTORY_PATH = bundle.getString("MAIN_DIRECTORY_PATH");
        ERRORS_DIRECTORY_PATH = bundle.getString("ERRORS_DIRECTORY_PATH");
        RESUME_FILE_PATH = bundle.getString("RESUME_FILE_PATH");
        FILES_COUNT = Integer.parseInt(bundle.getString("FILES_COUNT"));
        PERIOD_TIME = Double.parseDouble(bundle.getString("PERIOD_TIME"));
        SUBFOLDERS_COUNT = Integer.parseInt(bundle.getString("SUBFOLDERS_COUNT"));
        SCAN_DELAY = Double.parseDouble(bundle.getString("SCAN_DELAY"));
        SCAN_PERIOD = Double.parseDouble(bundle.getString("SCAN_PERIOD"));
        THREAD_COUNT = Integer.parseInt(bundle.getString("THREAD_COUNT"));
        INPUT_DATA_WAIT_PERIOD = Integer.parseInt(bundle.getString("INPUT_DATA_WAIT_PERIOD"));
        CERTIFICATES_COUNT = Integer.parseInt(bundle.getString("CERTIFICATES_COUNT"));
        GENERATE_DELAY = Integer.parseInt(bundle.getString("GENERATE_DELAY"));
        GENERATE_PERIOD = Integer.parseInt(bundle.getString("GENERATE_PERIOD"));
        GENERATE_ITERATION_COUNT = Integer.parseInt(bundle.getString("GENERATE_ITERATION_COUNT"));
    }
}
