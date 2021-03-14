package com.epam.esm.service;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.model.AbstractEntity;
import com.epam.esm.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateService<T extends AbstractEntity, D extends AbstractDTO, K>
        extends GenericService<GiftCertificate, GiftCertificateDTO, Long> {

    /**
     * Returns entity list from database accoding input parameters
     *
     * @param wantedTag
     * @param queryPart
     * @param sortBy
     * @param page - pages quantity
     * @param size - items quantity on the page
     * @return entity list
     * @throws DataServiceException
     */
    List<D> getListByQuery(List<String> wantedTag, String queryPart, String sortBy, int page, int size)
            throws DataServiceException;

    /**
     * Returns entity list by tag
     *
     * @param giftCertificateDTOS
     * @param tag
     * @return entity list
     */
    List<D> getByTag(String tag, List<GiftCertificateDTO> giftCertificateDTOS);

    /**
     * Makes a partial updateEntity to a particular resource’s object
     *
     * @param giftCertificateDTO
     */
    D updateSingleField(GiftCertificateDTO giftCertificateDTO);

    /**
     * Updates entity fields in database
     *
     * @param entity
     */
    D updateEntity(D entity) throws DataServiceException;

    /**
     * Returns entity count from database by specification with SQL query
     *
     * @param wantedTag
     * @param queryPart
     * @param sortBy
     * @return
     */
    K getCountGiftCertificates(List<String> wantedTag, String queryPart, String sortBy);
}
