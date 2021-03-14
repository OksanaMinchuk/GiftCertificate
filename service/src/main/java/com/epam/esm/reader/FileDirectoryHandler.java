package com.epam.esm.reader;

import org.apache.log4j.Logger;
import util.ConstantConfigurator;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDirectoryHandler {

    private static final Logger LOGGER = Logger.getRootLogger();

    private Path errorDirPath = Paths.get(ConstantConfigurator.ERRORS_DIRECTORY_PATH);
    private List<File> filesList = new ArrayList<>();

    public List<File> createFilesList(File dir) {
        File[] files = dir.listFiles();
        Arrays.stream(files).forEach(file -> {
            if (file.isDirectory()) {
                createFilesList(file);
            } else {
                filesList.add(file);
            }
        });
        return filesList;
    }

    public void createErrorDirectory() {
        try {
            Files.createDirectories(errorDirPath);
        } catch (IOException e) {
            LOGGER.error("FileDataReader: createErrorDirectory: " + e.getMessage());
        }
    }
}
