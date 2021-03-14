package com.epam.esm.db;

import com.epam.esm.db.specification.Specification;
import com.epam.esm.model.AbstractEntity;

import java.util.List;

public interface GenericRepository<T extends AbstractEntity, K> {

    /**
     * Adds entity to database and returns id
     *
     * @param entity
     * @return id
     */
    T create(T entity);

    /**
     * Updates entity fields in database
     *
     * @param entity
     */
    void update(T entity);

    /**
     * Deletes entity from database
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * Returns entity list from database by specification
     *
     * @param page - pages quantity
     * @param size - items quantity on the page
     * @param specification
     * @return entity list
     */
    List<T> getListBySpecification(Specification specification, int page, int size);

    /**
     * Returns entity from database by specification
     *
     * @param specification
     * @return entity
     */
    T getEntityBySpecification(Specification specification);

    /**
     * Returns entities count in database
     *
     * @return
     */
    K getEntityCount();
}
