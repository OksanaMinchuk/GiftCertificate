package com.epam.esm.service;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.model.AbstractEntity;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagService<T extends AbstractEntity, D extends AbstractDTO, K>
        extends GenericService<Tag, TagDTO, Long> {

    /**
     * Returns Tag list
     *
     * @param page - pages quantity
     * @param size - items quantity on the page
     * @return Tag list
     */
    List<T> getAllTag(int page, int size);

    /**
     * Returns TagDTO list
     *
     * @param page - pages quantity
     * @param size - items quantity on the page
     * @return TagDTO list
     * @throws DataServiceException
     */
    List<D> getAll(int page, int size) throws DataServiceException;

    /**
     * Returns Tag by name
     *
     * @param name
     * @return
     */
    D getByName(String name);

    /**
     * Returns the most widely used tag of a user with the highest cost of all orders.
     *
     * @param userId
     * @param  limit
     * @return
     */
    List<D> getPurchaseUserTags(Long userId, String filtrBy, int limit);

}
