package com.epam.esm.service.impl;

import com.epam.esm.db.GiftCertificateRepository;
import com.epam.esm.db.TagRepository;
import com.epam.esm.db.specification.impl.gift_certificate.GetGiftCertificateByIdSpecification;
import com.epam.esm.db.specification.impl.gift_certificate.GetGiftCertificateByQuery;
import com.epam.esm.db.specification.impl.gift_certificate.GetGiftCertificateCountByQuery;
import com.epam.esm.db.specification.impl.tag.GetTagByIdSpecification;
import com.epam.esm.db.specification.impl.tag.GetTagListSpecification;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.WrongDataException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.util.EntityDTOConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GiftCertificateServiceImpl
    implements GiftCertificateService<GiftCertificate, GiftCertificateDTO, Long> {

  private static final Logger LOGGER = Logger.getRootLogger();

  private GiftCertificateRepository<GiftCertificate, Long> giftCertificateRepository;
  private TagService<Tag, TagDTO, Long> tagService;
  private EntityDTOConverter entityDTOConverter;
  private TagRepository<Tag, Long> tagRepository;

  @Autowired
  public GiftCertificateServiceImpl(
      GiftCertificateRepository giftCertificateRepository,
      TagService tagService,
      EntityDTOConverter entityDTOConverter,
      TagRepository<Tag, Long> tagRepository) {

    this.giftCertificateRepository = giftCertificateRepository;
    this.tagService = tagService;
    this.entityDTOConverter = entityDTOConverter;
    this.tagRepository = tagRepository;
  }

  @Override
  public GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO)
      throws DataServiceException {
    //LOGGER.debug("GiftCertificateServiceImpl : create method");
    GiftCertificate giftCertificate =
        (GiftCertificate)
            entityDTOConverter.convertDtoToEntity(giftCertificateDTO, new GiftCertificate());
    createNewTagListForCertificate(giftCertificate);
    giftCertificate = giftCertificateRepository.create(giftCertificate);
    return (GiftCertificateDTO)
        entityDTOConverter.convertEntityToDto(giftCertificate, new GiftCertificateDTO());
  }

  @Override
  public GiftCertificateDTO updateEntity(GiftCertificateDTO giftCertificateDTO) {
    LOGGER.debug(
        "GiftCertificateServiceImpl : updateEntity method for id: " + giftCertificateDTO.getId());

    if (getById(giftCertificateDTO.getId()) == null) {
      throw new EntityNotFoundException(
          "GiftCertificate for id: " + giftCertificateDTO.getId() + " not found.");
    }

    GiftCertificate giftCertificate =
        (GiftCertificate)
            entityDTOConverter.convertDtoToEntity(giftCertificateDTO, new GiftCertificate());

    giftCertificate.setDateOfCreation(getById(giftCertificateDTO.getId()).getDateOfCreation());
    giftCertificate.setDateOfModification(LocalDate.now());

    createNewTagListForCertificate(giftCertificate);
    giftCertificateRepository.update(giftCertificate);
    return (GiftCertificateDTO)
        entityDTOConverter.convertEntityToDto(giftCertificate, new GiftCertificateDTO());
  }

  @Override
  public void update(GiftCertificateDTO entity) throws DataServiceException {
    throw new DataServiceException("Not supported method update(GiftCertificateDTO entity).");
  }

  @Override
  public GiftCertificateDTO updateSingleField(GiftCertificateDTO giftCertificateDTO) {
    LOGGER.debug(
        "GiftCertificateServiceImpl : updateSingleField method for id: "
            + giftCertificateDTO.getId());

    GiftCertificateDTO updatedGiftCertificateDTO = getById(giftCertificateDTO.getId());

    if (updatedGiftCertificateDTO == null) {
      throw new EntityNotFoundException(
          "GiftCertificate for id: " + giftCertificateDTO.getId() + " not found.");
    }

    if (giftCertificateDTO.getName() != null) {
      if (giftCertificateDTO.getName().length() > 0
              && giftCertificateDTO.getName().length() < 30) {
        updatedGiftCertificateDTO.setName(giftCertificateDTO.getName());
      } else {
        throw new DataServiceException("Name size should be from 1 to 30 symbols");
      }
    }

    if (giftCertificateDTO.getDescription() != null) {
      if (giftCertificateDTO.getDescription().length() > 0
          && giftCertificateDTO.getDescription().length() < 500) {
        updatedGiftCertificateDTO.setDescription(giftCertificateDTO.getDescription());
      } else {
        throw new DataServiceException("Description size should be from 1 to 500 symbols");
      }
    }

    if (giftCertificateDTO.getPrice() != null) {
      if (giftCertificateDTO.getPrice().compareTo(new BigDecimal(0.0)) == 1) {
        updatedGiftCertificateDTO.setPrice(giftCertificateDTO.getPrice());
      } else {
        throw new DataServiceException("Price should not be less than 0.0");
      }
    }

    if (giftCertificateDTO.getDuration() != null) {
      if (giftCertificateDTO.getDuration() > 0) {
        updatedGiftCertificateDTO.setDuration(giftCertificateDTO.getDuration());
      } else {
        throw new DataServiceException("Duration should not be less than 1");
      }
    }

    if (!giftCertificateDTO.getTags().isEmpty()) {
      updatedGiftCertificateDTO.setTags(giftCertificateDTO.getTags());
    }

    return updateEntity(updatedGiftCertificateDTO);
  }

  @Override
  public void delete(Long id) {
    LOGGER.debug("GiftCertificateServiceImpl : delete method");
    GiftCertificate giftCertificate =
        giftCertificateRepository.getEntityBySpecification(
            new GetGiftCertificateByIdSpecification(id));

    if (giftCertificate == null) {
      throw new EntityNotFoundException("GiftCertificate for id: " + id + " not found.");
    }
    giftCertificateRepository.delete(giftCertificate);
  }

  @Override
  public GiftCertificateDTO getById(Long id) {
    LOGGER.debug("GiftCertificateServiceImpl : getById method");

    GiftCertificate giftCertificate =
        giftCertificateRepository.getEntityBySpecification(
            new GetGiftCertificateByIdSpecification(id));

    if (giftCertificate == null) {
      throw new EntityNotFoundException("GiftCertificate for id: " + id + " not found.");
    }

    GiftCertificateDTO giftCertificateDTO =
        (GiftCertificateDTO)
            entityDTOConverter.convertEntityToDto(giftCertificate, new GiftCertificateDTO());
    return giftCertificateDTO;
  }

  @Override
  public List<GiftCertificateDTO> getByTag(
      String tag, List<GiftCertificateDTO> giftCertificateDTOS) {

    TagDTO tagDTO = tagService.getByName(tag);

    List<GiftCertificateDTO> resultGiftCertificateDTOS = new ArrayList<>();
    for (int i = 0; i < giftCertificateDTOS.size(); i++) {
      if (giftCertificateDTOS.get(i).getTags().contains(tagDTO)) {
        resultGiftCertificateDTOS.add(giftCertificateDTOS.get(i));
      }
    }
    return resultGiftCertificateDTOS;
  }

  @Override
  public List<GiftCertificateDTO> getListByQuery(
      List<String> tagList, String queryPart, String sortBy, int page, int size) {
    LOGGER.debug("GiftCertificateServiceImpl : getListByQuery method");
    String tagListString = null;
    if (tagList != null) {
      List<Tag> existTags = tagRepository.getAllListBySpecification(new GetTagListSpecification());
      int count = 0;
      for (String wantedTag : tagList) {
        for (Tag tag : existTags) {
          if (tag.getName().equals(wantedTag)) {
            count++;
            break;
          }
        }
        if (count == 0) {
          throw new EntityNotFoundException("Tag for name: " + wantedTag + " not found.");
        }
      }
      tagListString = tagList.stream().collect(Collectors.joining(","));
    }

    List<GiftCertificate> giftCertificates =
        giftCertificateRepository.getBySQLQuery(
            new GetGiftCertificateByQuery(tagListString, queryPart, sortBy), page, size);

    List<GiftCertificateDTO> giftCertificateDTOList = getGiftCertificateDTOList(giftCertificates);
    return giftCertificateDTOList;
  }

  /**
   * Converts GiftCertificate List to GiftCertificateDTO List
   *
   * @param giftCertificates
   * @return getGiftCertificateDTOList
   */
  private List<GiftCertificateDTO> getGiftCertificateDTOList(
      List<GiftCertificate> giftCertificates) {
    LOGGER.debug("GiftCertificateServiceImpl : getGiftCertificateDTOList private method");

    if (giftCertificates != null) {
      List<GiftCertificateDTO> giftCertificateDTOList = new ArrayList<>();

      for (GiftCertificate giftCertificate : giftCertificates) {
        giftCertificateDTOList.add(
            (GiftCertificateDTO)
                entityDTOConverter.convertEntityToDto(giftCertificate, new GiftCertificateDTO()));
      }
      return giftCertificateDTOList;
    } else {
      throw new DataServiceException("GiftCertificates not found.");
    }
  }

  /**
   * The method checks if there is a tag with the given name in the database and returns its id.
   *
   * @param name
   * @return tag id if name presents or 0 if not present
   */
  private long getPresentTagId(String name) {
    if (name.length() < 1 || name.length() > 30) {
      throw new WrongDataException("Tag name should be from 1 to 30 symbols");
    }
    List<Tag> existTagList = tagRepository.getAllListBySpecification(new GetTagListSpecification());
    long id;
    try {
      Optional<Tag> tag =
          existTagList.stream().filter(existTag -> existTag.getName().equals(name)).findAny();
      id = tag.get().getId();
    } catch (NoSuchElementException e) {
      id = 0;
    }
    return id;
  }

  /**
   * The method returns tags list for present certificate
   *
   * @param giftCertificate
   */
  private void createNewTagListForCertificate(GiftCertificate giftCertificate) {

    Set<Tag> tagsList = giftCertificate.getTags();
    Set<Tag> newTagsList = new HashSet<>();

    if (!tagsList.isEmpty()) {

      for (Tag newTag : tagsList) {
        if (newTag.getId() == null && newTag.getName() == null) {
          throw new WrongDataException("Both tag fields can not be null.");
        }
        if (newTag.getId() != null && newTag.getName() != null) {
          if (!tagService.getById(newTag.getId()).getName().equals(newTag.getName())) {
            throw new WrongDataException("You can add tag only by id or only by name.");
          } else {
            newTagsList.add(newTag);
          }
        } else if (newTag.getName() != null) {
          Long tagId = getPresentTagId(newTag.getName());
          Long wantedTagId;
          if (tagId != 0) {
            wantedTagId = tagId;
          } else {
            Tag tag = tagRepository.create(newTag);
            wantedTagId = tag.getId();
          }
          newTagsList.add(
              tagRepository.getEntityBySpecification(new GetTagByIdSpecification(wantedTagId)));
        } else if (newTag.getId() != null) {
          if (tagRepository.getEntityBySpecification(new GetTagByIdSpecification(newTag.getId()))
              == null) {
            throw new WrongDataException("Tag with this id: " + newTag.getId() + " is not exist.");
          }
          newTagsList.add(
              tagRepository.getEntityBySpecification(new GetTagByIdSpecification(newTag.getId())));
        }
      }
    }
    giftCertificate.setTags(newTagsList);
  }

  @Override
  public Long getEntityCount() {
    throw new DataServiceException("Not supported method getEntityCount().");
  }

  @Override
  public Long getCountGiftCertificates(List<String> tagList, String queryPart, String sortBy) {
    //LOGGER.debug("GiftCertificateServiceImpl : getCountGiftCertificates");

    String tagListString = null;
    if (tagList != null) {
      tagListString = tagList.stream().collect(Collectors.joining(","));
    }

    return giftCertificateRepository.getCountGiftCertificates(
        new GetGiftCertificateCountByQuery(tagListString, queryPart, sortBy));
  }
}
