package com.epam.esm.util;

import com.epam.esm.db.GiftCertificateRepository;
import com.epam.esm.db.UserRepository;
import com.epam.esm.db.specification.impl.gift_certificate.GetGiftCertificateByIdSpecification;
import com.epam.esm.db.specification.impl.user.GetUserByIdSpecification;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.PurchaseDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseConverter {

  private UserRepository<User, Long> userRepository;
  private UserService<User, UserDTO, Long> userService;
  private GiftCertificateRepository<GiftCertificate, Long> giftCertificateRepository;
  private GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> giftCertificateService;

  @Autowired
  public PurchaseConverter(
      UserRepository<User, Long> userRepository,
      UserService<User, UserDTO, Long> userService,
      GiftCertificateRepository<GiftCertificate, Long> giftCertificateRepository,
      GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> giftCertificateService) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.giftCertificateRepository = giftCertificateRepository;
    this.giftCertificateService = giftCertificateService;
  }

  public Purchase convertDtoToEntity(PurchaseDTO purchaseDTO) throws DataServiceException {
    if (purchaseDTO != null) {
      Purchase purchase = new Purchase();
      purchase.setId(purchaseDTO.getId());
      purchase.setPrice(
          giftCertificateRepository
              .getEntityBySpecification(
                  new GetGiftCertificateByIdSpecification(
                      purchaseDTO.getGiftCertificateDTO().getId()))
              .getPrice());
      purchase.setTimestamp(purchaseDTO.getTimestamp());
      purchase.setUser(
          userRepository.getEntityBySpecification(
              new GetUserByIdSpecification(purchaseDTO.getUserDTO().getId())));
      purchase.setGiftCertificate(
          giftCertificateRepository.getEntityBySpecification(
              new GetGiftCertificateByIdSpecification(
                  purchaseDTO.getGiftCertificateDTO().getId())));
      return purchase;
    } else {
      throw new DataServiceException("PurchaseDTO object is null.");
    }
  }

  public PurchaseDTO convertEntityToDto(Purchase purchase) throws DataServiceException {
    if (purchase != null) {
      PurchaseDTO purchaseDTO = new PurchaseDTO();
      purchaseDTO.setId(purchase.getId());
      purchaseDTO.setPrice(purchase.getPrice());
      purchaseDTO.setTimestamp(purchase.getTimestamp());
      purchaseDTO.setUserDTO(userService.getById(purchase.getUser().getId()));
      purchaseDTO.setGiftCertificateDTO(
          giftCertificateService.getById(purchase.getGiftCertificate().getId()));
      return purchaseDTO;
    } else {
      throw new DataServiceException("Purchase object is null.");
    }
  }
}
