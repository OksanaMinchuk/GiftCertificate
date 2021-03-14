package com.epam.esm.service.impl;

import com.epam.esm.db.impl.TagRepositoryImpl;
import com.epam.esm.db.specification.impl.tag.GetTagByIdSpecification;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.util.EntityDTOConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceImplTest {

  @Mock private TagRepositoryImpl tagRepository;
  @Mock private EntityDTOConverter entityDTOConverter;

  @InjectMocks private TagServiceImpl tagService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreate() throws DataServiceException {
    when(tagRepository.create(any(Tag.class))).thenReturn(new Tag());
    when(tagService.create(any(TagDTO.class))).thenReturn(new TagDTO());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testUpdate() throws DataServiceException {
    TagDTO tagDTO = new TagDTO(1L, "test");
    when(tagRepository.getEntityBySpecification(new GetTagByIdSpecification(anyLong())))
        .thenThrow(EntityNotFoundException.class);
    tagService.update(tagDTO);
    verify(tagRepository, times(1)).update(anyObject());
  }

  @Test(expected = EntityNotFoundException.class)
  public void testDelete() throws DataServiceException {
    TagDTO tagDTO = new TagDTO(1L, "test");
    when(tagRepository.getEntityBySpecification(new GetTagByIdSpecification(anyLong())))
        .thenThrow(EntityNotFoundException.class);
    tagService.delete(tagDTO.getId());
    verify(tagRepository, times(1)).delete(anyObject());
  }

  @Test
  public void testGetAllTag() throws DataServiceException {
    List<Tag> tags = new ArrayList<>();
    int page = 1;
    int size = 10;

    when(tagRepository.getListBySpecification(anyObject(), eq(page), eq(size))).thenReturn(tags);

    List<Tag> actual = tagService.getAllTag(page, size);

    verify(tagRepository, times(1)).getListBySpecification(anyObject(), eq(page), eq(size));
    assertEquals(tags, actual);
  }

  @Test(expected = EntityNotFoundException.class)
  public void testGetById() throws DataServiceException {
    when(tagRepository.getEntityBySpecification(anyObject()))
        .thenThrow(EntityNotFoundException.class);

    TagDTO tagDTO = tagService.getById(anyLong());
    verify(tagRepository, times(1))
        .getEntityBySpecification(anyObject());
  }
}
