package util;

import com.github.javafaker.Faker;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DirectoryCreator {

    private static final Logger LOGGER = Logger.getRootLogger();
    private static final int SUBFOLDERS_DEPTH = 3;
    private Faker faker = new Faker();
    private List<File> directories = new ArrayList<>();

    public List<File> createDirectoriesList(File dir) {
        File[] files = dir.listFiles();
        Arrays.stream(files).forEach(file -> {
            if (file.isDirectory()) {
                directories.add(file);
                createDirectoriesList(file);
            }
        });
        return directories;
    }

    public List<File> createDirectories() {
        String ROOT_DIR_PATH =
                ConstantConfigurator.MAIN_DIRECTORY_PATH + File.separator + faker.country().name() + new Random().nextInt(100);

        List<String> pathsLevel_2 = new ArrayList<>();
        for (int i = 0; i < SUBFOLDERS_DEPTH; i++) {
            pathsLevel_2.add(ROOT_DIR_PATH + File.separator + faker.country().name() + new Random().nextInt(100));
        }

        int subfolderCountOnLevel_2 = pathsLevel_2.size();
        int subfolderCountOnLevel_3 = ConstantConfigurator.SUBFOLDERS_COUNT - pathsLevel_2.size();

        List<Path> pathsLevel_3 = new ArrayList<>();
        pathsLevel_3.add(Paths.get(ROOT_DIR_PATH));

        for (int i = 0; i < subfolderCountOnLevel_2; i++) {
            for (int j = 0; j < subfolderCountOnLevel_3 / subfolderCountOnLevel_2; j++) {
                pathsLevel_3.add(Paths.get(pathsLevel_2.get(i) + File.separator + faker.country().name() + new Random().nextInt(100)));
            }
        }

        try {
            for (Path path: pathsLevel_3) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            LOGGER.error("DirectoryCreator: createDirectories method: " + e.getMessage());
        }

        directories.add(new File(new File(pathsLevel_2.get(0)).getParent()));
        return createDirectoriesList(new File(new File(pathsLevel_2.get(0)).getParent()));
    }

}
