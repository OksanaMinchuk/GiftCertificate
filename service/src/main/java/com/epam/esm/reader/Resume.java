package com.epam.esm.reader;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.ConstantConfigurator;

import java.io.File;
import java.util.List;

@Component
public class Resume {

  @Autowired
  private GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> giftCertificateService;

  public int getActualCountErrorFiles() {
    FileDirectoryHandler fileDirectoryHandler = new FileDirectoryHandler();
    List<File> errorsFileList =
        fileDirectoryHandler.createFilesList(new File(ConstantConfigurator.ERRORS_DIRECTORY_PATH));
    return errorsFileList.size();
  }

  public int getActualCountRows() {
    return giftCertificateService.getCountGiftCertificates(null, null, null).intValue();
  }

}
