package com.epam.esm.service.impl;

import com.epam.esm.db.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.db.impl.PurchaseRepositoryImpl;
import com.epam.esm.db.impl.UserRepositoryImpl;
import com.epam.esm.dto.*;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.Purchase;
import com.epam.esm.model.Tag;
import com.epam.esm.util.PurchaseConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceImplTest {

  @Mock private UserServiceImpl userService;
  @Mock private UserRepositoryImpl userRepository;
  @Mock private GiftCertificateServiceImpl giftCertificateService;
  @Mock private GiftCertificateRepositoryImpl giftCertificateRepository;
  @Mock private PurchaseRepositoryImpl purchaseRepository;
  @Mock private ModelMapper modelMapper;
  @Mock private PurchaseConverter purchaseConverter;

  @InjectMocks private PurchaseServiceImpl purchaseService;

  @Test
  public void createPurchase() {
    OrderDTO orderDTO = new OrderDTO(1L, 5L);

    OrderDTO orderDTOMock = mock(OrderDTO.class);
    when(orderDTOMock.getUserId()).thenReturn(1L);
    when(orderDTOMock.getGiftCertificateId()).thenReturn(5L);

    UserDTO userDTO = new UserDTO();
    when(userService.getById(anyLong())).thenReturn(userDTO);

    GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();
    when(giftCertificateService.getById(5L)).thenReturn(giftCertificateDTO);

    Purchase purchase = new Purchase();
    when(purchaseConverter.convertDtoToEntity(anyObject())).thenReturn(purchase);

    PurchaseDTO purchaseDTO = new PurchaseDTO();
    purchaseDTO.setUserDTO(userDTO);
    purchaseDTO.setGiftCertificateDTO(giftCertificateDTO);

    purchaseService.createPurchase(orderDTO);
    when(purchaseConverter.convertEntityToDto(anyObject())).thenReturn(purchaseDTO);

    verify(purchaseRepository, times(1)).create(anyObject());
  }

  @Test(expected = DataServiceException.class)
  public void testCreate() {
    PurchaseDTO purchaseDTO = new PurchaseDTO();
    purchaseService.create(purchaseDTO);
    verify(purchaseRepository, times(1)).create(anyObject());
  }

  @Test(expected = DataServiceException.class)
  public void testUpdate() {
    PurchaseDTO purchaseDTO = new PurchaseDTO();
    purchaseService.update(purchaseDTO);
    verify(purchaseRepository, times(1)).update(anyObject());
  }

  @Test(expected = EntityNotFoundException.class)
  public void delete() {
    PurchaseDTO purchaseDTO = new PurchaseDTO();
    when(purchaseRepository.getEntityBySpecification(anyObject()))
        .thenThrow(EntityNotFoundException.class);
    purchaseService.delete(purchaseDTO.getId());
    verify(purchaseRepository, times(1)).delete(anyObject());
  }

  @Test(expected = EntityNotFoundException.class)
  public void getById() {
    when(purchaseRepository.getEntityBySpecification(anyObject()))
        .thenThrow(EntityNotFoundException.class);

    PurchaseDTO purchaseDTO = purchaseService.getById(anyLong());
    verify(purchaseRepository, times(1)).getEntityBySpecification(anyObject());
  }

  @Test
  public void getAll() {
    List<Purchase> purchases = new ArrayList<>();
    int page = 1;
    int size = 10;

    when(purchaseRepository.getListBySpecification(anyObject(), eq(page), eq(size)))
        .thenReturn(purchases);

    List<PurchaseDTO> actual = purchaseService.getAll(page, size);

    verify(purchaseRepository, times(1)).getListBySpecification(anyObject(), eq(page), eq(size));
    assertEquals(purchases, actual);
  }
}
