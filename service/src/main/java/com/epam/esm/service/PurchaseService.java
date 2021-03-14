package com.epam.esm.service;

import com.epam.esm.dto.AbstractDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PurchaseDTO;
import com.epam.esm.exception.DataServiceException;
import com.epam.esm.model.AbstractEntity;
import com.epam.esm.model.Purchase;

import java.util.List;

public interface PurchaseService<T extends AbstractEntity, D extends AbstractDTO, K>
    extends GenericService<Purchase, PurchaseDTO, Long> {

    /**
     * Creates purchase by userId and giftCertificateId
     *
     * @param orderDTO
     * @return purchase id
     */
    D createPurchase(OrderDTO orderDTO) throws DataServiceException;

    /**
     * Returns PurchaseDTO list
     *
     * @param page - pages quantity
     * @param size - items quantity on the page
     * @return PurchaseDTO list
     * @throws DataServiceException
     */
    List<D> getAll(int page, int size) throws DataServiceException;

    /**
     * Returns PurchaseDTO list for user
     *
     * @param userId
     * @param page - pages quantity
     * @param size - items quantity on the page
     * @return users PurchaseDTO list
     */
    List<D> getPurchaseByUserId(Long userId, int page, int size);

    /**
     * Returns PurchaseDTO list for user and GiftCertificate
     *
     * @param userId
     * @param giftCertificateId
     * @param page - pages quantity
     * @param size - items quantity on the page
     * @return PurchaseDTO list
     */
//    List<D> getPurchaseByUserIdAndGiftCertificateId(Long userId, Long giftCertificateId, int page, int size);
}
