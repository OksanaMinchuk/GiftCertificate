package util;

import com.github.javafaker.Faker;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.creator.BookJSONCreator;
import util.creator.GiftCertificateJSONCreator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileCreator implements Runnable {

    private static final Logger LOGGER = Logger.getRootLogger();
    private static final int WRONG_JSON_FORMAT_RANGE = 17;
    private static final int WRONG_FIELD_NAME_RANGE = 18;
    private static final int WRONG_NON_VALID_BEAN_RANGE = 19;
    private static final int WRONG_DB_CONSTRAINTS_RANGE = 20;

    private final int CERTIFICATES_COUNT = ConstantConfigurator.CERTIFICATES_COUNT;
    private final String FILE_EXTENTION = ".json";

    private Faker faker = new Faker();
    private List<File> directories;

    private int folderIndex;

    public FileCreator() {
    }

    public void setFolderIndex(int folderIndex) {
        this.folderIndex = folderIndex;
    }

    public void setDirectories(List<File> directories) {
        this.directories = directories;
    }

    @Override
    public void run() {
        LOGGER.debug("Thread " + folderIndex + " started");
        for (int i = 1; i <= ConstantConfigurator.FILES_COUNT; i++) {
            createFile(i);
        }
        LOGGER.debug("Thread " + folderIndex + " finished");
    }

    private void createFile(int fileNumber) {

        FileCategory fileCategory = generateFileCategoryByFileNumber(fileNumber);
        Path currentDirPath = directories.get(folderIndex).toPath();

        switch (fileCategory) {
            case WRONG_JSON_FORMAT_TYPE:
                createWrongJsonFormatFile(currentDirPath);
                break;
            case WRONG_FIELD_NAME_TYPE:
                createWrongFieldNameFile(currentDirPath);
                break;
            case WRONG_NON_VALID_BEAN_TYPE:
                createWrongBeanFile(currentDirPath);
                break;
            case WRONG_DB_CONSTRAINTS_TYPE:
                createWrongDBConstraintsFile(currentDirPath);
                break;
            case VALID_FILE_TYPE:
                createValidFile(currentDirPath);
        }
    }

    private FileCategory generateFileCategoryByFileNumber(int fileNumber) {
        FileCategory fileCategory = FileCategory.VALID_FILE_TYPE;
        if (fileNumber % WRONG_JSON_FORMAT_RANGE == 0) {
            fileCategory = FileCategory.WRONG_JSON_FORMAT_TYPE;
        } else if (fileNumber % WRONG_FIELD_NAME_RANGE == 0) {
            fileCategory = FileCategory.WRONG_FIELD_NAME_TYPE;
        } else if (fileNumber % WRONG_NON_VALID_BEAN_RANGE == 0) {
            fileCategory = FileCategory.WRONG_NON_VALID_BEAN_TYPE;
        } else if (fileNumber % WRONG_DB_CONSTRAINTS_RANGE == 0) {
            fileCategory = FileCategory.WRONG_DB_CONSTRAINTS_TYPE;
        }
        return fileCategory;
    }

    private void createWrongJsonFormatFile(Path currentDirPath) {
        Path fileToCreatePath =
                currentDirPath.resolve(WRONG_JSON_FORMAT_RANGE + " " +
                        faker.address().cityName()
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss-SSS"))
                        + FILE_EXTENTION);
        try {
            Path newFilePath = Files.createFile(fileToCreatePath);
            OutputStream outputStream = Files.newOutputStream(newFilePath);

            String wrongJsonString = "";
            for (int j = 0; j < CERTIFICATES_COUNT; j++) {
                JSONObject jsonObject = GiftCertificateJSONCreator.createValidGiftCertificate();
                wrongJsonString += jsonObject.toString() + ",";
            }
            wrongJsonString = "[" + wrongJsonString.substring(0, wrongJsonString.length() - 1).replace("{", "+") + "]";

            outputStream.write(wrongJsonString.getBytes());

        } catch (IOException e) {
            LOGGER.error("FileCreator: createNonValidFile: " + e.getMessage());
        }
    }

    private void createWrongFieldNameFile(Path currentDirPath) {
        Path fileToCreatePath =
                currentDirPath.resolve(WRONG_FIELD_NAME_RANGE + " " +
                        faker.address().cityName()
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss-SSS"))
                        + FILE_EXTENTION);
        try {
            Path newFilePath = Files.createFile(fileToCreatePath);
            OutputStream outputStream = Files.newOutputStream(newFilePath);

            String wrongJsonString = "";
            for (int j = 0; j < CERTIFICATES_COUNT; j++) {
                JSONObject jsonObject = GiftCertificateJSONCreator.createValidGiftCertificate();
                wrongJsonString += jsonObject.toString() + ",";
            }
            wrongJsonString = "[" + wrongJsonString.substring(0, wrongJsonString.length() - 1).replace("description", "about") + "]";

            outputStream.write(wrongJsonString.getBytes());

        } catch (IOException e) {
            LOGGER.error("FileCreator: createNonValidFile: " + e.getMessage());
        }
    }

    private void createWrongBeanFile(Path currentDirPath) {
        Path fileToCreatePath =
                currentDirPath.resolve(WRONG_NON_VALID_BEAN_RANGE + " " +
                        faker.address().cityName()
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss-SSS"))
                        + FILE_EXTENTION);
        try (RandomAccessFile randomAccessFile =
                     new RandomAccessFile(
                             fileToCreatePath.toString(),
                             "rw"); FileChannel fc = randomAccessFile.getChannel(); FileLock fileLock = fc.tryLock()) {
            if (fileLock != null) {

                JSONArray jsonArray = new JSONArray();

                jsonArray.add(GiftCertificateJSONCreator.createValidGiftCertificate());
                jsonArray.add(BookJSONCreator.createBook());
                jsonArray.add(GiftCertificateJSONCreator.createValidGiftCertificate());

                ByteBuffer buffer = ByteBuffer.wrap(jsonArray.toJSONString().getBytes());
                buffer.put(jsonArray.toJSONString().getBytes());

                buffer.flip();
                while (buffer.hasRemaining()) {
                    fc.write(buffer);
                }
            }
        } catch (OverlappingFileLockException | IOException ex) {
            LOGGER.error(
                    "Exception occured while trying to get a lock on File... " +
                            ex.getMessage());
        }
    }

    private void createWrongDBConstraintsFile(Path currentDirPath) {
        Path fileToCreatePath =
                currentDirPath.resolve(WRONG_DB_CONSTRAINTS_RANGE + " " +
                        faker.address().cityName()
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss-SSS"))
                        + FILE_EXTENTION);
        try (RandomAccessFile randomAccessFile =
                     new RandomAccessFile(
                             fileToCreatePath.toString(),
                             "rw"); FileChannel fc = randomAccessFile.getChannel(); FileLock fileLock = fc.tryLock()) {
            if (fileLock != null) {

                JSONArray jsonArray = new JSONArray();

                jsonArray.add(GiftCertificateJSONCreator.createValidGiftCertificate());
                jsonArray.add(GiftCertificateJSONCreator.createNonValidGiftCertificate());
                jsonArray.add(GiftCertificateJSONCreator.createValidGiftCertificate());

                ByteBuffer buffer = ByteBuffer.wrap(jsonArray.toJSONString().getBytes());
                buffer.put(jsonArray.toJSONString().getBytes());

                buffer.flip();
                while (buffer.hasRemaining()) {
                    fc.write(buffer);
                }
            }
        } catch (OverlappingFileLockException | IOException ex) {
            LOGGER.error(
                    "Exception occured while trying to get a lock on File... " +
                            ex.getMessage());
        }
    }

    private void createValidFile(Path currentDirPath) {
        Path fileToCreatePath =
                currentDirPath.resolve(
                        faker.address().cityName() + " "
                                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss-SSS"))
                                + FILE_EXTENTION);
        try (RandomAccessFile randomAccessFile =
                     new RandomAccessFile(
                             fileToCreatePath.toString(),
                             "rw"); FileChannel fc = randomAccessFile.getChannel(); FileLock fileLock = fc.tryLock()) {
            if (fileLock != null) {

                JSONArray jsonArray = new JSONArray();
                for (int j = 0; j < CERTIFICATES_COUNT; j++) {
                    jsonArray.add(GiftCertificateJSONCreator.createValidGiftCertificate());
                }

                ByteBuffer buffer = ByteBuffer.wrap(jsonArray.toJSONString().getBytes());
                buffer.put(jsonArray.toJSONString().getBytes());

                buffer.flip();
                while (buffer.hasRemaining()) {
                    fc.write(buffer);
                }
            }
        } catch (OverlappingFileLockException | IOException ex) {
            LOGGER.error(
                    "Exception occured while trying to get a lock on File... " +
                            ex.getMessage());
        }
    }

}
