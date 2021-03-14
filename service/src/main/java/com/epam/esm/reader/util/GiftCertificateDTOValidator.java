package com.epam.esm.reader.util;

import com.epam.esm.dto.GiftCertificateDTO;

import java.math.BigDecimal;

public class GiftCertificateDTOValidator {

  private static int MAX_NAME_LENGHT = 30;
  private static int MAX_DESCRIPTION_LENGHT = 500;

  public static boolean isValid(GiftCertificateDTO giftCertificateDTO) {

    boolean result = false;

    if (giftCertificateDTO != null) {
      if (giftCertificateDTO.getName().length() > 0
          && giftCertificateDTO.getName().length() < MAX_NAME_LENGHT
          && giftCertificateDTO.getDescription().length() > 0
          && giftCertificateDTO.getDescription().length() < MAX_DESCRIPTION_LENGHT
          && giftCertificateDTO.getPrice().compareTo(new BigDecimal(0.0)) == 1
          && giftCertificateDTO.getDuration() > 0) {
        result = true;
      }
    }
    return result;
  }
}
