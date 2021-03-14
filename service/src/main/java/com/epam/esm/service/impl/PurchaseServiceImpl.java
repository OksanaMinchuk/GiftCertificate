package com.epam.esm.service.impl;

import com.epam.esm.db.PurchaseRepository;
import com.epam.esm.db.UserRepository;
import com.epam.esm.db.specification.impl.purchase.GetPurchaseByIdSpecification;
import com.epam.esm.db.specification.impl.purchase.GetPurchaseListByUserIdSpecification;
import com.epam.esm.db.specification.impl.purchase.GetPurchaseListSpecification;
import com.epam.esm.db.specification.impl.user.GetUserByIdSpecification;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PurchaseDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.PurchaseService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.PurchaseConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService<Purchase, PurchaseDTO, Long> {

  private static final Logger LOGGER = Logger.getRootLogger();

  private UserService<User, UserDTO, Long> userService;
  private UserRepository<User, Long> userRepository;
  private GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> giftCertificateService;
  private PurchaseRepository<Purchase, Long> purchaseRepository;
  private PurchaseConverter purchaseConverter;

  @Autowired
  public PurchaseServiceImpl(
      UserService<User, UserDTO, Long> userService,
      GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> giftCertificateService,
      PurchaseRepository<Purchase, Long> purchaseRepository,
      PurchaseConverter purchaseConverter,
      UserRepository<User, Long> userRepository) {
    this.userService = userService;
    this.giftCertificateService = giftCertificateService;
    this.purchaseRepository = purchaseRepository;
    this.purchaseConverter = purchaseConverter;
    this.userRepository = userRepository;
  }

  @Override
  public PurchaseDTO createPurchase(OrderDTO orderDTO) throws DataServiceException {
    LOGGER.debug("PurchaseServiceImpl : create method for order:" + orderDTO);

    if (orderDTO.getUserId() <= 0 || orderDTO.getGiftCertificateId() <= 0) {
      throw new DataServiceException("Incorrect value for id.");
    }
    PurchaseDTO purchaseDTO = new PurchaseDTO();
    purchaseDTO.setUserDTO(userService.getById(orderDTO.getUserId()));
    purchaseDTO.setGiftCertificateDTO(
        giftCertificateService.getById(orderDTO.getGiftCertificateId()));

    Purchase purchase = purchaseConverter.convertDtoToEntity(purchaseDTO);
    purchase = purchaseRepository.create(purchase);
    return purchaseConverter.convertEntityToDto(purchase);
  }

  @Override
  public PurchaseDTO create(PurchaseDTO purchaseDTO) throws DataServiceException {
    throw new DataServiceException("Not supported method create(PurchaseDTO entity).");
  }

  @Override
  public void update(PurchaseDTO purchaseDTO) throws DataServiceException {
    throw new DataServiceException("Not supported method updateEntity(PurchaseDTO purchaseDTO).");
  }

  @Override
  public void delete(Long id) {
    LOGGER.debug("PurchaseServiceImpl : delete method");

    Purchase purchase =
        purchaseRepository.getEntityBySpecification(new GetPurchaseByIdSpecification(id));
    if (purchase == null) {
      throw new EntityNotFoundException("Purchase for id: " + id + " not found.");
    }
    purchaseRepository.delete(purchase);
  }

  @Override
  public PurchaseDTO getById(Long id) throws DataServiceException {
    LOGGER.debug("PurchaseServiceImpl : getEntityBySpecification method");

    Purchase purchase =
        purchaseRepository.getEntityBySpecification(new GetPurchaseByIdSpecification(id));
    if (purchase == null) {
      throw new EntityNotFoundException("Purchase for id: " + id + " not found.");
    }
    PurchaseDTO purchaseDTO = purchaseConverter.convertEntityToDto(purchase);
    return purchaseDTO;
  }

  @Override
  public List<PurchaseDTO> getAll(int page, int size) throws DataServiceException {
    LOGGER.debug("PurchaseServiceImpl : getAll <PurchaseDTO> method");

    List<Purchase> purchases =
        purchaseRepository.getListBySpecification(new GetPurchaseListSpecification(), page, size);
    List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
    for (Purchase purchase : purchases) {
      purchaseDTOS.add(purchaseConverter.convertEntityToDto(purchase));
    }
    return purchaseDTOS;
  }

  @Override
  public List<PurchaseDTO> getPurchaseByUserId(Long userId, int page, int size) {
    LOGGER.debug("PurchaseServiceImpl : getPurchaseByUserId <PurchaseDTO> method");

    User user = userRepository.getEntityBySpecification(new GetUserByIdSpecification(userId));
    List<Purchase> purchases =
        purchaseRepository.getListBySpecification(
            new GetPurchaseListByUserIdSpecification(user), page, size);

    List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
    for (Purchase purchase : purchases) {
      purchaseDTOS.add(purchaseConverter.convertEntityToDto(purchase));
    }
    return purchaseDTOS;
  }

  @Override
  public Long getEntityCount() {
    return purchaseRepository.getEntityCount();
  }
}
