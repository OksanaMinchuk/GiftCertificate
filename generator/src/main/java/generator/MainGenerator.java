package generator;

import org.apache.log4j.Logger;
import util.DirectoryCreator;
import util.FileCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainGenerator {

    private static final Logger LOGGER = Logger.getRootLogger();

    private List<File> directories;

    public MainGenerator() {
    }

    public void startGenerating() {

        DirectoryCreator directoryCreator = new DirectoryCreator();
        directories = directoryCreator.createDirectories();

        LOGGER.debug("directories size: " + directories.size());
        for (File dir : directories) {
            LOGGER.debug(dir);
        }

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < directories.size(); i++) {
            FileCreator fileCreator = new FileCreator();
            fileCreator.setDirectories(directories);
            fileCreator.setFolderIndex(i);

            Thread thread = new Thread(fileCreator);
            threads.add(thread);
            thread.start();
        }
    }
}
