package com.epam.esm.service.impl;

import com.epam.esm.db.TagRepository;
import com.epam.esm.db.impl.TagRepositoryImpl;
import com.epam.esm.db.specification.impl.tag.GetTagByIdSpecification;
import com.epam.esm.db.specification.impl.tag.GetTagByNameSpecification;
import com.epam.esm.db.specification.impl.tag.GetTagListSpecification;
import com.epam.esm.db.specification.impl.tag.GetTagOfUserWithTheHighestCost;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.util.EntityDTOConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService<Tag, TagDTO, Long> {

  private static final Logger LOGGER = Logger.getRootLogger();

  private TagRepository<Tag, Long> tagRepository;

  private EntityDTOConverter entityDTOConverter;

  @Autowired
  public TagServiceImpl(TagRepositoryImpl tagRepository, EntityDTOConverter entityDTOConverter) {
    this.tagRepository = tagRepository;
    this.entityDTOConverter = entityDTOConverter;
  }

  @Override
  public TagDTO create(TagDTO tagDTO) {
    LOGGER.debug("TagServiceImpl : create method");
    if (isTagNamePresent(tagDTO)) {
      throw new DataServiceException(
          "Tag with this name: " + tagDTO.getName() + "  presents in the database.");
    }

    Tag tag = (Tag) entityDTOConverter.convertDtoToEntity(tagDTO, new Tag());
    tag = tagRepository.create(tag);
    return (TagDTO) entityDTOConverter.convertEntityToDto(tag, new TagDTO());
  }

  @Override
  public void update(TagDTO tagDTO) {
    LOGGER.debug("TagServiceImpl : updateEntity method for tagDTO id:" + tagDTO.getId());
    if (getById(tagDTO.getId()) == null) {
      throw new EntityNotFoundException("Tag for id: " + tagDTO.getId() + " not found.");
    }
    if (isTagNamePresent(tagDTO)) {
      throw new DataServiceException(
          "Tag with this name: " + tagDTO.getName() + "  presents in the database.");
    }
    Tag tag = (Tag) entityDTOConverter.convertDtoToEntity(tagDTO, new Tag());
    tagRepository.update(tag);
  }

  /**
   * The method checks if tag present with the same name in the database
   *
   * @param tagDTO
   * @return true if name present in the database
   */
  private boolean isTagNamePresent(TagDTO tagDTO) {
    boolean result = false;
    List<Tag> tagList = tagRepository.getAllListBySpecification(new GetTagListSpecification());
    for (Tag existTag : tagList) {
      if (existTag.getName().equals(tagDTO.getName())) {
        result = true;
      }
    }
    return result;
  }

  @Override
  public void delete(Long id) {
    LOGGER.debug("TagServiceImpl : delete method for id:" + id);
    Tag tag = tagRepository.getEntityBySpecification(new GetTagByIdSpecification(id));
    if (tag == null) {
      throw new EntityNotFoundException("Tag for id: " + id + " not found.");
    }
    tagRepository.delete(tag);
  }

  @Override
  public List<TagDTO> getAll(int page, int size) {
    LOGGER.debug("TagServiceImpl : getAll <TagDTO> method");
    List<Tag> tags = getAllTag(page, size);
    List<TagDTO> tagsDTO = new ArrayList<>();
    for (Tag tag : tags) {
      tagsDTO.add((TagDTO) entityDTOConverter.convertEntityToDto(tag, new TagDTO()));
    }
    return tagsDTO;
  }

  @Override
  public List<Tag> getAllTag(int page, int size) {
    LOGGER.debug("TagServiceImpl : getAll <Tag> method");
    return tagRepository.getListBySpecification(new GetTagListSpecification(), page, size);
  }

  @Override
  public TagDTO getById(Long id) {
    LOGGER.debug("TagServiceImpl : getEntityBySpecification method for id:" + id);
    Tag tag = tagRepository.getEntityBySpecification(new GetTagByIdSpecification(id));
    if (tag == null) {
      throw new EntityNotFoundException("Tag for id: " + id + " not found.");
    }
    return (TagDTO) entityDTOConverter.convertEntityToDto(tag, new TagDTO());
  }

  @Override
  public TagDTO getByName(String name) {
    LOGGER.debug("TagServiceImpl : getByName method for name:" + name);
    Tag tag = tagRepository.getEntityBySpecification(new GetTagByNameSpecification(name));
    if (tag == null) {
      throw new EntityNotFoundException("Tag for name: " + name + " not found.");
    }
    return (TagDTO) entityDTOConverter.convertEntityToDto(tag, new TagDTO());
  }

  @Override
  public List<TagDTO> getPurchaseUserTags(Long userId, String sort, int limit) {
    LOGGER.debug("TagServiceImpl : getPurchaseUserTags method");

    List<BigInteger> tagsId =
        tagRepository.getBySQLQuery(new GetTagOfUserWithTheHighestCost(userId, sort, limit));
    List<TagDTO> tagsDTO = new ArrayList<>();
    for (BigInteger id : tagsId) {
      tagsDTO.add(getById(id.longValue()));
    }

    return tagsDTO;
  }

  @Override
  public Long getEntityCount() {
    return tagRepository.getEntityCount();
  }
}
