package generator;

import org.apache.log4j.Logger;
import util.ConstantConfigurator;

import java.util.Timer;
import java.util.TimerTask;

public class Runner {

    private static final Logger LOGGER = Logger.getRootLogger();
    private static int count = 1;

    public static void main(String[] args) {

        Timer timer = new Timer("Timer");

        TimerTask repeatedTask =
                new TimerTask() {
                    public void run() {
                        if (count > ConstantConfigurator.GENERATE_ITERATION_COUNT) {
                            timer.cancel();
                            LOGGER.debug("... generating finished");
                        } else {
                            MainGenerator mainGenerator = new MainGenerator();
                            mainGenerator.startGenerating();
                            LOGGER.debug("round: " + count++);
                        }
                    }
                };

        timer.scheduleAtFixedRate(
                repeatedTask,
                (long) (ConstantConfigurator.GENERATE_DELAY * 1000),
                (long) (ConstantConfigurator.GENERATE_PERIOD * 1000));

    }
}
