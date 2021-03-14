package com.epam.esm.util;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.model.AbstractEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityDTOConverter<E extends AbstractEntity, D extends AbstractDTO> {

  private ModelMapper modelMapper;

  @Autowired
  public EntityDTOConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public E convertDtoToEntity(D dto, E entity) throws DataServiceException {
    if (dto != null) {
      return (E) modelMapper.map(dto, entity.getClass());
    } else {
      throw new DataServiceException("Dto object is null.");
    }
  }

  public D convertEntityToDto(E entity, D dto) throws DataServiceException {
    if (entity != null) {
      return (D) modelMapper.map(entity, dto.getClass());
    } else {
      throw new DataServiceException("Entity object is null.");
    }
  }
}
