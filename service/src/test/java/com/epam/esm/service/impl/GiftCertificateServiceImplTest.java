package com.epam.esm.service.impl;

import com.epam.esm.db.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.db.impl.TagRepositoryImpl;
import com.epam.esm.db.specification.impl.gift_certificate.GetGiftCertificateByIdSpecification;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.util.EntityDTOConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GiftCertificateServiceImplTest {

  @Mock private GiftCertificateRepositoryImpl giftCertificateRepository;
  @Mock private TagRepositoryImpl tagRepository;
  @Mock private TagServiceImpl tagService;
  @Mock private ModelMapper modelMapper;
  @Mock private EntityDTOConverter entityDTOConverter;

  @InjectMocks private GiftCertificateServiceImpl giftCertificateService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void create() throws DataServiceException {
    Set<TagDTO> tagDTOList = new HashSet<>();
    tagDTOList.add(new TagDTO(1L, "test"));
    GiftCertificateDTO giftCertificateDTO =
        new GiftCertificateDTO(
            1L,
            "education",
            "Free English",
            new BigDecimal(10.5),
            LocalDate.now(),
            LocalDate.now(),
            (byte) 20);
    giftCertificateDTO.setTags(tagDTOList);

    Set<Tag> tagSet = new HashSet<>();
    tagSet.add(new Tag(null, "test"));
    GiftCertificate giftCertificate =
            new GiftCertificate(
                    1L,
                    "education",
                    "Free English",
                    new BigDecimal(10.5),
                    LocalDate.now(),
                    LocalDate.now(),
                    (byte) 20);
    giftCertificate.setTags(tagSet);

    GiftCertificate giftCertificateMock = mock(GiftCertificate.class);
    when(tagRepository.create(anyObject())).thenReturn(new Tag(1L, "test"));

    when(entityDTOConverter.convertDtoToEntity(anyObject(), anyObject()))
            .thenReturn(giftCertificate);
    when(giftCertificateMock.getId()).thenReturn(1L);
    when(giftCertificateMock.getName()).thenReturn("name");
    when(giftCertificateMock.getTags()).thenReturn(tagSet);

    when(giftCertificateRepository.create(anyObject())).thenReturn(new GiftCertificate());

    when(giftCertificateService.create(giftCertificateDTO)).thenReturn(giftCertificateDTO);
    verify(giftCertificateRepository, times(1)).create(anyObject());

  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdate() {
    List<TagDTO> tagList = new ArrayList<>();
    tagList.add(new TagDTO(1L, "test"));
    GiftCertificateDTO giftCertificateDTO =
        new GiftCertificateDTO(
            1L,
            "education",
            "Free English",
            new BigDecimal(10.3),
            LocalDate.now(),
            LocalDate.now(),
            (byte) 20);
    giftCertificateDTO.setTags(new HashSet<>(tagList));

    GiftCertificateDTO giftCertificateDTOMock = mock(GiftCertificateDTO.class);
    when(giftCertificateRepository.getEntityBySpecification(
            new GetGiftCertificateByIdSpecification(anyLong())))
        .thenThrow(EntityNotFoundException.class);
    when(entityDTOConverter.convertDtoToEntity(giftCertificateDTO, new GiftCertificate()))
        .thenReturn(new GiftCertificate());
    when(giftCertificateDTOMock.getId()).thenReturn(new Long(1));
    when(giftCertificateDTOMock.getName()).thenReturn("name");
    when(giftCertificateDTOMock.getTags()).thenReturn(anySetOf(TagDTO.class));

    giftCertificateService.updateEntity(giftCertificateDTO);
    verify(giftCertificateRepository, times(1)).update(anyObject());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testDelete() {
    GiftCertificateDTO giftCertificateDTO =
        new GiftCertificateDTO(
            1L,
            "education",
            "Free English",
            new BigDecimal(10.5),
            LocalDate.now(),
            LocalDate.now(),
            (byte) 20);
    when(giftCertificateRepository.getEntityBySpecification(
            new GetGiftCertificateByIdSpecification(anyLong())))
        .thenThrow(EntityNotFoundException.class);
    giftCertificateService.delete(giftCertificateDTO.getId());
    verify(giftCertificateRepository, times(1)).delete(anyObject());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testGetById() throws DataServiceException {
    when(giftCertificateRepository.getEntityBySpecification(anyObject()))
            .thenThrow(EntityNotFoundException.class);

    GiftCertificateDTO giftCertificateDTO = giftCertificateService.getById(anyLong());
    verify(giftCertificateRepository, times(1))
            .getEntityBySpecification(anyObject());
  }
}
