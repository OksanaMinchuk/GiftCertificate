package com.epam.esm.reader;

import com.epam.esm.reader.util.Counter;
import com.epam.esm.util.BeanUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import util.ConstantConfigurator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class MainDataReader {

    private static final Logger LOGGER = Logger.getRootLogger();

    private FileDirectoryHandler fileDirectoryHandler;

    private int startCountRows;
    private int startCountErrorFile;

    public MainDataReader() {
    }

    public void startReading() {
        Resume resume = BeanUtil.getBean(Resume.class);
        createErrorDirectory();
        startCountRows = resume.getActualCountRows();
        startCountErrorFile = resume.getActualCountErrorFiles();

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(ConstantConfigurator.THREAD_COUNT);

        Timer timer = new Timer("Timer");

        TimerTask repeatedTask =
                new TimerTask() {

                    public void run() {
                        fileDirectoryHandler = new FileDirectoryHandler();
                        List<File> filesList =
                                fileDirectoryHandler.createFilesList(
                                        new File(ConstantConfigurator.MAIN_DIRECTORY_PATH));
                        LOGGER.debug("start TimerTask for fileList with size: " + filesList.size());

                        List<FileDataReader> fileDataReaderList = new ArrayList<>();

                        if (filesList.size() != 0) {
                            for (File file : filesList) {
                                FileDataReader fileDataReader = BeanUtil.getBean(FileDataReader.class);
                                fileDataReader.setCurrentFile(file);
                                fileDataReaderList.add(fileDataReader);
                            }
                            try {
                                executor.invokeAll(fileDataReaderList);
                            } catch (InterruptedException e) {
                                LOGGER.error("MainDataReader: startReading: " + e.getMessage());
                            }

                        } else {
                            LOGGER.debug("waiting data...");
                            try {
                                Thread.sleep((long) (ConstantConfigurator.INPUT_DATA_WAIT_PERIOD * 1000));
                                {
                                    filesList =
                                            fileDirectoryHandler.createFilesList(
                                                    new File(ConstantConfigurator.MAIN_DIRECTORY_PATH));
                                    if (filesList.size() == 0) {
                                        createResume();
                                    }
                                }
                            } catch (InterruptedException e) {
                                LOGGER.debug("MainDataReader: startReading: " + e.getMessage());
                            }
                        }
                    }
                };

        timer.scheduleAtFixedRate(
                repeatedTask,
                (long) (ConstantConfigurator.SCAN_DELAY * 1000),
                (long) (ConstantConfigurator.SCAN_PERIOD * 1000));
    }

    public void createErrorDirectory() {
        fileDirectoryHandler = new FileDirectoryHandler();
        fileDirectoryHandler.createErrorDirectory();
    }

    public void createResume() {
        LOGGER.debug("createResume...");

        int validFiles = Counter.getValidFilesCount();
        int errosFiles = Counter.getInValidFilesCount();

        Resume resume = BeanUtil.getBean(Resume.class);

        int countActualErrorFiles = resume.getActualCountErrorFiles();
        int countActualIncreaseErrorFiles = countActualErrorFiles - startCountErrorFile;
        int countExpectedIncreaseErrorFiles = errosFiles;

        int countActualRows = resume.getActualCountRows();
        int countActualIncreaseRows = countActualRows - startCountRows;
        int countExpectedIncreaseRows = validFiles * ConstantConfigurator.CERTIFICATES_COUNT;

        createResumeFile(validFiles, errosFiles, countActualIncreaseErrorFiles, countExpectedIncreaseErrorFiles, countActualIncreaseRows, countExpectedIncreaseRows);

        LOGGER.info("count handled valid files: " + validFiles);
        LOGGER.info("count handled invalid files: " + errosFiles);

        LOGGER.info("count actual increase of error files: " + countActualIncreaseErrorFiles);
        LOGGER.info("count expected increase of error files: " + countExpectedIncreaseErrorFiles);

        LOGGER.info("count actual increase of DB rows: " + countActualIncreaseRows);
        LOGGER.info("count expected increase of DB rows: " + countExpectedIncreaseRows);
    }

    public void createResumeFile(int validFiles, int errosFiles,
                                 int countActualIncreaseErrorFiles, int countExpectedIncreaseErrorFiles,
                                 int countActualIncreaseRows, int countExpectedIncreaseRows) {

        Path filePath = Paths.get(ConstantConfigurator.RESUME_FILE_PATH + " " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")) + ".txt");
        try {
            Path newFilePath = Files.createFile(filePath);

            OutputStream outputStream = Files.newOutputStream(newFilePath);
            String title = "Resume: " + "\n";
            outputStream.write(title.getBytes());
            outputStream.write("---------------------------------------------------\n".getBytes());

            outputStream.write(("\tcount handled valid files: " + validFiles + "\n").getBytes());
            outputStream.write(("\tcount handled invalid files: " + errosFiles + "\n").getBytes());
            outputStream.write("---------------------------------------------------\n".getBytes());

            outputStream.write(("\tcount actual increase of error files: " + countActualIncreaseErrorFiles + "\n").getBytes());
            outputStream.write(("\tcount expected increase of error files: " + countExpectedIncreaseErrorFiles + "\n").getBytes());
            outputStream.write("---------------------------------------------------\n".getBytes());

            outputStream.write(("\tcount actual increase of DB rows: " + countActualIncreaseRows + "\n").getBytes());
            outputStream.write(("\tcount expected increase of DB rows: " + countExpectedIncreaseRows + "\n").getBytes());
            outputStream.write("---------------------------------------------------\n".getBytes());

        } catch (IOException e) {
            LOGGER.error("MainDataReader: createResumeFile: " + e.getMessage());
        }
    }
}
