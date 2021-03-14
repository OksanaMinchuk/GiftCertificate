package com.epam.esm.service;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.model.AbstractEntity;

public interface GenericService<T extends AbstractEntity, D extends AbstractDTO, K> {

    /**
     * Adds entity to database and returns id
     *
     * @param entity
     * @return id
     */
    D create(D entity) throws DataServiceException;

    /**
     * Updates entity fields in database
     *
     * @param entity
     */
    void update(D entity) throws DataServiceException;

    /**
     * Deletes entity from database
     *
     * @param id
     */
    void delete(K id);

    /**
     * Returns entity from database by id
     *
     * @param id
     * @return entity
     */
    D getById(K id) throws DataServiceException;

    /**
     * Returns entities count in database
     *
     * @return
     */
    K getEntityCount();

}
