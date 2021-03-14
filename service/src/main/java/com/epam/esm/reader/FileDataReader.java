package com.epam.esm.reader;

import com.epam.esm.reader.util.Counter;
import com.epam.esm.reader.util.GiftCertificateDTOValidator;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import util.ConstantConfigurator;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.Callable;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileDataReader implements Callable<Path> {

    private static final Logger LOGGER = Logger.getRootLogger();

    private Path errorDirPath = Paths.get(ConstantConfigurator.ERRORS_DIRECTORY_PATH);

    @Autowired
    private GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> giftCertificateService;

    private File currentFile;

    public FileDataReader() {
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    @Override
    public Path call() {
        LOGGER.debug("Thread FileDataReader start for: " + currentFile.getName());

        Path currentFilePath = null;

        if (currentFile.exists()) {
            currentFilePath = currentFile.toPath();
            List<String> jsonStringList;
            try {
                ObjectMapper mapper = new ObjectMapper();

                jsonStringList = Files.readAllLines(currentFilePath);
                List<GiftCertificateDTO> giftCertificateDTOList = mapper.readValue(jsonStringList.get(0), new TypeReference<List<GiftCertificateDTO>>() {
                });

                if (isValidGiftCertificateList(giftCertificateDTOList)) {
                    Counter.increaseValidFilesCount();
                    Files.delete(currentFilePath);
                    giftCertificateDTOList.forEach(dto -> giftCertificateService.create(dto));
                } else {
                    Counter.increaseInValidFilesCount();
                    Files.move(
                            currentFilePath,
                            errorDirPath.resolve(currentFile.getName()),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                Counter.increaseInValidFilesCount();
                LOGGER.error("FileDataReader: call method: " + e.getMessage());
                try {
                    Files.move(
                            currentFilePath,
                            errorDirPath.resolve(currentFile.getName()),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    LOGGER.error("FileDataReader: call method: " + e.getMessage());
                }
            }
        }
        LOGGER.debug("Thread FileDataReader finished for: " + currentFile.getName());
        return currentFilePath;
    }

    private boolean isValidGiftCertificateList(List<GiftCertificateDTO> giftCertificateDTOList) {
        return giftCertificateDTOList.stream().allMatch(GiftCertificateDTOValidator::isValid);
    }
}
